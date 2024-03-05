import org.gradle.internal.logging.text.StyledTextOutput
import org.gradle.internal.logging.text.StyledTextOutputFactory
import org.gradle.internal.service.ServiceRegistry
import java.io.File
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.extension
import kotlin.io.path.isRegularFile
import kotlin.io.path.moveTo
import kotlin.io.path.name

class InitializerRelocator(
    private val serviceRegistry: ServiceRegistry,
    private val templatePath: String,
    private val projectName: String,
    var projectPath: String,
    private val sourcePackage: String,
    private val destinationPackage: String
) {
    private val outStyledPrint: StyledTextOutput =
        serviceRegistry.get(StyledTextOutputFactory::class.java).create("formattedOutput")

    fun copyProjectPath(
        logMessage: String,
        doneMessage: String,
        errorMessage: String
    ): String = try {
        val fProjectPath = File(templatePath)
        val parentFolder = fProjectPath.parent
        val destinationFolder = "$parentFolder${File.separator}$projectName"
        val targetPath = File(destinationFolder)
        outStyledPrint.text(logMessage)
        fProjectPath.copyRecursively(
            target = targetPath,
            overwrite = true
        )

        outStyledPrint.withStyle(StyledTextOutput.Style.Success).println(doneMessage)

        destinationFolder
    } catch (_: Exception) {
        outStyledPrint.withStyle(StyledTextOutput.Style.Failure).println(errorMessage)
        ""
    }

    fun changePackage(onFileWalk: (File) -> Unit) {
        Files.walkFileTree(File(projectPath).toPath(),
            object : SimpleFileVisitor<Path>() {
                @Throws(IOException::class)
                override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
                    if (
                        file.isRegularFile() &&
                        file.extension in arrayOf("kt", "xml", "kts", "swift", "xcconfig", "pbxproj", "plist") &&
                        !file.name.contains("ProjectInitializeTask")
                    ) {
                        outStyledPrint.withStyle(StyledTextOutput.Style.Normal)
                            .text("· [${file.name}] Modifying source package $sourcePackage to destination package $destinationPackage...")
                        if (file.toFile().absolutePath.contains(
                                other = ".idea",
                                ignoreCase = true
                            )
                        ) {
                            outStyledPrint.withStyle(StyledTextOutput.Style.Info).println(" [SKIPPED]")
                        } else {
                            try {
                                val content = file.toFile().readText(charset = Charsets.UTF_8)
                                if (content.contains(sourcePackage)) {
                                    file.toFile().writeText(content.replace(sourcePackage, destinationPackage))
                                    outStyledPrint.withStyle(StyledTextOutput.Style.Success).println(" [SUCCESS]")
                                } else {
                                    outStyledPrint.withStyle(StyledTextOutput.Style.Info).println(" <SKIPPED>")
                                }
                                onFileWalk(file.toFile())
                            } catch (_: Exception) {
                                outStyledPrint.withStyle(StyledTextOutput.Style.Failure).println(" [ERROR]")
                            }
                        }
                    }

                    return FileVisitResult.CONTINUE
                }
            }
        )
    }

    fun relocatePackageFolders() {
        val sourcePackageFolder = sourcePackage.replace(".", File.separator)
        val destinationPackageFolder = destinationPackage.replace(".", File.separator)

        val folders = recurseFolders(File(projectPath))
            .filter { x ->
                !x.absolutePath.contains("build") &&
                        !x.absolutePath.contains(".idea") &&
                        !x.absolutePath.contains(".git") &&
                        !x.absolutePath.contains(".gradle") &&
                        !x.absolutePath.contains(".run") &&
                        x.absolutePath.endsWith(sourcePackageFolder)
            }.distinct()


        folders.forEach { sourceFolder ->
            val destinationFolder =
                File(sourceFolder.absolutePath.replace(sourcePackageFolder, destinationPackageFolder))

            outStyledPrint.withStyle(StyledTextOutput.Style.Normal).text(
                "· Relocate source package folder:" +
                        "\n\t\t- from: ${sourceFolder.absolutePath}" +
                        "\n\t\t- to: ${destinationFolder.absolutePath}..."
            )
            try {
                sourceFolder.copyRecursively(destinationFolder, overwrite = true)
                val sourceFolderParents = sourceFolder.getAllParentsRecursive()
                val destinationFolderParents = destinationFolder.getAllParentsRecursive()

                val commonParent = sourceFolderParents.filter { parent -> parent in destinationFolderParents }.first()

                val parentToDelete = sourceFolder.getParentWithAncestor(commonParent)
                parentToDelete.deleteRecursively()

                outStyledPrint.withStyle(StyledTextOutput.Style.Success).println(" [SUCCESS]")
            } catch (_: Exception) {
                outStyledPrint.withStyle(StyledTextOutput.Style.Failure).println(" [ERROR]")
            }
        }
    }

    fun File.getParentWithAncestor(ancestor: File): File {
        if (this.parentFile == ancestor)
            return this
        else
            return this.parentFile.getParentWithAncestor(ancestor)
    }

    fun File.getAllParentsRecursive(): List<File> {
        val parents = mutableListOf<File>()
        getAllParentsRecursiveHelper(this, parents)

        return parents
    }

    private fun getAllParentsRecursiveHelper(file: File, parents: MutableList<File>) {
        val parent = file.parentFile

        if (parent != null && parent.exists()) {
            parents.add(parent)
            getAllParentsRecursiveHelper(parent, parents)
        }
    }

    private fun recurseFolders(currentPath: File): List<File> {
        val files = currentPath.listFiles()?.filter { x -> x.isDirectory } ?: emptyList()

        if (files.isNotEmpty()) {
            return files.union(files.map { xDir -> recurseFolders(xDir) }.flatten()).toList()
        }

        return emptyList()
    }
}

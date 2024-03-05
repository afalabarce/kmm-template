import org.gradle.internal.logging.text.StyledTextOutput
import org.gradle.internal.logging.text.StyledTextOutputFactory
import org.gradle.internal.service.ServiceRegistry
import java.io.File

class InitializerValidator(
    private val serviceRegistry: ServiceRegistry,
    private val templatePath: String,
    private val destinationPath: String,
    private val sourcePackage: String,
    private val destinationPackage: String,
    private val destinationPathDescription: String,
    private val sourcePackageDescription: String,
    private val destinationPackageDescription: String,
    private val appName: String,
    private val appNameDescription: String
) {
    private fun help(returnValue: Boolean, message: String): Boolean {
        val out: StyledTextOutput =
            serviceRegistry.get(StyledTextOutputFactory::class.java).create("formattedOutput")

        if (!returnValue) {
            with(out.withStyle(StyledTextOutput.Style.Description)) {
                println("Project Initializer Task Help")
                println("-------------------------------")
                println("This task can relocate packages and folders from source package to destination package")
                println("- appName, $appNameDescription")
                println("- destinationPath, $destinationPathDescription")
                println("- sourcePackage, $sourcePackageDescription")
                println("- destinationPackage, $destinationPackageDescription")
                println("\nUsage:")
                println(
                    "./gradlew -q initializeKmmProject \\\n" +
                            "\t--appName=\"Your Awesome App Name\" \\\n" +
                            "\t--destinationPath=\"your-new-project-root\" \\\n" +
                            "\t--sourcePackage=\"io.afalabarce.template.kmm\" \\\n" +
                            "\t--destinationPackage=\"io.github.afalabarce.awesomeapp\""
                )
                println("-------------------------------")
            }

            out.withStyle(StyledTextOutput.Style.Failure).println(message)
        }

        return returnValue
    }

    internal fun validate(): Boolean {
        var message = ""
        var returnValue = true

        if (destinationPath.isEmpty()) {
            message = "ERROR: Destination path can not be empty\n"
            returnValue = false
        }
        if (sourcePackage.isEmpty()) {
            message += "ERROR: Source package can not be empty\n"
            returnValue = false
        }

        if (destinationPackage.isEmpty()) {
            message += "ERROR: Destination package can not be empty\n"
            returnValue = false
        }

        if (appName.isEmpty()) {
            message += "ERROR: App Name can not be empty\n"
            return false
        }

        if (File("$templatePath${File.separator}..${File.separator}$destinationPath").exists()) {
            message += "ERROR: Destination path can not exists\n"
            returnValue = false
        }
        if (destinationPath.trim().lowercase() == templatePath) {
            message += "ERROR: Destination path can not be equal to current project dir (case insensitive)\n"
            returnValue = false
        }

        if (destinationPackage.trim().lowercase() == sourcePackage.trim().lowercase()) {
            message += "ERROR: Destination package can not be equal to Source package (case insensitive)\n"
            returnValue = false
        }

        return help(returnValue, message)
    }
}
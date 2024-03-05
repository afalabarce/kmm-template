import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.internal.logging.text.StyledTextOutput
import org.gradle.internal.logging.text.StyledTextOutputFactory

abstract class ProjectInitializeTask : DefaultTask() {
    //region Task parameters definition

    private var _sourcePackage: String = BuildVersion.environment.applicationId
    private var _destinationPackage: String = ""

    private var _destinationPath: String = ""
    private var _appName: String = BuildVersion.environment.appName

    @Input
    fun getAppName(): String = _appName

    @Option(
        option = "appName",
        description = APP_NAME_DESCRIPTION
    )
    fun setAppName(newAppName: String) {
        _appName = newAppName
    }

    @get:Input
    abstract val projectDir: Property<String>

    @Input
    fun getSourcePackage(): String = _sourcePackage

    @Option(
        option = "sourcePackage",
        description = SOURCE_PACKAGE_DESCRIPTION
    )
    fun setSourcePackage(sourcePackage: String) {
        _sourcePackage = sourcePackage
    }

    @Input
    fun getDestinationPath(): String = _destinationPath

    @Option(
        option = "destinationPath",
        description = DESTINATION_PATH_DESCRIPTION
    )
    fun setDestinationPath(destinationPath: String) {
        _destinationPath = destinationPath
    }

    @Input
    fun getDestinationPackage(): String = _destinationPackage

    @Option(
        option = "destinationPackage",
        description = DESTINATION_PACKAGE_DESCRIPTION
    )
    fun setDestinationPackage(destinationPackage: String) {
        _destinationPackage = destinationPackage
    }

    //endregion

    @TaskAction
    fun deployKmmProject() {
        val templatePath = projectDir.get()
        val validator = InitializerValidator(
            serviceRegistry = services,
            templatePath = templatePath,
            destinationPath = _destinationPath,
            sourcePackage = _sourcePackage,
            destinationPackage = _destinationPackage,
            appName = _appName,
            destinationPathDescription = DESTINATION_PATH_DESCRIPTION,
            sourcePackageDescription = SOURCE_PACKAGE_DESCRIPTION,
            destinationPackageDescription = DESTINATION_PACKAGE_DESCRIPTION,
            appNameDescription = APP_NAME_DESCRIPTION
        )
        val out: StyledTextOutput =
            services.get(StyledTextOutputFactory::class.java).create("formattedOutput")
        check(validator.validate())
        val relocator = InitializerRelocator(
            serviceRegistry = services,
            templatePath = templatePath,
            projectName = _destinationPath,
            projectPath = "",
            sourcePackage = _sourcePackage,
            destinationPackage = _destinationPackage
        )
        val newProjectPath = relocator.copyProjectPath(
            logMessage = "Copy template to destination path...",
            doneMessage = "[DONE]",
            errorMessage = "[ERROR]"
        )

        check(newProjectPath.isNotEmpty()) { "ERROR: can't create destination folder" }
        relocator.projectPath = newProjectPath
        relocator.relocatePackageFolders()
        relocator.changePackage { file ->
            val content = file.readText()

            when (file.name) {
                STRINGS_XML_FILE -> {
                    file.writeText(
                        content.replace(
                            oldValue = "<string name=\"app_name\">${BuildVersion.environment.appName}</string>",
                            newValue = "<string name=\"app_name\">${_appName}</string>"
                        )
                    )
                }

                CONFIG_XCCONFIG_FILE -> {
                    file.writeText(
                        content.replace(
                            oldValue = "APP_NAME=${BuildVersion.environment.appName}",
                            newValue = "APP_NAME=$_appName"
                        ).replace(
                            oldValue = "BUNDLE_VERSION_STRING=1.0.0",
                            newValue = "BUNDLE_VERSION_STRING=${BuildVersion.environment.appVersionCode}"
                        ).replace(
                            oldValue = "BUNDLE_VERSION=100",
                            newValue = "BUNDLE_VERSION=${BuildVersion.environment.appVersion}"
                        )
                    )
                }

                else -> { /* Do nothing */
                }
            }

        }
    }

    companion object {
        private const val SOURCE_PACKAGE_DESCRIPTION =
            "Source package from template, by default ${BuildVersion.environment.applicationId}"
        private const val DESTINATION_PACKAGE_DESCRIPTION =
            "Destination package for the template, can't be empty, and not equals to sourcePackage"
        private const val DESTINATION_PATH_DESCRIPTION =
            "Destination path for the new project(sibling to root project dir), can't be empty, can't exists, and not equals to current template project dir"
        private const val APP_NAME_DESCRIPTION =
            "New project Application Name (By Default ${BuildVersion.environment.appName})"
        private const val STRINGS_XML_FILE = "strings.xml"
        private const val CONFIG_XCCONFIG_FILE = "Config.xcconfig"
    }
}

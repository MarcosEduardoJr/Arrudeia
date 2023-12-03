import com.droidmaster.arrudeia.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("arrudeia.android.lint")
            }
            configureKotlinJvm()
        }
    }
}

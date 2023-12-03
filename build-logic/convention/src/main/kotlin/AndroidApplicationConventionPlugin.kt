import com.android.build.api.dsl.ApplicationExtension
import com.droidmaster.arrudeia.configureGradleManagedDevices
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.droidmaster.arrudeia.configureKotlinAndroid
import com.droidmaster.arrudeia.configurePrintApksTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("arrudeia.android.lint")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
                configureGradleManagedDevices(this)
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
            }
        }
    }

}
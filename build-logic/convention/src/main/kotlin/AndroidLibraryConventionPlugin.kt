import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.droidmaster.arrudeia.configureFlavors
import com.droidmaster.arrudeia.configureGradleManagedDevices
import com.droidmaster.arrudeia.configureKotlinAndroid
import com.droidmaster.arrudeia.configurePrintApksTask
import com.droidmaster.arrudeia.disableUnnecessaryAndroidTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("arrudeia.android.lint")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
                configureFlavors(this)
                configureGradleManagedDevices(this)
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
                add("androidTestImplementation", kotlin("test"))
                add("testImplementation", kotlin("test"))
            }
        }
    }
}
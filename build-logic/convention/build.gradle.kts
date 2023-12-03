import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    `kotlin-dsl`
}

group = "com.droidmaster.arrudeia.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "arrudeia.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "arrudeia.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = "arrudeia.android.application.jacoco"
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "arrudeia.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "arrudeia.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "arrudeia.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = "arrudeia.android.library.jacoco"
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }
        register("androidTest") {
            id = "arrudeia.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("androidHilt") {
            id = "arrudeia.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "arrudeia.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidFirebase") {
            id = "arrudeia.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
        register("androidFlavors") {
            id = "arrudeia.android.application.flavors"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
        register("androidLint") {
            id = "arrudeia.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("jvmLibrary") {
            id = "arrudeia.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}

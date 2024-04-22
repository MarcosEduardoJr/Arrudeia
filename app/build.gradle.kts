plugins {
    id("arrudeia.android.application")
    id("arrudeia.android.application.compose")
    id("arrudeia.android.application.flavors")
    id("arrudeia.android.application.jacoco")
    id("arrudeia.android.hilt")
    id("jacoco")
    id("arrudeia.android.application.firebase")
    //id("com.google.android.gms.oss-licenses-plugin")
}

android {

    defaultConfig {
        applicationId = "com.droidmaster.arrudeia"
        versionCode = 21
        versionName = "1.7.2" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        // Custom test runner to set up Hilt dependency graph
        //testInstrumentationRunner = "com.arrudeia.core.testing.ArrudeiaTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
           // applicationIdSuffix = ArrudeiaBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
           // applicationIdSuffix = ArrudeiaBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
        }
        create("benchmark") {
            // Enable all the optimizations from release build through initWith(release).
            initWith(release)
            matchingFallbacks.add("release")
            // Debug key signing is available to everyone.
            signingConfig = signingConfigs.getByName("debug")
            // Only use benchmark proguard rules
            proguardFiles("benchmark-rules.pro")
            isMinifyEnabled = true
            //applicationIdSuffix = ArrudeiaBuildType.BENCHMARK.applicationIdSuffix
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.droidmaster.arrudeia"
}

dependencies {

    implementation(project(":feature:onboarding"))
    implementation(project(":feature:sign"))
    implementation(project(":feature:home"))
    implementation(project(":feature:trip"))
    implementation(project(":feature:stories"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:arrudeia"))

    implementation(libs.androidx.material3)

    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.accompanist.testharness)
    androidTestImplementation(kotlin("test"))
    debugImplementation(libs.androidx.compose.ui.testManifest)
    //debugImplementation(project(":ui-test-hilt-manifest"))

    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(project(":sync:work"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.coil.kt)
    implementation(libs.androidx.metrics)

    implementation(libs.firebase.firestore)

    testImplementation(libs.androidx.navigation.testing)
    testImplementation(libs.accompanist.testharness)
    testImplementation(kotlin("test"))
    implementation(libs.work.testing)
    kaptTest(libs.hilt.compiler)

}

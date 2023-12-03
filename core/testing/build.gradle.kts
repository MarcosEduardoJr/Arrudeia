
plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.compose")
    id("arrudeia.android.hilt")
}

android {
    namespace = "com.arrudeia.core.testing"
}

dependencies {
    api(libs.accompanist.testharness)
    api(libs.androidx.activity.compose)
    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)
    api(libs.roborazzi)
    api(libs.robolectric.shadows)
    api(libs.turbine)

    debugApi(libs.androidx.compose.ui.testManifest)

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:notifications"))
    implementation(project(":core:analytics"))
    implementation(libs.kotlinx.datetime)
}

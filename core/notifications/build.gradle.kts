
plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.compose")
    id("arrudeia.android.hilt")
}

android {
    namespace = "com.arrudeia.core.notifications"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core.ktx)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.cloud.messaging)
}

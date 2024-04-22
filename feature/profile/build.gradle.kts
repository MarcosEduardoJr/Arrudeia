plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
    id("arrudeia.android.library.jacoco")
}

android {
    namespace = "com.arrudeia.feature.profile"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))

    implementation(project(":core:common"))
    implementation(libs.androidx.compose.material3)
    implementation(libs.glide.compose)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.firebase.bom)
    implementation(libs.firebase.storage)

    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)



}

plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
    id("arrudeia.android.library.jacoco")
}

android {
    namespace = "com.arrudeia.feature.stories"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))

    implementation(project(":core:common"))
    implementation(libs.androidx.compose.material3)
    implementation(libs.glide.compose)

    implementation(libs.kotlinx.serialization.json)
}

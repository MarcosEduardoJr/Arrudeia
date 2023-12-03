plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
    id("arrudeia.android.library.jacoco")
}

android {
    namespace = "com.arrudeia.feature.trip"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))
}

plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
    id("arrudeia.android.library.jacoco")
}

android {
    namespace = "com.arrudeia.feature.home"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.compose.material3)
}



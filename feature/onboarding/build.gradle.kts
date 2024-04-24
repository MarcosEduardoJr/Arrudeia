plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
}

android {
    namespace = "com.arrudeia.feature.onboarding"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))

     implementation(libs.androidx.compose.material3)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
}

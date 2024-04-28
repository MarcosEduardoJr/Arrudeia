plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
    id("io.gitlab.arturbosch.detekt")
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
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit4)
    implementation(libs.mockito.core)
    implementation(libs.mockk)
}

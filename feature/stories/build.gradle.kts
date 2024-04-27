plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.arrudeia.feature.stories"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))


    implementation(libs.androidx.compose.material3)
    implementation(libs.glide.compose)

    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    implementation(libs.firebase.firestore)
}

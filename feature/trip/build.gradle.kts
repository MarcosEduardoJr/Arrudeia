plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.arrudeia.feature.trip"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.glide.compose)
    testImplementation(libs.mockk)
    testImplementation(libs.mockito.core)

    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.androidx.compose.material3)

    implementation(libs.firebase.firestore)

    implementation(libs.coreTesting)
}

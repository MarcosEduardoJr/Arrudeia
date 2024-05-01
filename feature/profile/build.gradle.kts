plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.arrudeia.feature.profile"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))

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


    implementation(libs.apollo.runtime)


    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)


    implementation(libs.firebase.bom)
    implementation(libs.firebase.auth)
    implementation(libs.mockito.kotlin)
}

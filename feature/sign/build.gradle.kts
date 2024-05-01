plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
}

android {
    namespace = "com.arrudeia.feature.sign"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(libs.firebase.bom)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.material3)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)


    implementation(libs.firebase.bom)
    implementation(libs.firebase.storage)

    implementation(libs.junit4)
    implementation(libs.mockk)
    implementation(libs.coreTesting)
    implementation(libs.kotlinx.coroutines.test)
}

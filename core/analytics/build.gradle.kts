plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.compose")
    id("arrudeia.android.hilt")
}

android {
    namespace = "com.arrudeia.core.analytics"
    // namespace = "com.arrudeia.core.analytics"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.firebase.analytics)
    implementation(libs.kotlinx.coroutines.android)
}

plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
    id("arrudeia.android.library.jacoco")
}

android {
    namespace = "com.arrudeia.feature.sign"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))
    implementation(libs.firebase.bom)
 //   implementation(libs.google.auth)
  //  implementation(libs.kotlinx.coroutines.play.services)
   // implementation(libs.androix.credentials)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.material3)
}

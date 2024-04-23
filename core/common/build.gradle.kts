
plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.jacoco")
    id("arrudeia.android.hilt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.arrudeia.core.common"
}
secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}
dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.bom)
    implementation(libs.firebase.firestore)

}
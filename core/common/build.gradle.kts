
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
}
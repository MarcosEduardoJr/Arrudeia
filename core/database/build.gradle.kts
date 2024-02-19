plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.jacoco")
    id("arrudeia.android.hilt")
    id("arrudeia.android.room")
}

android {
    defaultConfig {
        testInstrumentationRunner =
            "com.arrudeia.core.testing.ArrudeiaTestRunner"
    }
    namespace = "com.arrudeia.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

}

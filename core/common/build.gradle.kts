
plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.jacoco")
    id("arrudeia.android.hilt")
}

android {
    namespace = "com.arrudeia.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}
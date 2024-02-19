
plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.jacoco")
    id("arrudeia.android.hilt")
}

android {
    defaultConfig {
        testInstrumentationRunner = "com.arrudeia.core.testing.ArrudeiaTestRunner"
    }
    namespace = "com.arrudeia.sync"
}

dependencies {
    implementation(project(":core:analytics"))
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.firebase.cloud.messaging)
    implementation(libs.hilt.ext.work)
    implementation(libs.kotlinx.coroutines.android)

    kapt(libs.hilt.ext.compiler)

    androidTestImplementation(libs.androidx.work.testing)
}

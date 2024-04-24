plugins {
    id("arrudeia.android.library")
    kotlin("kapt")
}




android {
    namespace = "com.arrudeia.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.converter.moshi)
    implementation(libs.converter.gson)
    implementation(libs.mockito.core)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.apollo.runtime)

    implementation(libs.firebase.bom)
    implementation(libs.firebase.firestore)
}
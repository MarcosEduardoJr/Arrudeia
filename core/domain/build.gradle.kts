
plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.jacoco")
    kotlin("kapt")
}

android {
    namespace = "com.arrudeia.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(project(mapOf("path" to ":core:network")))

    kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.serialization.json)
    testImplementation(project(":core:testing"))

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)
    implementation(libs.converter.moshi)
    implementation(libs.converter.gson)

}
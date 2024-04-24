plugins {
    id("arrudeia.android.feature")
    id("arrudeia.android.library.compose")
}

android {
    namespace = "com.arrudeia.feature.home"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))

    implementation(project(":feature:stories"))
    implementation(project(":feature:trip"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:arrudeia"))
    implementation(libs.androidx.compose.material3)
    implementation(libs.glide.compose)

    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.firebase.bom)
    implementation(libs.firebase.firestore)

    implementation(libs.apollo.runtime)

    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
}



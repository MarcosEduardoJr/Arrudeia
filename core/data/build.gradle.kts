plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.jacoco")
    id("arrudeia.android.hilt")
    id("kotlinx-serialization")

    id("com.apollographql.apollo3").version("3.8.3")
}

apollo {
    service("service") {
        packageName.set("com.arrudeia.core.data")
    }
}

android {
    namespace = "com.arrudeia.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:analytics"))
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:notifications"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.firebase.auth)

    implementation(libs.apollo.runtime)
    implementation(libs.okhttp.logging)
}

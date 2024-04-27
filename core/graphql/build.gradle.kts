plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.hilt")
    id("kotlinx-serialization")

    id("com.apollographql.apollo3").version("3.8.3")
}

apollo {
    service("service") {
        packageName.set("com.arrudeia.core.graphql")
    }
}

android {
    namespace = "com.arrudeia.core.graphql"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    implementation(libs.apollo.runtime)
    implementation(libs.okhttp.logging)


    implementation(libs.kotlinx.serialization.json)
}
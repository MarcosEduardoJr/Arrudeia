plugins {
    id("com.android.library")
    id("kotlinx-serialization")
    id("com.apollographql.apollo3").version("3.8.3")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-android")
}

apollo {
    service("service") {
        packageName.set("com.arrudeia.core.graphql")
    }

}

android {
    compileSdk = 34
    namespace = "com.arrudeia.core.graphql"
    defaultConfig {
        minSdk = 21
        targetSdk = 34
        multiDexEnabled = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    implementation(libs.apollo.runtime)
    implementation(libs.okhttp.logging)


    implementation(libs.kotlinx.serialization.json)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
}

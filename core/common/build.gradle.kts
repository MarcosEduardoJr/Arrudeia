plugins {
    id("com.android.library")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("kapt")
    id("kotlin-android")
   id("com.google.dagger.hilt.android")
}

android {
    compileSdk = 34
    namespace = "com.arrudeia.core.common"
    defaultConfig {
        minSdk = 21
        multiDexEnabled = true
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")  
excludes.add("META-INF/LICENSE-notice.md")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    implementation(project(":core:graphql"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

   kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.converter.moshi)
    implementation(libs.converter.gson)
    implementation(libs.mockito.core)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.apollo.runtime)

    implementation(libs.firebase.bom)
    implementation(libs.firebase.firestore)

    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit4)
    implementation(libs.mockito.core)
    implementation(libs.mockk)
    implementation(libs.coreTesting)
    implementation(libs.kotlin.stdlib)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.util)
    debugApi(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.core.ktx)
}

kapt {
    correctErrorTypes = true
}
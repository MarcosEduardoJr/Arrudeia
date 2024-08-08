plugins {
    id("com.android.library")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("kapt")
    id("kotlin-android")
   id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlin.serialization)

}

android {
    compileSdk = 34
    namespace = "com.arrudeia.core.common"
    defaultConfig {
        minSdk = 21
        targetSdk = 34
        multiDexEnabled = true
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")  
excludes.add("META-INF/LICENSE-notice.md")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = false
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
    implementation(project(":core:designsystem"))
    implementation(libs.apollo.runtime)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

   kapt(libs.hilt.compiler)

    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.okhttp.logging) 

    implementation(libs.converter.moshi)
    implementation(libs.converter.gson)
    implementation(libs.mockito.core)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)

    implementation(libs.kotlinx.coroutines.test)

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

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    implementation(libs.accompanist.drawablepainter)

    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")
    implementation(libs.glide.compose)
    implementation(libs.junit4)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation(libs.play.services.wearable)

    implementation(libs.google.maps.services)

    implementation(libs.accompanist.permissions)

    implementation("com.google.maps.android:maps-compose:4.3.3")

    // Optionally, you can include the Compose utils library for Clustering,
    // Street View metadata checks, etc.
    implementation("com.google.maps.android:maps-compose-utils:4.3.3")

    // Optionally, you can include the widgets library for ScaleBar, etc.
    implementation("com.google.maps.android:maps-compose-widgets:4.3.3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation(libs.places)

    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.core)
    testImplementation("com.apollographql.apollo3:apollo-testing-support:3.8.5")

}

kapt {
    correctErrorTypes = true
}
plugins {
    id("com.android.library")
    id("io.gitlab.arturbosch.detekt")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-android")
    alias(libs.plugins.kotlin.serialization)
}

android {
    compileSdk = 34
    namespace = "com.arrudeia.feature.tips"
    defaultConfig {
        minSdk = 21
        targetSdk = 34
        multiDexEnabled = true
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":core:graphql"))
    implementation(project(":feature:receipt"))
    implementation(project(":feature:aid"))
    implementation(project(":feature:checklist"))
    implementation(libs.androidx.compose.material3)
    implementation(libs.glide.compose)

    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.firebase.bom)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)

    implementation(libs.apollo.runtime)

    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)


    implementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.testManifest)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)

    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.mockito.core)
    implementation(libs.mockk)
    implementation(libs.coreTesting)


    implementation(libs.junit4)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidyoutubeplayer)

    implementation(libs.google.maps.android.compose)

    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.androidx.work)

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

    implementation(libs.androidx.compose.runtime.android)
    coreLibraryDesugaring(libs.android.desugarJdkLibs)


    implementation(libs.androidx.compose.material.iconsExtended)
}




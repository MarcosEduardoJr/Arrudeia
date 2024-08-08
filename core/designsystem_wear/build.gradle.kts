plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("io.gitlab.arturbosch.detekt")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-android")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.droidmaster.arrudeia.core.designsystem_wear"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
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
    testOptions {

        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)

    implementation(libs.multidex)
    implementation(libs.kotlin.stdlib)

    implementation(libs.play.services.wearable)
    implementation(libs.compose.wear.foundation)
    implementation(libs.androidx.wear.tiles)
    implementation(libs.androidx.wear.tiles.material)
    implementation(libs.androidx.wear.compose.material)
    implementation(libs.horologist.compose.tools)
    implementation(libs.horologist.tiles)
    implementation(libs.androidx.wear.watchface.complications.data.source.ktx)
    implementation(libs.androidx.compose.material3)

    implementation(project(":core:common"))


    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)



    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit4)
    implementation(libs.mockito.core)
    implementation(libs.mockk)
    implementation(libs.coreTesting)



    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

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
}
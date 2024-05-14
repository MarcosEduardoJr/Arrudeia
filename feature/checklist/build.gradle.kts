plugins {
    id("com.android.library")
    id("io.gitlab.arturbosch.detekt")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-android")
}

android {
    compileSdk = 34
    namespace = "com.arrudeia.feature.checklist"
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

    implementation(libs.apollo.runtime)

    
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.glide.compose)
    testImplementation(libs.mockk)
    testImplementation(libs.mockito.core)

    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.androidx.compose.material3)

    implementation(libs.firebase.firestore)

    implementation(libs.coreTesting)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.mockito.core)
    implementation(libs.mockk)
    implementation(libs.coreTesting)


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

    implementation(libs.junit4)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

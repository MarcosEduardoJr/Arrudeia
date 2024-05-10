plugins {
    id("com.android.library" )
    id("kotlin-android")
    id("com.apollographql.apollo3").version("3.8.3")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
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
        multiDexEnabled = true
    }

    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(libs.apollo.runtime)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}

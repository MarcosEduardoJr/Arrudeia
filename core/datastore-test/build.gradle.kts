
plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.hilt")
}

android {
    namespace = "com.arrudeia.core.datastore.test"
}

dependencies {
    api(project(":core:datastore"))
    api(libs.androidx.dataStore.core)

    implementation(libs.protobuf.kotlin.lite)
    implementation(project(":core:common"))
    implementation(project(":core:testing"))
}

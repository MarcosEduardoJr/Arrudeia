
plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.hilt")
}

android {
    namespace = "com.arrudeia.core.data.test"
}

dependencies {
    api(project(":core:data"))
    implementation(project(":core:testing"))
    implementation(project(":core:common"))
}

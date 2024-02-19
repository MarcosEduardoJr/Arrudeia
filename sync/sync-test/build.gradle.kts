
plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.hilt")
}

android {
    namespace = "com.arrudeia.core.sync.test"
}

dependencies {
    api(project(":sync:work"))
    implementation(project(":core:data"))
}

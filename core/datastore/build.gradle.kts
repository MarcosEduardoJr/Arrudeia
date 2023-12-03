plugins {
    id("arrudeia.android.library")
    id("arrudeia.android.library.jacoco")
    id("arrudeia.android.hilt")
    alias(libs.plugins.protobuf)
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = "com.arrudeia.core.datastore"
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

androidComponents.beforeVariants {
    android.sourceSets.register(it.name) {
        java.srcDir(buildDir.resolve("generated/source/proto/${it.name}/java"))
        kotlin.srcDir(buildDir.resolve("generated/source/proto/${it.name}/kotlin"))
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(libs.androidx.dataStore.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.protobuf.kotlin.lite)

    testImplementation(project(":core:datastore-test"))
    testImplementation(project(":core:testing"))
}

package com.droidmaster.arrudeia

/**
 * This is shared between :app and :benchmarks module to provide configurations type safety.
 */
enum class ArrudeiaBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
    BENCHMARK(".benchmark")
}

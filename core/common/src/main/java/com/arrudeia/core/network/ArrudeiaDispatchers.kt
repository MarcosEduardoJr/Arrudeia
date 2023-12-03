package com.arrudeia.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val arrudeiaDispatchers: ArrudeiaDispatchers)

enum class ArrudeiaDispatchers {
    Default,
    IO,
}

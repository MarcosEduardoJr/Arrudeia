package com.arrudeia.core.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
abstract class ViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    open val coroutineRule = CoroutinesMainTestRule()

    protected val dispatcher = UnconfinedTestDispatcher()
    fun coTest(
        context: CoroutineContext = dispatcher,
        dispatchTimeoutMs: Long = 200_000L,
        testBody: suspend TestScope.() -> Unit
    ) {
        runTest(context, timeout = dispatchTimeoutMs.milliseconds, testBody)
    }
}
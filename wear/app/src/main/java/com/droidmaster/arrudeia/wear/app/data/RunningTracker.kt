@file:OptIn(ExperimentalCoroutinesApi::class)

package com.arrudeia.wear.app.data

import com.arrudeia.core.run.data.WatchToPhoneConnector
import com.arrudeia.core.run.domain.messaging.MessagingAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration

class RunningTracker @Inject constructor(
    private val watchToPhoneConnector: WatchToPhoneConnector,
    private val exerciseTracker: HealthServicesExerciseTracker,
    applicationScope: CoroutineScope
) {
    private val _heartRate = MutableStateFlow(0)
    val heartRate = _heartRate.asStateFlow()

    private val _isTracking = MutableStateFlow(false)
    val isTracking = _isTracking.asStateFlow()

    private val _isTrackable = MutableStateFlow(false)
    val isTrackable = _isTrackable.asStateFlow()

    val distanceMeters = watchToPhoneConnector
        .messagingActions
        .filterIsInstance<MessagingAction.DistanceUpdate>()
        .map { it.distanceMeters }
        .stateIn(
            applicationScope,
            SharingStarted.Lazily,
            0
        )

    val elapsedTime = watchToPhoneConnector
        .messagingActions
        .filterIsInstance<MessagingAction.TimeUpdate>()
        .map { it.elapsedDuration }
        .stateIn(
            applicationScope,
            SharingStarted.Lazily,
            Duration.ZERO
        )

    init {
        watchToPhoneConnector
            .messagingActions
            .onEach { action : MessagingAction ->
                when (action) {
                    is MessagingAction.Trackable -> {
                        _isTrackable.value = true
                    }

                    is MessagingAction.Untrackable -> {
                        _isTrackable.value = false
                    }

                    else -> Unit
                }
            }
            .launchIn(applicationScope)

        watchToPhoneConnector
            .connectedNode
            .filterNotNull()
            .onEach {
                applicationScope.launch {
                    exerciseTracker.prepareExercise()
                }
            }
            .launchIn(applicationScope)

        isTracking
            .flatMapLatest { isTracking ->
                if (isTracking) {
                    exerciseTracker.heartRate
                } else flowOf()
            }
            .onEach { heartRate ->
                watchToPhoneConnector.sendActionToPhone(MessagingAction.HeartRateUpdate(heartRate))
                _heartRate.value = heartRate
            }
            .launchIn(applicationScope)
    }

    fun setIsTracking(isTracking: Boolean) {
        _isTracking.value = isTracking
    }
}
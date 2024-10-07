package com.arrudeia.feature.social.presentation.viewmodel


import androidx.lifecycle.ViewModel
import com.arrudeia.core.domain.GetIsUserLoggedUseCase
import com.arrudeia.core.domain.GetUserImageUseCase
import com.arrudeia.core.domain.GetUserUuidUseCase
import com.arrudeia.feature.social.domain.GetTravelersUseCase
import com.arrudeia.feature.social.domain.UpdateTravelersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SocialPlanViewModel @Inject constructor(
    private val userUseCase: GetUserImageUseCase,
    private val getTravelersUseCase: GetTravelersUseCase,
    private val getIsUserLoggedUseCase: GetIsUserLoggedUseCase,
    private val updateTravelersUseCase: UpdateTravelersUseCase,
    private val getUserUuidUseCase: GetUserUuidUseCase,
) : ViewModel() {

}

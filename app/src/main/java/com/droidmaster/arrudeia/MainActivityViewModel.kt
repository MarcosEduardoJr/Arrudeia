package com.droidmaster.arrudeia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.domain.usecase.profileScreen.ProfileScreenUseCases
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.utils.Response
import com.example.chatwithme.domain.model.UserStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val useCases: ProfileScreenUseCases
) : ViewModel(){

    fun setUserStatusToFirebase(userStatus: UserStatus) {
        viewModelScope.launch {
            useCases.setUserStatusToFirebase(userStatus).collect { response ->
                when (response) {
                    is Response.Loading -> {}
                    is Response.Success -> {}
                    is Response.Error -> {}
                }
            }
        }
    }
}



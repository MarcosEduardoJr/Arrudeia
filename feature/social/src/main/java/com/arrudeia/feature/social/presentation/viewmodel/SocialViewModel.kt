package com.arrudeia.feature.social.presentation.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.core.domain.GetIsUserLoggedUseCase
import com.arrudeia.core.domain.GetUserImageUseCase
import com.arrudeia.core.domain.GetUserUuidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(
    private val userUseCase: GetUserImageUseCase,
    private val getIsUserLoggedUseCase: GetIsUserLoggedUseCase,
    private val getUserUuidUseCase: GetUserUuidUseCase,
) : ViewModel() {
    private var currentUserUUID: String? = null

    private fun getUserUuid() {
        viewModelScope.launch {
            getUserUuidUseCase().let {
                currentUserUUID = it
            }
        }
    }

    private val _imgUserUrl = mutableStateOf("")
    val imgUserUrl: State<String> = _imgUserUrl

    fun getUserPersonalInformation() {
        viewModelScope.launch {
            _imgUserUrl.value = userUseCase()

        }
    }

    private val _isUserLoggedUseCase = mutableStateOf(true)
    val isUserLoggedUseCase: State<Boolean> = _isUserLoggedUseCase

    fun isUserLogged() {
        viewModelScope.launch {
            _isUserLoggedUseCase.value = getIsUserLoggedUseCase()
        }
    }
}

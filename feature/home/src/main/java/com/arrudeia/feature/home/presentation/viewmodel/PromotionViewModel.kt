package com.arrudeia.feature.home.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrudeia.feature.home.domain.GetPromotionsUseCase
import com.arrudeia.feature.home.domain.entity.PromotionUseCaseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionViewModel @Inject constructor(
    private val useCase: GetPromotionsUseCase,
) : ViewModel() {


    private val _list = mutableStateListOf<PromotionUseCaseEntity>()
    val list: List<PromotionUseCaseEntity> = _list

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading
    fun fetchPromotions(
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = useCase()
                _list.clear()
                _isLoading.value = false
                _list.addAll(response)
            } catch (e: Exception) {
                _list.addAll(emptyList())
                _isLoading.value = false
            }
        }
    }
}



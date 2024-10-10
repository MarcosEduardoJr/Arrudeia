package com.arrudeia.feature.home.domain

import com.arrudeia.feature.home.data.PromotionRepositoryImpl
import javax.inject.Inject

class GetPromotionsUseCase @Inject constructor(
    private val repository: PromotionRepositoryImpl,
) {
    suspend operator fun invoke() =
        repository.getAllPromotions()
}

package com.arrudeia.feature.home.data

import com.arrudeia.feature.home.domain.entity.PromotionUseCaseEntity


interface PromotionsRepository {
    suspend fun getAllPromotions(): List<PromotionUseCaseEntity>

}

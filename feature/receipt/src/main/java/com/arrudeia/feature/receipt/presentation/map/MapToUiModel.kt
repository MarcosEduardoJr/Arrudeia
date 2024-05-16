package com.arrudeia.feature.receipt.presentation.map

import com.arrudeia.core.graphql.GetRecipeDetailQuery
import com.arrudeia.feature.receipt.data.entity.IngredientReceiptDetailRepositoryEntity
import com.arrudeia.feature.receipt.data.entity.ReceiptDetailRepositoryEntity
import com.arrudeia.feature.receipt.domain.entity.IngredientReceiptDetailUseCaseEntity
import com.arrudeia.feature.receipt.domain.entity.ReceiptDetailUseCaseEntity
import com.arrudeia.feature.receipt.domain.entity.ReceiptUseCase
import com.arrudeia.feature.receipt.presentation.model.IngredientReceiptDetailUiModel
import com.arrudeia.feature.receipt.presentation.model.ReceiptDetailUiModel
import com.arrudeia.feature.receipt.presentation.model.ReceiptUIModel

fun ReceiptUseCase?.mapToUiModel(): ReceiptUIModel? {
    var listResult: ReceiptUIModel? = null
    this?.let {
        listResult = ReceiptUIModel(
            uuid = it.uuid,
            name = it.name,
            time = it.time,
            quantity = it.quantity,
            img = it.img,
            level = it.level
        )
    }
    return listResult
}


fun ReceiptDetailUseCaseEntity.toEntity(): ReceiptDetailUiModel {
    return ReceiptDetailUiModel(
            description = this.description,
            name = this.name,
            time = this.time,
            quantity = this.quantity,
            img = this.img,
            level = this.level,
            ingredients = this.ingredients.toEntity(),
            urlVideo = this.urlVideo
        )
}

fun List<IngredientReceiptDetailUseCaseEntity?>?.toEntity(): List<IngredientReceiptDetailUiModel?>? {
    return if (this == null) null
    else {
        val list = mutableListOf<IngredientReceiptDetailUiModel>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        IngredientReceiptDetailUiModel(
                            id = item.id,
                            recipeId = item.recipeId,
                            step = item.step,
                        )
                    )
                }
            }
        }
        list.toList()
    }
}


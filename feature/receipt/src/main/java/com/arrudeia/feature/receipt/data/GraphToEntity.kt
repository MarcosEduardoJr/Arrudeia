package com.arrudeia.feature.receipt.data

import com.arrudeia.core.graphql.GetRecipeDetailQuery
import com.arrudeia.core.graphql.GetRecipesQuery
import com.arrudeia.feature.receipt.data.entity.IngredientReceiptDetailRepositoryEntity
import com.arrudeia.feature.receipt.data.entity.ReceiptDetailRepositoryEntity
import com.arrudeia.feature.receipt.data.entity.ReceiptRepositoryEntity

fun List<GetRecipesQuery.Recipe?>?.toRecipeRepositoryEntity(): List<ReceiptRepositoryEntity?>? {
    return if (this == null) null
    else {
        val list = mutableListOf<ReceiptRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        ReceiptRepositoryEntity(
                            uuid = item.uuid,
                            name = item.name,
                            time = item.time,
                            quantity = item.quantity,
                            img = item.img,
                            level = item.level
                        )
                    )
                }
            }
        }
        list.toList()
    }
}

fun GetRecipeDetailQuery.Recipe?.toReceiptDetailRepositoryEntity(): ReceiptDetailRepositoryEntity? {
    return if (this == null) null
    else {
        ReceiptDetailRepositoryEntity(
            description = this.description,
            name = this.name,
            time = this.time,
            quantity = this.quantity,
            img = this.img,
            level = this.level,
            ingredients = this.ingredients.toIngredientReceiptDetailRepositoryEntity(),
            urlVideo = this.urlVideo
        )

    }
}

fun List<GetRecipeDetailQuery.Ingredient?>?.toIngredientReceiptDetailRepositoryEntity(): List<IngredientReceiptDetailRepositoryEntity?>? {
    return if (this == null) null
    else {
        val list = mutableListOf<IngredientReceiptDetailRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        IngredientReceiptDetailRepositoryEntity(
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

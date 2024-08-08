package com.arrudeia.feature.receipt.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.receipt.R
import com.arrudeia.feature.receipt.data.entity.IngredientReceiptDetailRepositoryEntity
import com.arrudeia.feature.receipt.data.entity.ReceiptDetailRepositoryEntity
import com.arrudeia.feature.receipt.data.entity.ReceiptRepositoryEntity
import com.arrudeia.feature.receipt.domain.entity.IngredientReceiptDetailUseCaseEntity
import com.arrudeia.feature.receipt.domain.entity.ReceiptDetailUseCaseEntity
import com.arrudeia.feature.receipt.domain.entity.ReceiptUseCase
import com.arrudeia.core.common.R.string.generic_error

fun Result<ReceiptDetailRepositoryEntity?>.toEntity(): Result<ReceiptDetailUseCaseEntity?> {
    var item: Result<ReceiptDetailUseCaseEntity?> =
        Result.Error(generic_error)
    when (this) {
        is Result.Success -> {
            this.data?.let {
                item = Result.Success(
                    ReceiptDetailUseCaseEntity(
                        description = it.description,
                        name = it.name,
                        time = it.time,
                        quantity = it.quantity,
                        img = it.img,
                        level = it.level,
                        ingredients = it.ingredients.toEntity(),
                        urlVideo = it.urlVideo
                    )
                )
            }
        }

        is Result.Error -> {
            item = Result.Error(this.message)
        }

        else -> {
            item = Result.Error(generic_error)
        }
    }

    return item
}

fun List<IngredientReceiptDetailRepositoryEntity?>?.toEntity(): List<IngredientReceiptDetailUseCaseEntity?>? {
    return if (this == null) null
    else {
        val list = mutableListOf<IngredientReceiptDetailUseCaseEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        IngredientReceiptDetailUseCaseEntity(
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


fun Result<List<ReceiptRepositoryEntity?>?>.mapToUseCaseEntity(): Result<List<ReceiptUseCase?>?> {
    var item: Result<List<ReceiptUseCase>?> =
        Result.Error(generic_error)
    when (this) {
        is Result.Success -> {
            Result.Success(this.data?.let {
                val list = mutableListOf<ReceiptUseCase>()
                it.forEach {
                    list.add(
                        ReceiptUseCase(
                            uuid = it?.uuid,
                            name = it?.name,
                            time = it?.time,
                            quantity = it?.quantity,
                            img = it?.img,
                            level = it?.level
                        )
                    )
                }
                item = Result.Success(
                    list
                )
            })
        }

        is Result.Error -> {
            item = Result.Error(this.message)
        }

        else -> {
            item = Result.Error(generic_error)
        }
    }
    return item
}


package com.arrudeia.feature.aid.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.aid.R
import com.arrudeia.feature.aid.data.entity.AidDetailRepositoryEntity
import com.arrudeia.feature.aid.data.entity.AidRepositoryEntity
import com.arrudeia.feature.aid.data.entity.AidStepRepositoryEntity
import com.arrudeia.feature.aid.domain.entity.AidDetailUseCaseEntity
import com.arrudeia.feature.aid.domain.entity.AidStepUseCaseEntity
import com.arrudeia.feature.aid.domain.entity.AidUseCaseEntity

fun Result<AidDetailRepositoryEntity?>.toEntity(): Result<AidDetailUseCaseEntity?> {
    var item: Result<AidDetailUseCaseEntity?> =
        Result.Error(R.string.erro_message_list_travels)
    when (this) {
        is Result.Success -> {
            this.data?.let {
                item = Result.Success(
                    AidDetailUseCaseEntity(
                        description = it.description,
                        name = it.name,
                        img = it.img,
                        id = it.id,
                        steps = it.steps.toEntity(),
                        urlVideo = it.urlVideo
                    )
                )
            }
        }

        is Result.Error -> {
            item = Result.Error(this.message)
        }

        else -> {
            item = Result.Error(R.string.erro_message_list_travels)
        }
    }

    return item
}

fun List<AidStepRepositoryEntity?>?.toEntity(): List<AidStepUseCaseEntity?>? {
    return if (this == null) null
    else {
        val list = mutableListOf<AidStepUseCaseEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        AidStepUseCaseEntity(
                            id = item.id,
                            aidId = item.aidId,
                            step = item.step,
                        )
                    )
                }
            }
        }
        list.toList()
    }
}


fun Result<List<AidRepositoryEntity?>?>.mapToUseCaseEntity(): Result<List<AidUseCaseEntity?>?> {
    var item: Result<List<AidUseCaseEntity>?> =
        Result.Error(R.string.erro_message_list_travels)
    when (this) {
        is Result.Success -> {
            Result.Success(this.data?.let { it ->
                val list = mutableListOf<AidUseCaseEntity>()
                it.forEach { aid ->
                    list.add(
                        AidUseCaseEntity(
                            id = aid?.id,
                            name = aid?.name,
                            description = aid?.description,
                            urlVideo = aid?.urlVideo,
                            img = aid?.img,
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
            item = Result.Error(R.string.erro_message_list_travels)
        }
    }
    return item
}


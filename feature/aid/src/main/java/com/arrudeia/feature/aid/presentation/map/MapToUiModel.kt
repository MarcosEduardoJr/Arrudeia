package com.arrudeia.feature.aid.presentation.map

import com.arrudeia.feature.aid.domain.entity.AidDetailUseCaseEntity
import com.arrudeia.feature.aid.domain.entity.AidStepUseCaseEntity
import com.arrudeia.feature.aid.domain.entity.AidUseCaseEntity
import com.arrudeia.feature.aid.domain.toEntity
import com.arrudeia.feature.aid.presentation.model.AidDetailUiModel
import com.arrudeia.feature.aid.presentation.model.AidStepReceiptDetailUiModel
import com.arrudeia.feature.aid.presentation.model.AidUIModel

fun AidUseCaseEntity?.mapToUiModel(): AidUIModel? {
    var listResult: AidUIModel? = null
    this?.let { aid ->
        listResult = AidUIModel(
            id = aid?.id,
            name = aid?.name,
            description = aid?.description,
            urlVideo = aid?.urlVideo,
            img = aid?.img,
        )
    }
    return listResult
}


fun AidDetailUseCaseEntity.toEntity(): AidDetailUiModel {
    return AidDetailUiModel(
        description = this.description,
        name = this.name,
        img = this.img,
        id = this.id,
        steps = this.steps.toEntity(),
        urlVideo = this.urlVideo
    )
}

fun List<AidStepUseCaseEntity?>?.toEntity(): List<AidStepReceiptDetailUiModel?>? {
    return if (this == null) null
    else {
        val list = mutableListOf<AidStepReceiptDetailUiModel>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        AidStepReceiptDetailUiModel(
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


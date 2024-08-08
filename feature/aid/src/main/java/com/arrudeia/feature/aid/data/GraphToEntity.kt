package com.arrudeia.feature.aid.data

import com.arrudeia.core.graphql.GetAidDetailQuery
import com.arrudeia.core.graphql.GetAidsQuery
import com.arrudeia.feature.aid.data.entity.AidDetailRepositoryEntity
import com.arrudeia.feature.aid.data.entity.AidRepositoryEntity
import com.arrudeia.feature.aid.data.entity.AidStepRepositoryEntity

fun List<GetAidsQuery.Aid?>?.toAidsRepositoryEntity(): List<AidRepositoryEntity?>? {
    return if (this == null) null
    else {
        val list = mutableListOf<AidRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        AidRepositoryEntity(
                            id = item.id,
                            name = item.name,
                            description = item.description,
                            urlVideo = item.urlVideo,
                            img = item.img,
                        )
                    )
                }
            }
        }
        list.toList()
    }
}

fun GetAidDetailQuery.Aid?.toAidDetailRepositoryEntity(): AidDetailRepositoryEntity? {
    return if (this == null) null
    else {
        AidDetailRepositoryEntity(
            description = this.description,
            name = this.name,
            img = this.img,
            id = this.id,
            steps = this.steps.toAidStepsRepositoryEntity(),
            urlVideo = this.urlVideo
        )

    }
}

fun List<GetAidDetailQuery.Step?>?.toAidStepsRepositoryEntity(): List<AidStepRepositoryEntity?>? {
    return if (this == null) null
    else {
        val list = mutableListOf<AidStepRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        AidStepRepositoryEntity(
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

package com.arrudeia.feature.checklist.data.entity

import com.arrudeia.core.graphql.CheckListGraphQuery

fun List<CheckListGraphQuery.Checklist?>?.toEntity(): List<CheckListRepositoryEntity>? {
    return if (this == null) null
    else {
        val list = mutableListOf<CheckListRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        CheckListRepositoryEntity(
                            name = item.name.orEmpty(),
                        )
                    )
                }
            }
        }
        list.toList()
    }
}
package com.arrudeia.feature.checklist.data.entity

import kotlinx.serialization.Serializable

@Serializable
@Suppress("ConstructorParameterNaming")
data class CheckListRepositoryEntity(
    val name: String = "",
)

package com.arrudeia.feature.checklist.presentation.map

import com.arrudeia.feature.checklist.domain.entity.CheckListUseCaseEntity
import com.arrudeia.feature.checklist.presentation.model.CheckListUIModel

fun CheckListUseCaseEntity?.mapToCheckListUiModel(): CheckListUIModel? {
    var listResult: CheckListUIModel? = null
    this?.let {
        listResult = CheckListUIModel(
            name = it.name,
        )
    }
    return listResult
}
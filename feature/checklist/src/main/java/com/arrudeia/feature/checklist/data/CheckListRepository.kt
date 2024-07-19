package com.arrudeia.feature.checklist.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.checklist.data.entity.CheckListRepositoryEntity

interface CheckListRepository {
    suspend fun getChecklist(): Result<List<CheckListRepositoryEntity>?>
}

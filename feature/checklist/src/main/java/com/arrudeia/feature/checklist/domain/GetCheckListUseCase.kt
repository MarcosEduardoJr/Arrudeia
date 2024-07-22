package com.arrudeia.feature.checklist.domain

import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.core.result.Result
import com.arrudeia.feature.checklist.data.CheckListRepositoryImpl
import com.arrudeia.feature.checklist.data.entity.CheckListRepositoryEntity
import com.arrudeia.feature.checklist.domain.entity.CheckListUseCaseEntity
import javax.inject.Inject

class GetCheckListUseCase @Inject constructor(
    private val repository: CheckListRepositoryImpl,
) {
    suspend operator fun invoke():
            Result<List<CheckListUseCaseEntity>?> =
        repository.getChecklist().mapToUseCaseEntity()

    private fun Result<List<CheckListRepositoryEntity>?>.mapToUseCaseEntity(): Result<List<CheckListUseCaseEntity>?> {
        var item: Result<List<CheckListUseCaseEntity>?> =
            Result.Error(generic_error)
        when (this) {
            is Result.Success -> {
                Result.Success(this.data?.let {
                    val list = mutableListOf<CheckListUseCaseEntity>()
                    it.forEach {
                        list.add(
                            CheckListUseCaseEntity(
                                name = it.name,
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
}

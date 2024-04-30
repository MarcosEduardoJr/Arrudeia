package com.arrudeia.feature.profile.domain

import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserAddressUseCaseEntity
import javax.inject.Inject

import com.arrudeia.core.result.Result
import com.arrudeia.feature.profile.domain.map.toRepositoryEntity

class UpdateUserAddressUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl
) {
    suspend operator fun invoke(user: UserAddressUseCaseEntity): Result<String?> {
        val entity = user.toRepositoryEntity()
        return repository.updateUserAddress(entity)
    }
}

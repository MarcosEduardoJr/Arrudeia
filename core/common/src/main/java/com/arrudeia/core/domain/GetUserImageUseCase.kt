package com.arrudeia.core.domain

import com.arrudeia.core.data.repository.ProfileDataStoreUserRepositoryImpl
import javax.inject.Inject

class GetUserImageUseCase @Inject constructor(
    private val repository: ProfileDataStoreUserRepositoryImpl
) {
    suspend operator fun invoke() = repository.getImageUser()

}



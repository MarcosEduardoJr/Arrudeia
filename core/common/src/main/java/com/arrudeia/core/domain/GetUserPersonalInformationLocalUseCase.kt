package com.arrudeia.core.domain

import com.arrudeia.core.data.repository.ProfileDataStoreUserRepositoryImpl
import javax.inject.Inject

class GetUserPersonalInformationLocalUseCase @Inject constructor(
    private val repository: ProfileDataStoreUserRepositoryImpl
) {
    suspend operator fun invoke() = repository.getUserData()

}



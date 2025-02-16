package com.arrudeia.feature.profile.domain
import com.arrudeia.core.data.repository.ProfileDataStoreUserRepository
import javax.inject.Inject

class LogoutCurrentUserDataStoreUseCase @Inject constructor(
    private val repository: ProfileDataStoreUserRepository,
) {
    suspend operator fun invoke() {
        repository.logoutUser()
    }
}

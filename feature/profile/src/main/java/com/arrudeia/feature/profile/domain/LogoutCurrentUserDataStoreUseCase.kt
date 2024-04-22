package com.arrudeia.feature.profile.domain
import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepository
import javax.inject.Inject

class LogoutCurrentUserDataStoreUseCase @Inject constructor(
    private val repository: ProfileDataStoreUserRepository,
) {
    suspend operator fun invoke() {
        repository.logoutUser()
    }
}

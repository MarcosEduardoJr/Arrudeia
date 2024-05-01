package com.arrudeia.feature.profile.domain

import android.net.Uri
import com.arrudeia.core.result.Result
import com.arrudeia.feature.profile.data.FirebaseUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.data.entity.ProfileDataStoreUserRepositoryEntity
import com.arrudeia.feature.profile.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserPersonalInformationUseCaseEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull

class UpdateUserPersonalInformationUseCaseTest {

    private val repository = mock(ProfileRepositoryImpl::class.java)
    private val repositoryDataStore = mock(ProfileDataStoreUserRepositoryImpl::class.java)
    private val firebaseUserRepositoryImpl = mock(FirebaseUserRepositoryImpl::class.java)
    private val useCase = UpdateUserPersonalInformationUseCase(
        repository,
        repositoryDataStore,
        firebaseUserRepositoryImpl
    )
    @Test
    fun `invoke calls repository with correct parameters`() = runBlocking {
        val user = UserPersonalInformationUseCaseEntity(
            uuid = "testUUID",
            name = "testName",
            email = "testEmail",
            phone = "testPhone",
            idDocument = "testIdDocument",
            birthDate = "testBirthDate",
            profileImage = "testProfileImage"
        )

        val expectedEntity = UserPersonalInformationRepositoryEntity(
            uuid = user.uuid,
            name = user.name,
            email = user.email,
            phone = user.phone,
            idDocument = user.idDocument,
            birthDate = user.birthDate,
            profileImage = user.profileImage
        )

        val expectedDataStoreEntity = ProfileDataStoreUserRepositoryEntity(
            uid = user.uuid.orEmpty(),
            name = user.name.orEmpty(),
            email = user.email.orEmpty(),
            image = user.profileImage.orEmpty()
        )

        `when`(repository.updateUserPersonalInformation(expectedEntity)).thenReturn(Result.Success("Success"))
        `when`(firebaseUserRepositoryImpl.saveUserImage(anyOrNull())).thenReturn("testProfileImage")
        val result = useCase(user, mock(Uri::class.java))

        verify(repository).updateUserPersonalInformation(expectedEntity)
        verify(repositoryDataStore).saveUser(expectedDataStoreEntity)
        assertTrue(result is Result.Success)
        assertEquals("Success", (result as Result.Success).data)
    }
}
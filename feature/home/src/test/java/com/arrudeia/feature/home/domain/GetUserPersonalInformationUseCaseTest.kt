package com.arrudeia.feature.home.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.home.data.HomeProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.home.data.HomeProfileRepositoryImpl
import com.arrudeia.feature.home.data.entity.ProfileDataStoreUserRepositoryEntity
import com.arrudeia.feature.home.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.feature.home.domain.entity.UserPersonalInformationUseCaseEntity
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

class GetUserPersonalInformationUseCaseTest {

    private val repository = mock(HomeProfileRepositoryImpl::class.java)
    private val repositoryDataStore = mock(HomeProfileDataStoreUserRepositoryImpl::class.java)
    private val useCase = GetUserPersonalInformationUseCase(repository, repositoryDataStore)

    @Test
    fun `invoke calls repository`() = runBlocking {
        val userData = ProfileDataStoreUserRepositoryEntity(uid = "testUid")
        val userPersonalInformation = UserPersonalInformationRepositoryEntity(
            uuid = "testUuid",
            name = "testName",
            email = "testEmail",
            phone = "testPhone",
            idDocument = "testIdDocument",
            birthDate = "testBirthDate",
            profileImage = "testProfileImage"
        )

        `when`(repositoryDataStore.getUserData()).thenReturn(Result.Success(userData))
        `when`(repository.getUserPersonalInformationDetails(anyString())).thenReturn(Result.Success(userPersonalInformation))

        val result = useCase()

        verify(repositoryDataStore).getUserData()
        verify(repository).getUserPersonalInformationDetails(anyString())
        assertTrue(result is Result.Success)
        result as Result.Success
        assertEquals("testUuid", result.data?.uuid)
        assertEquals("testName", result.data?.name)
        assertEquals("testEmail", result.data?.email)
        assertEquals("testPhone", result.data?.phone)
        assertEquals("testIdDocument", result.data?.idDocument)
        assertEquals("testBirthDate", result.data?.birthDate)
        assertEquals("testProfileImage", result.data?.profileImage)
    }
}
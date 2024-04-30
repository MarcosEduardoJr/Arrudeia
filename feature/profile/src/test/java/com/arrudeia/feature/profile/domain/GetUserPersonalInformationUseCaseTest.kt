package com.arrudeia.feature.profile.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.feature.profile.data.entity.ProfileDataStoreUserRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserPersonalInformationUseCaseEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetUserPersonalInformationUseCaseTest {

    private lateinit var useCase: GetUserPersonalInformationUseCase
    private val mockRepository: ProfileRepositoryImpl = mock(ProfileRepositoryImpl::class.java)
    private val mockDataStore: ProfileDataStoreUserRepositoryImpl =
        mock(ProfileDataStoreUserRepositoryImpl::class.java)

    @Before
    fun setup() {
        useCase = GetUserPersonalInformationUseCase(mockRepository, mockDataStore)
    }

    @Test
    fun `invoke returns UserPersonalInformationUseCaseEntity on success`() = runBlocking {
        val uid = "testUid"
        val userPersonalInformation = UserPersonalInformationRepositoryEntity(
            "uuid",
            "name",
            "email",
            "phone",
            "idDocument",
            "birthDate",
            "profileImage"
        )
        val expected = Result.Success(
            UserPersonalInformationUseCaseEntity(
                "uuid",
                "name",
                "email",
                "phone",
                "idDocument",
                "birthDate",
                "profileImage"
            )
        )

        `when`(mockDataStore.getUserData()).thenReturn(ProfileDataStoreUserRepositoryEntity(uid))
        `when`(mockRepository.getUserPersonalInformationDetails(uid)).thenReturn(Result.Success(userPersonalInformation))

        val result = useCase.invoke()

        assertEquals(expected, result)
    }

    @Test
    fun `invoke returns error when getUserData is null`() = runBlocking {

        `when`(mockDataStore.getUserData()).thenReturn(null)
        `when`(mockRepository.getUserPersonalInformationDetails(String())).thenReturn(Result.Error(R.string.error_get_user))

        val result = useCase.invoke()

        assertTrue(result is Result.Error)
    }

    @Test
    fun `invoke returns error when getUserPersonalInformationDetails returns error`() = runBlocking {
        val user = ProfileDataStoreUserRepositoryEntity("testUid")
        `when`(mockDataStore.getUserData()).thenReturn(user)
        `when`(mockRepository.getUserPersonalInformationDetails(user.uid)).thenReturn(Result.Error(R.string.error_get_user))

        val result = useCase.invoke()

        assertTrue(result is Result.Error)
        assertEquals(R.string.error_get_user, (result as Result.Error).message)
    }
}
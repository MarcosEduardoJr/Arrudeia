package com.arrudeia.feature.profile.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.data.entity.ProfileDataStoreUserRepositoryEntity
import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserAddressUseCaseEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetUserAddressUseCaseTest {

    private lateinit var useCase: GetUserAddressUseCase
    private val mockRepository: ProfileRepositoryImpl = mock(ProfileRepositoryImpl::class.java)
    private val mockDataStore: ProfileDataStoreUserRepositoryImpl =
        mock(ProfileDataStoreUserRepositoryImpl::class.java)

    @Before
    fun setup() {
        useCase = GetUserAddressUseCase(mockRepository, mockDataStore)
    }

    @Test
    fun `invoke returns UserAddressUseCaseEntity on success`() = runBlocking {
        val uid = "testUid"
        val user = ProfileDataStoreUserRepositoryEntity(uid, "testName", "testEmail", "testImage")
        val address = UserAddressRepositoryEntity(
            "uuid",
            "zipCode",
            "street",
            0,
            "district",
            "city",
            "state",
            "country"
        )
        val expected = Result.Success(
            UserAddressUseCaseEntity(
                "uuid",
                "zipCode",
                "street",
                0,
                "district",
                "city",
                "state",
                "country"
            )
        )

        `when`(mockDataStore.getUserData()).thenReturn(user)
        `when`(mockRepository.getUserAddress(uid)).thenReturn(Result.Success(address))

        val result = useCase.invoke()

        assertEquals(expected, result)
    }

    @Test
    fun `invoke returns error when getUserData is null`() = runBlocking {

        `when`(mockDataStore.getUserData()).thenReturn(null)
        `when`(mockRepository.getUserAddress(String())).thenReturn(Result.Error(R.string.error_get_user))

        val result = useCase()

        assertTrue(result is Result.Error)
    }

    @Test
    fun `invoke returns error when getUserAddress returns error`() = runBlocking {
        val user =
            ProfileDataStoreUserRepositoryEntity("testUid", "testName", "testEmail", "testImage")
        `when`(mockDataStore.getUserData()).thenReturn(user)
        `when`(mockRepository.getUserAddress(user.uid)).thenReturn(Result.Error(R.string.error_get_user))


        val result = useCase()

        assertTrue(result is Result.Error)
        assertEquals(R.string.error_get_user, (result as Result.Error).message)
    }
}
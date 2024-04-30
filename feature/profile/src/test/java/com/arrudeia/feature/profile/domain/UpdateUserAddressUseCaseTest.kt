package com.arrudeia.feature.profile.domain

import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.domain.entity.UserAddressUseCaseEntity
import com.arrudeia.core.result.Result
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

class UpdateUserAddressUseCaseTest {

    private val repository = mock(ProfileRepositoryImpl::class.java)
    private val useCase = UpdateUserAddressUseCase(repository)

    @Test
    fun `invoke calls repository with correct parameters`() = runBlocking {
        val user = UserAddressUseCaseEntity(
            uuid = "testUUID",
            zipCode = "testZipCode",
            street = "testStreet",
            number = 0,
            district = "testDistrict",
            city = "testCity",
            state = "testState",
            country = "testCountry"
        )

        val expectedEntity = UserAddressRepositoryEntity(
            uuid = user.uuid,
            zipCode = user.zipCode,
            street = user.street,
            number = user.number,
            district = user.district,
            city = user.city,
            state = user.state,
            country = user.country
        )

        `when`(repository.updateUserAddress(expectedEntity)).thenReturn(Result.Success("Success"))

        val result = useCase(user)

        verify(repository).updateUserAddress(expectedEntity)
        assertTrue(result is Result.Success)
        assertEquals("Success", (result as Result.Success).data)
    }
}
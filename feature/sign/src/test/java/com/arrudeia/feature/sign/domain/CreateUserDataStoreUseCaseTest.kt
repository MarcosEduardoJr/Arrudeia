package com.arrudeia.feature.sign.domain

import com.arrudeia.core.data.repository.ProfileRepositoryImpl
import com.arrudeia.core.data.repository.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.sign.domain.CreateUserDataStoreUseCase
import com.arrudeia.core.sign.data.SignDataStoreUserRepository
import com.arrudeia.feature.sign.data.entity.SignDataStoreUserRepositoryEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import com.arrudeia.core.result.Result.Success

class CreateUserDataStoreUseCaseTest {

    private lateinit var repository: SignDataStoreUserRepository
    private lateinit var useCase: CreateUserDataStoreUseCase
    private lateinit var repositoryProfile: ProfileRepositoryImpl

    @Before
    fun setup() {
        repository = mockk()
        repositoryProfile = mockk()
        useCase = CreateUserDataStoreUseCase(repository, repositoryProfile)
    }

    @Test
    fun `invoke calls repository saveUser`() = runBlocking {
        val uid = "testUid"
        val name = "testName"
        val email = "testEmail"


        coEvery { repositoryProfile.getUserPersonalInformationDetails(uid) } returns Success(
            UserPersonalInformationRepositoryEntity("", "", "", "", "", "", "")
        )
        coEvery {
            repository.saveUser(
                SignDataStoreUserRepositoryEntity(
                    uid,
                    name,
                    email
                )
            )
        } returns true

        val result = useCase(uid, name, email)

        Assert.assertTrue(result)
    }
}
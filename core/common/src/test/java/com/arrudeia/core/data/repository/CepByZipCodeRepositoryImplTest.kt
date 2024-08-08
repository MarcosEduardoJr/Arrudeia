package com.arrudeia.core.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.arrudeia.core.data.repository.map.toCepAddressRepositoryEntity
import com.arrudeia.core.graphql.GetCepAddressQuery
import com.arrudeia.core.result.Result.Success
import com.arrudeia.core.result.Result.Error
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.anyString

class CepByZipCodeRepositoryImplTest {

    private lateinit var apolloClient: ApolloClient
    private lateinit var repository: CepByZipCodeRepositoryImpl

    @Before
    fun setUp() {
        apolloClient = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()
        repository = CepByZipCodeRepositoryImpl(apolloClient)
    }


    @Test
    fun `getAddressByZipCode returns Success when data is available`() = runBlocking {

        val testQuery = GetCepAddressQuery(anyString())
        val testData = GetCepAddressQuery.Data(
            GetCepAddressQuery.CepAddress(
                zipCode = anyString(),
                street = anyString(),
                district = anyString(),
                city = anyString(),
                country = anyString(),
                state = anyString()
            )
        )
        apolloClient.enqueueTestResponse(testQuery, testData)
        val result = repository.getAddressByZipCode(anyString())
        val expected = Success(testData.cepAddress?.toCepAddressRepositoryEntity())
        assertTrue(result == expected)
    }

    @Test
    fun `getAddressByZipCode returns Error when data has errors`() = runBlocking {
        val testQuery = GetCepAddressQuery(anyString())
        val testData = GetCepAddressQuery.Data(
            null
        )
        apolloClient.enqueueTestResponse(testQuery, testData)
        val result = repository.getAddressByZipCode(anyString())
        val expected = Error(null)
        assertTrue(result == expected)
        assertTrue(result is Error)
    }
}
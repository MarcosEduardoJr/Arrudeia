package com.arrudeia.core.network.retrofit.service


import com.arrudeia.core.network.model.NetworkArrudeiaTv
import com.arrudeia.core.network.model.NetworkTravel
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitArrudeiaNetworkService {

    @GET(value = "uc")
    suspend fun getAllTravels(
        @Query("id") id: String?,
        @Query("export") exportType: String?,
    ): Response<List<NetworkTravel>>

    @GET(value = "uc")
    suspend fun getAllArrudeiaTv(
        @Query("id") id: String?,
        @Query("export") exportType: String?,
    ): Response<List<NetworkArrudeiaTv>>


}

@Serializable
data class NetworkResponse<T>(
    val data: T,
)
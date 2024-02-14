package com.arrudeia.core.network.retrofit.service


import com.arrudeia.core.network.model.NetworkArrudeiaTv
import com.arrudeia.core.network.model.NetworkArrudeiaTvListImages
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
    suspend fun getAllArrudeiaTvIds(
        @Query("id") id: String?,
        @Query("export") exportType: String?,
    ): Response<List<NetworkArrudeiaTv>>

    @GET(value = "uc")
    suspend fun getAllArrudeiaTvListById(
        @Query("id") id: String?,
        @Query("export") exportType: String?,
    ): Response<List<NetworkArrudeiaTvListImages>>


}

@Serializable
data class NetworkResponse<T>(
    val data: T,
)
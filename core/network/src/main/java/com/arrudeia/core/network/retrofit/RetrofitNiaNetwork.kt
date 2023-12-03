package com.arrudeia.core.network.retrofit

import com.arrudeia.core.network.BuildConfig
import com.arrudeia.core.network.NiaNetworkDataSource
import com.arrudeia.core.network.model.NetworkChangeList
import com.arrudeia.core.network.model.NetworkNewsResource
import com.arrudeia.core.network.model.NetworkTopic
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for NIA Network API
 */
private interface RetrofitNiaNetworkApi {
    @GET(value = "topics")
    suspend fun getTopics(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<NetworkTopic>>

    @GET(value = "newsresources")
    suspend fun getNewsResources(
        @Query("id") ids: List<String>?,
    ): NetworkResponse<List<NetworkNewsResource>>

    @GET(value = "changelists/topics")
    suspend fun getTopicChangeList(
        @Query("after") after: Int?,
    ): List<NetworkChangeList>

    @GET(value = "changelists/newsresources")
    suspend fun getNewsResourcesChangeList(
        @Query("after") after: Int?,
    ): List<NetworkChangeList>
}

private const val NIA_BASE_URL = BuildConfig.BACKEND_URL

/**
 * Wrapper for data provided from the [NIA_BASE_URL]
 */
@Serializable
private data class NetworkResponse<T>(
    val data: T,
)

/**
 * [Retrofit] backed [NiaNetworkDataSource]
 */
@Singleton
class RetrofitNiaNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NiaNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(NIA_BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitNiaNetworkApi::class.java)

    override suspend fun getTopics(ids: List<String>?): List<NetworkTopic> =
        networkApi.getTopics(ids = ids).data

    override suspend fun getNewsResources(ids: List<String>?): List<NetworkNewsResource> =
        networkApi.getNewsResources(ids = ids).data

    override suspend fun getTopicChangeList(after: Int?): List<NetworkChangeList> =
        networkApi.getTopicChangeList(after = after)

    override suspend fun getNewsResourceChangeList(after: Int?): List<NetworkChangeList> =
        networkApi.getNewsResourcesChangeList(after = after)
}

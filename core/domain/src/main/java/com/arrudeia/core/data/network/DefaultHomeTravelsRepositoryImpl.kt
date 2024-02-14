package com.arrudeia.core.data.network

import com.arrudeia.core.data.interactions.Result
import com.arrudeia.core.data.repository.ArrudeiaTvRepositoryEntity
import com.arrudeia.core.data.repository.DefaultHomeTravelsRepository
import com.arrudeia.core.data.repository.StoriesRepositoryEntity
import com.arrudeia.core.data.repository.TravelRepositoryEntity
import com.arrudeia.core.network.ArrudeiaDispatchers
import com.arrudeia.core.network.BuildConfig
import com.arrudeia.core.network.Dispatcher
import com.arrudeia.core.network.JvmUnitTestFakeAssetManager
import com.arrudeia.core.network.fake.FakeAssetManager
import com.arrudeia.core.network.model.ArrudeiaNetworkError
import com.arrudeia.core.network.model.NetworkArrudeiaTv
import com.arrudeia.core.network.model.NetworkTravel
import com.arrudeia.core.network.retrofit.service.RetrofitArrudeiaNetworkService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject


class DefaultHomeTravelsRepositoryImpl @Inject constructor(
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
    private val networkJson: Json,
    @Dispatcher(ArrudeiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : DefaultHomeTravelsRepository {


    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getAllTravels():  List<TravelRepositoryEntity>? =
        withContext(ioDispatcher) {
            assets.open(HOME_TRAVELS_ASSET).use(networkJson::decodeFromStream)
        }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getAllArrudeiaTv(): List<ArrudeiaTvRepositoryEntity>?  =
        withContext(ioDispatcher) {
            assets.open(ARRUDEIA_TV_ASSET).use(networkJson::decodeFromStream)
        }

    override suspend fun getTravelById(id: Long): TravelRepositoryEntity? {
        var result: TravelRepositoryEntity? = null
        getAllTravels()?.forEach { if (it.id == id) result = it }
        return result
    }

    companion object {
        private const val HOME_TRAVELS_ASSET = "home_travel.json"
        private const val ARRUDEIA_TV_ASSET = "arrudeia_tv_group_id_list.json"
    }
}



package com.arrudeia.core.data.network

import com.arrudeia.core.data.interactions.Result
import com.arrudeia.core.data.repository.StoriesRepository
import com.arrudeia.core.data.repository.StoriesRepositoryEntity
import com.arrudeia.core.data.repository.StoryRepositoryEntity
import com.arrudeia.core.data.repository.TravelRepositoryEntity
import com.arrudeia.core.network.ArrudeiaDispatchers
import com.arrudeia.core.network.BuildConfig
import com.arrudeia.core.network.Dispatcher
import com.arrudeia.core.network.JvmUnitTestFakeAssetManager
import com.arrudeia.core.network.fake.FakeAssetManager
import com.arrudeia.core.network.model.ArrudeiaNetworkError
import com.arrudeia.core.network.model.NetworkArrudeiaTvListImages
import com.arrudeia.core.network.retrofit.service.RetrofitArrudeiaNetworkService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class StoriesRepositoryImpl @Inject constructor(
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
    private val networkJson: Json,
    @Dispatcher(ArrudeiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    ) : StoriesRepository {



    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getStories():  List<StoriesRepositoryEntity>? =
        withContext(ioDispatcher) {
            assets.open(ARRUDEIA_TV_GROUP_ID_LIST).use(networkJson::decodeFromStream)
        }

    override suspend fun getStoriesById(id: Long): List<StoryRepositoryEntity>? {
        var result: List<StoryRepositoryEntity>? = null
        getStories()?.forEach { if (it.id == id) result = it.images }
        return result
    }


    companion object {
        private const val ARRUDEIA_TV_GROUP_ID_LIST = "arrudeia_tv_item_list.json"
    }
}
package com.arrudeia.core.data.network

import com.arrudeia.core.data.repository.StoriesRepository
import com.arrudeia.core.data.repository.StoriesRepositoryEntity
import com.arrudeia.core.data.repository.StoryRepositoryEntity
import com.arrudeia.core.data.repository.TravelRepositoryEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class StoriesRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : StoriesRepository {

    override suspend fun getStories(): List<StoriesRepositoryEntity> {
        val list = mutableListOf<StoriesRepositoryEntity>()
        return suspendCancellableCoroutine { continuation ->
            db.collection(ARRUDEIA_TV).get()
                .addOnSuccessListener { snapshot ->
                    snapshot.documents.forEach { item ->
                        val result = item.toObject(StoriesRepositoryEntity::class.java)
                        result?.let { list.add(result) }
                    }
                    continuation.resume(list)
                }
                .addOnFailureListener { exception ->
                    continuation.resume(listOf())
                }
        }
    }

    override suspend fun getStoriesById(id: Long): List<StoryRepositoryEntity> {
        var result = mutableListOf<StoryRepositoryEntity>()
        getStories().forEach { if (it.id == id && it.images.isNullOrEmpty()) result = it.images!!.toMutableList() }
        return result
    }


    companion object {
        const val ARRUDEIA_TV = "arrudeia_tv"
    }
}
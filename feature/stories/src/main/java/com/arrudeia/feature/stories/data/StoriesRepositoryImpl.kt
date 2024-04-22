package com.arrudeia.feature.stories.data

import com.arrudeia.feature.stories.data.entity.StoriesRepositoryEntity
import com.arrudeia.feature.stories.data.entity.StoryRepositoryEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

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
        getStories().forEach { if (it.id == id && !it.images.isNullOrEmpty()) result = it.images!!.toMutableList() }
        return result
    }


    companion object {
        const val ARRUDEIA_TV = "arrudeia_tv"
    }
}
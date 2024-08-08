package com.arrudeia.feature.stories.data

import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.core.result.Result
import com.arrudeia.feature.stories.data.entity.StoriesRepositoryEntity
import com.arrudeia.feature.stories.data.entity.StoryRepositoryEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class StoriesRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : StoriesRepository {

    override suspend fun getStories(): Result<List<StoriesRepositoryEntity>?> {
        val list = mutableListOf<StoriesRepositoryEntity>()
        return suspendCancellableCoroutine { continuation ->
            db.collection(ARRUDEIA_TV).get()
                .addOnSuccessListener { snapshot ->
                    snapshot.documents.forEach { item ->
                        val result = item.toObject(StoriesRepositoryEntity::class.java)
                        result?.let { list.add(result) }
                    }
                    continuation.resume(Result.Success(list))
                }
                .addOnFailureListener {
                    continuation.resume(Result.Error(generic_error))
                }
        }
    }

    override suspend fun getStoriesById(id: Long): Result<List<StoryRepositoryEntity>?> {
        var result = mutableListOf<StoryRepositoryEntity>()
        when (val list = getStories()) {
            is Result.Success -> {
                list.data?.forEach {
                    if (it.id == id && !it.images.isNullOrEmpty())
                        result = it.images.toMutableList()
                }
            }

            is Result.Error, is Result.Loading -> return Result.Error(generic_error)
        }
        return Result.Success(result)
    }


    companion object {
        const val ARRUDEIA_TV = "arrudeia_tv"
    }
}
package com.arrudeia.feature.home.data

import com.arrudeia.feature.home.data.entity.ArrudeiaTvRepositoryEntity
import com.arrudeia.feature.home.data.entity.TravelRepositoryEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


class DefaultHomeTravelsRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : DefaultHomeTravelsRepository {


    override suspend fun getAllTravels(): List<TravelRepositoryEntity> {
        val list = mutableListOf<TravelRepositoryEntity>()
        return suspendCancellableCoroutine { continuation ->
            db.collection(HOME_TRAVELS).get()
                .addOnSuccessListener { snapshot ->
                    snapshot.documents.forEach { item ->
                        val result = item.toObject(TravelRepositoryEntity::class.java)
                        result?.let { list.add(result) }
                    }
                    continuation.resume(list)
                }
                .addOnFailureListener { exception ->
                    continuation.resume(listOf())
                }
        }
    }

    override suspend fun getAllArrudeiaTv(): List<ArrudeiaTvRepositoryEntity> {
        val list = mutableListOf<ArrudeiaTvRepositoryEntity>()
        return suspendCancellableCoroutine { continuation ->
            db.collection(HOME_ARRUDEIA_TV).get()
                .addOnSuccessListener { snapshot ->
                    snapshot.documents.forEach { item ->
                        val result = item.toObject(ArrudeiaTvRepositoryEntity::class.java)
                        result?.let { list.add(result) }
                    }
                    continuation.resume(list)
                }
                .addOnFailureListener { exception ->
                    continuation.resume(listOf())
                }
        }
    }

    companion object {
        const val HOME_TRAVELS = "home_travels"
        const val HOME_ARRUDEIA_TV = "home_arrudeia_tv"
    }
}



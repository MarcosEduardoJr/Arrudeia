package com.arrudeia.feature.trip.data

import com.arrudeia.feature.trip.data.entity.TravelRepositoryEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


class TripTravelRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : TripTravelRepository {


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



    override suspend fun getTravelById(id: Long): TravelRepositoryEntity? {
        var result: TravelRepositoryEntity? = null
        getAllTravels().forEach { if (it.id == id) result = it }
        return result
    }

    companion object {
        const val HOME_TRAVELS = "home_travels"
    }
}



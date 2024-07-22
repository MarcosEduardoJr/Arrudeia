package com.arrudeia.feature.trip.data

import com.arrudeia.feature.trip.data.entity.TravelRepositoryEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import com.arrudeia.core.result.Result
import com.arrudeia.feature.trip.R
import com.arrudeia.core.common.R.string.generic_error

class TripTravelRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : TripTravelRepository {


    override suspend fun getAllTravels(): Result<List<TravelRepositoryEntity>> {
        val list = mutableListOf<TravelRepositoryEntity>()
        return suspendCancellableCoroutine { continuation ->
            db.collection(HOME_TRAVELS).get()
                .addOnSuccessListener { snapshot ->
                    snapshot.documents.forEach { item ->
                        val result = item.toObject(TravelRepositoryEntity::class.java)
                        result?.let { list.add(result) }
                    }
                    continuation.resume(Result.Success(list))
                }
                .addOnFailureListener {
                    continuation.resume(Result.Error(generic_error))
                }
        }
    }


    override suspend fun getTravelById(id: Long): Result<TravelRepositoryEntity?> {
        var item: Result<TravelRepositoryEntity?> =
            Result.Error(generic_error)

        when (val result = getAllTravels()) {
            is Result.Success -> {
                result.data.forEach { travel -> if (travel.id == id) item = Result.Success(travel) }
            }

            is Result.Error -> {
                item = Result.Error(result.message)
            }

            else -> {
                item = Result.Error(generic_error)
            }
        }

        return item
    }

    companion object {
        const val HOME_TRAVELS = "home_travels"
    }
}



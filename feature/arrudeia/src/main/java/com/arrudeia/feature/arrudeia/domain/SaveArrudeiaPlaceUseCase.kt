package com.arrudeia.feature.arrudeia.domain

import android.net.Uri
import com.arrudeia.core.places.data.ArrudeiaPlaceRepositoryImpl
import com.arrudeia.core.places.data.FirebaseArrudeiaMapRepositoryImpl
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaAvailablePlaceUiModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import com.arrudeia.core.result.Result

class SaveArrudeiaPlaceUseCase @Inject constructor(
    private val repository: ArrudeiaPlaceRepositoryImpl,
    private val firebaseArrudeiaMapRepositoryImpl: FirebaseArrudeiaMapRepositoryImpl,
    private val firebaseAuth: FirebaseAuth,
) {

    suspend operator fun invoke(
        name: String,
        phone: String,
        socialNetwork: String,
        description: String,
        uri: Uri?,
        availables: List<ArrudeiaAvailablePlaceUiModel>?,
        categoryName: String,
        subCategoryName: String,
        location: LatLng,
        priceLevel: Int = 1,
        rating: Int = 1,
        city: String,
        state: String,
        country: String
    ): Result<String?> {
        var image = ""
        uri?.let {
            image = firebaseArrudeiaMapRepositoryImpl.savePlaceImage(name.orEmpty(), it).orEmpty()
        }
        val result = repository.saveArrudeiaPlace(
            categoryName,
            description,
            image,
            latitude = location.latitude,
            longitude = location.longitude,
            name,
            phone,
            priceLevel,
            rating,
            socialNetwork,
            subCategoryName,
            firebaseAuth.uid.orEmpty(),
            city,
            state,
            country
        )
        return when (result) {
            is Result.Success -> {
                result.data?.let {
                    availables?.forEach {
                        repository.saveArrudeiaAvaliablePlace(
                            it.available.name,
                            result.data.orEmpty()
                        )
                    }
                }
                Result.Success(result.data)
            }

            is Result.Error -> {
                Result.Error(null)
            }

            else -> {
                Result.Error(null)
            }
        }
    }
}

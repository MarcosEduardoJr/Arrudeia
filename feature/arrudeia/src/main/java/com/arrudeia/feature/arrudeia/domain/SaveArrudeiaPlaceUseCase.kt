package com.arrudeia.feature.arrudeia.domain

import android.net.Uri
import com.arrudeia.feature.arrudeia.data.ArrudeiaPlaceRepositoryImpl
import com.arrudeia.feature.arrudeia.data.FirebaseArrudeiaMapRepositoryImpl
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaAvailablePlaceUiModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

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
        priceLevel: Int = 0,
        rating: Double = 0.0,
    ): String? {
        var image = ""
        uri?.let {
            image = firebaseArrudeiaMapRepositoryImpl.savePlaceImage(name.orEmpty(), it).orEmpty()
        }
        val id = repository.saveArrudeiaPlace(
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
        )
        id?.let {  availables?.forEach { repository.saveArrudeiaAvaliablePlace(it.available.name, id) } }
        return id
    }


}

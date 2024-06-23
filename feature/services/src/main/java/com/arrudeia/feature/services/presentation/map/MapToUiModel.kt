package com.arrudeia.feature.services.presentation.map

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import com.arrudeia.core.designsystem.component.DropListUiModel
import com.arrudeia.feature.services.domain.entity.CepAddressUseCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceDetailUseCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceExpertiseUseCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceImageUseCaseEntity
import com.arrudeia.feature.services.presentation.model.CepAddressUiModel
import com.arrudeia.feature.services.presentation.model.ServiceDetailUiModel
import com.arrudeia.feature.services.presentation.model.ServiceExpertiseUiModel
import com.arrudeia.feature.services.presentation.model.ServiceImageDetailUiModel
import com.arrudeia.feature.services.presentation.model.ServiceUIModel


fun ServiceCaseEntity.mapToUiModel(): ServiceUIModel {
    var listResult: ServiceUIModel
    this.let { item ->
        listResult = ServiceUIModel(
            id = item.id,
            name = item.name,
            description = item.description,
            categoryId = item.categoryId,
            city = item.city,
            country = item.country,
            imageUrl = item.imageUrl
        )
    }
    return listResult
}


fun ServiceDetailUseCaseEntity.toEntity(): ServiceDetailUiModel {
    return ServiceDetailUiModel(
        uuidUserCreator = this.uuidUserCreator.orEmpty(),
        name = this.name.orEmpty(),
        description = this.description.orEmpty(),
        zipCode = this.zipCode.orEmpty(),
        street = this.street.orEmpty(),
        number = this.number ?: 0,
        district = this.district.orEmpty(),
        city = this.city.orEmpty(),
        state = this.state.orEmpty(),
        country = this.country.orEmpty(),
        categoryId = this.categoryId ?: 0,
        image = this.image.toEntity(),
        id = this.id ?: 0
    )
}

fun List<ServiceImageUseCaseEntity?>?.toEntity(): List<ServiceImageDetailUiModel?> {
    return if (this == null) listOf()
    else {
        val list = mutableListOf<ServiceImageDetailUiModel>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        ServiceImageDetailUiModel(
                            id = item.id,
                            serviceId = item.serviceId,
                            url = item.url,
                        )
                    )
                }
            }
        }
        list.toList()
    }
}

fun ServiceExpertiseUseCaseEntity.toEntity(): DropListUiModel {
    return DropListUiModel(
        name = this.name,
        id = this.id,
        icon = Icons.Rounded.Build
    )
}

fun CepAddressUseCaseEntity.toCepAddressUiModel(): CepAddressUiModel {
    return CepAddressUiModel(
        zipCode = this.zipCode.orEmpty(),
        street = this.street.orEmpty(),
        district = this.district.orEmpty(),
        city = this.city.orEmpty(),
        state = this.state.orEmpty(),
        country = this.country.orEmpty()
    )
}

package com.arrudeia.feature.services.domain

import com.arrudeia.core.data.repository.entity.CepAddressRepositoryEntity
import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.R
import com.arrudeia.feature.services.data.entity.ServiceDetailRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceExpertiseRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceImageRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceUserImageRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceUserRepositoryEntity
import com.arrudeia.feature.services.domain.entity.CepAddressUseCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceDetailUseCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceExpertiseUseCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceImageUseCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceUserImageUseCase
import com.arrudeia.feature.services.domain.entity.ServiceUserUseCaseEntity
import com.arrudeia.feature.services.presentation.model.NewServiceUserImageUiModel
import com.arrudeia.feature.services.presentation.model.NewServiceUserUiModel

fun Result<ServiceDetailRepositoryEntity?>.toServiceDetailUseCaseEntity(): Result<ServiceDetailUseCaseEntity?> {
    var item: Result<ServiceDetailUseCaseEntity?> =
        Result.Error(R.string.erro_message_list_travels)
    when (this) {
        is Result.Success -> {
            this.data?.let {
                item = Result.Success(
                    ServiceDetailUseCaseEntity(
                        uuidUserCreator = it.uuidUserCreator.orEmpty(),
                        name = it.name.orEmpty(),
                        description = it.description.orEmpty(),
                        zipCode = it.zipCode.orEmpty(),
                        street = it.street.orEmpty(),
                        number = it.number ?: 0,
                        district = it.district.orEmpty(),
                        city = it.city.orEmpty(),
                        state = it.state.orEmpty(),
                        country = it.country.orEmpty(),
                        categoryId = it.categoryId ?: 0,
                        image = it.image.toServiceUserImageUseCaseEntity(),
                        id = it.id ?: 0
                    )
                )
            }
        }

        is Result.Error -> {
            item = Result.Error(this.message)
        }

        else -> {
            item = Result.Error(R.string.erro_message_list_travels)
        }
    }

    return item
}

fun List<ServiceImageRepositoryEntity?>?.toServiceUserImageUseCaseEntity(): List<ServiceImageUseCaseEntity?> {
    return if (this == null) listOf()
    else {
        val list = mutableListOf<ServiceImageUseCaseEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        ServiceImageUseCaseEntity(
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


fun Result<List<ServiceRepositoryEntity?>?>.toServicesUseCaseEntity(): Result<List<ServiceCaseEntity>> {
    var item: Result<List<ServiceCaseEntity>> =
        Result.Error(R.string.erro_message_list_travels)
    when (this) {
        is Result.Success -> {
            this.data?.let {
                val list = mutableListOf<ServiceCaseEntity>()
                this.let { repodeItem ->
                    it.forEach { item ->
                        item?.let {
                            list.add(
                                ServiceCaseEntity(
                                    id = item.id,
                                    name = item.name,
                                    description = item.description,
                                    categoryId = item.categoryId,
                                    city = item.city,
                                    country = item.country,
                                    imageUrl = item.imageUrl
                                )
                            )
                        }
                    }
                }
                item = Result.Success(list.toList())
            }
        }

        is Result.Error -> {
            item = Result.Error(this.message)
        }

        else -> {
            item = Result.Error(R.string.erro_message_list_travels)
        }
    }
    return item
}


fun Result<List<ServiceExpertiseRepositoryEntity>>.toEntity(): Result<List<ServiceExpertiseUseCaseEntity>> {
    var item: Result<List<ServiceExpertiseUseCaseEntity>> =
        Result.Error(R.string.erro_message_list_travels)
    when (this) {
        is Result.Success -> {
            this.data?.let {
                val list = mutableListOf<ServiceExpertiseUseCaseEntity>()
                this.let { repodeItem ->
                    it.forEach { item ->
                        item?.let {
                            list.add(
                                ServiceExpertiseUseCaseEntity(
                                    id = item.id,
                                    name = item.name,
                                )
                            )
                        }
                    }
                }
                item = Result.Success(list.toList())
            }
        }

        is Result.Error -> {
            item = Result.Error(this.message)
        }

        else -> {
            item = Result.Error(R.string.erro_message_list_travels)
        }
    }
    return item
}


fun ServiceUserUseCaseEntity.toServiceUserRepositoryEntity(): ServiceUserRepositoryEntity {
    return ServiceUserRepositoryEntity(
        uuidUserCreator  = this.uuid_user_creator.orEmpty(),
        name = this.name.orEmpty(),
        description = this.description.orEmpty(),
        zipCode = this.zip_code.orEmpty(),
        street = this.street.orEmpty(),
        number = this.number ?: 0,
        district = this.district.orEmpty(),
        city = this.city.orEmpty(),
        state = this.state.orEmpty(),
        country = this.country.orEmpty(),
        categoryId = this.category_id ?: 0,
        image = this.image.toServiceUserImageRepositoryEntity()
    )
}


fun List<ServiceUserImageUseCase>?.toServiceUserImageRepositoryEntity(): List<ServiceUserImageRepositoryEntity> {
    return if (this == null) listOf()
    else {
        val list = mutableListOf<ServiceUserImageRepositoryEntity>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        ServiceUserImageRepositoryEntity(
                            id = item.id,
                            service_id = item.service_id,
                            url = item.url,
                        )
                    )
                }
            }
        }
        list.toList()
    }
}



fun NewServiceUserUiModel.toServiceUserUseCase(): ServiceUserUseCaseEntity {
    return ServiceUserUseCaseEntity(
        uuid_user_creator = this.uuid_user_creator.orEmpty(),
        name = this.name.orEmpty(),
        description = this.description.orEmpty(),
        zip_code = this.zip_code.orEmpty(),
        street = this.street.orEmpty(),
        number = this.number ?: 0,
        district = this.district.orEmpty(),
        city = this.city.orEmpty(),
        state = this.state.orEmpty(),
        country = this.country.orEmpty(),
        category_id = this.category_id ?: 0,
        image = this.image.toServiceUserImageUseCase()
    )
}


fun List<NewServiceUserImageUiModel?>?.toServiceUserImageUseCase(): List<ServiceUserImageUseCase> {
    return if (this == null) listOf()
    else {
        val list = mutableListOf<ServiceUserImageUseCase>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        ServiceUserImageUseCase(
                            id = item.id,
                            service_id = item.service_id,
                            url = item.url,
                        )
                    )
                }
            }
        }
        list.toList()
    }
}


fun List<ServiceUserImageUseCase?>?.toServiceUserImageUiModel(): List<NewServiceUserImageUiModel> {
    return if (this == null) listOf()
    else {
        val list = mutableListOf<NewServiceUserImageUiModel>()
        this.let {
            it.forEach { item ->
                item?.let {
                    list.add(
                        NewServiceUserImageUiModel(
                            id = item.id,
                            service_id = item.service_id,
                            url = item.url,
                        )
                    )
                }
            }
        }
        list.toList()
    }
}



fun Result<CepAddressRepositoryEntity?>.toCepAddressUseCaseEntity(): Result<CepAddressUseCaseEntity?> {
    var item: Result<CepAddressUseCaseEntity?> =
        Result.Error(R.string.erro_message_list_travels)
    when (this) {
        is Result.Success -> {
            this.data?.let {
                item = Result.Success(
                    CepAddressUseCaseEntity(
                        city = it.city.orEmpty(),
                        country = it.country.orEmpty(),
                        district = it.district.orEmpty(),
                        state = it.state.orEmpty(),
                        street = it.street.orEmpty(),
                        zipCode = it.zipCode.orEmpty(),
                    )
                )
            }
        }

        is Result.Error -> {
            item = Result.Error(this.message)
        }

        else -> {
            item = Result.Error(R.string.erro_message_list_travels)
        }
    }

    return item
}


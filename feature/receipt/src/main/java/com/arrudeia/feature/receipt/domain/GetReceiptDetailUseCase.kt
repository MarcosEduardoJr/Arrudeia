package com.arrudeia.feature.receipt.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.receipt.data.ReceiptDetailRepositoryImpl
import com.arrudeia.feature.receipt.domain.entity.ReceiptDetailUseCaseEntity
import javax.inject.Inject

class GetReceiptDetailUseCase @Inject constructor(
    private val repository: ReceiptDetailRepositoryImpl,
) {
    suspend operator fun invoke(id: String):
            Result<ReceiptDetailUseCaseEntity?> =
        repository.getReceipt(id).toEntity()
}

package com.arrudeia.feature.receipt.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.receipt.R
import com.arrudeia.feature.receipt.data.ReceiptsRepositoryImpl
import com.arrudeia.feature.receipt.data.entity.ReceiptRepositoryEntity
import com.arrudeia.feature.receipt.domain.entity.ReceiptUseCase
import javax.inject.Inject

class GetReceiptsUseCase @Inject constructor(
    private val repository: ReceiptsRepositoryImpl,
) {
    suspend operator fun invoke():
            Result<List<ReceiptUseCase?>?> =
        repository.getReceipts().mapToUseCaseEntity()
}

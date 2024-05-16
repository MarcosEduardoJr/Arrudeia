package com.arrudeia.feature.receipt.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.receipt.data.entity.ReceiptRepositoryEntity


interface ReceiptsRepository {
    suspend fun getReceipts(): Result<List<ReceiptRepositoryEntity?>?>
}

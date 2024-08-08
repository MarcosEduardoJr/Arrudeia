package com.arrudeia.feature.receipt.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.receipt.data.entity.ReceiptDetailRepositoryEntity


interface ReceiptDetailRepository {
    suspend fun getReceipt(id: String): Result<ReceiptDetailRepositoryEntity?>
}

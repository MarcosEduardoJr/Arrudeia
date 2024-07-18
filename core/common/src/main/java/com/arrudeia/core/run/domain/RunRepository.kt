package com.arrudeia.core.run.domain

import com.arrudeia.core.designsystem.component.util.util.DataError
import com.arrudeia.core.designsystem.component.util.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface RunRepository {
    fun getRuns(): Flow<List<Run>>
    suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError>
    suspend fun deleteRun(id: RunId)
    suspend fun deleteAllRuns()
}
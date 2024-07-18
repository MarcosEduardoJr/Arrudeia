package com.arrudeia.core.run.domain

import com.arrudeia.core.designsystem.component.util.util.DataError
import kotlinx.coroutines.flow.Flow
import com.arrudeia.core.designsystem.component.util.util.Result

typealias RunId = String

interface LocalRunDataSource {
    fun getRuns(): Flow<List<Run>>
    suspend fun upsertRun(run: Run): Result<RunId, DataError.Local>
    suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local>
    suspend fun deleteRun(id: String)
    suspend fun deleteAllRuns()
}
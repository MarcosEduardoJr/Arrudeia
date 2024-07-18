package com.arrudeia.core.run.data

import android.database.sqlite.SQLiteFullException
import com.arrudeia.core.designsystem.component.util.util.DataError
import com.arrudeia.core.designsystem.component.util.util.Result
import com.arrudeia.core.run.data.dao.RunDao
import com.arrudeia.core.run.data.mappers.toRunEntity
import com.arrudeia.core.run.domain.LocalRunDataSource
import com.arrudeia.core.run.domain.Run
import com.arrudeia.core.run.domain.RunId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.arrudeia.core.run.data.mappers.toRun

class RoomLocalRunDataSource @Inject constructor (
    private val runDao: RunDao
): LocalRunDataSource {

    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns()
            .map { runEntities ->
                runEntities.map { it.toRun() }
            }
    }

    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> {
        return try {
            val entity = run.toRunEntity()
            runDao.upsertRun(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local> {
        return try {
            val entities = runs.map { it.toRunEntity() }
            runDao.upsertRuns(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}
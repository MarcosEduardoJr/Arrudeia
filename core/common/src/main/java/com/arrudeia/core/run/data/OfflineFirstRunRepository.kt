package com.arrudeia.core.run.data

import com.arrudeia.core.designsystem.component.util.util.DataError
import com.arrudeia.core.designsystem.component.util.util.EmptyResult
import com.arrudeia.core.designsystem.component.util.util.Result
import com.arrudeia.core.designsystem.component.util.util.asEmptyDataResult
import com.arrudeia.core.run.domain.Run
import com.arrudeia.core.run.domain.RunId
import com.arrudeia.core.run.domain.RunRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstRunRepository @Inject constructor(
    private val localRunDataSource: RoomLocalRunDataSource,
    private val applicationScope: CoroutineScope,
    private val firebaseRunMapScreenshotRepository: FirebaseRunMapScreenshotRepositoryImpl
) : RunRepository {

    override fun getRuns(): Flow<List<Run>> {
        return localRunDataSource.getRuns()
    }


    override suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError> {
        run.mapPictureUrl = firebaseRunMapScreenshotRepository.saveMapScreenShot(mapPicture)
        val result = localRunDataSource.upsertRun(run).asEmptyDataResult()
        return result
    }

    override suspend fun deleteRun(id: RunId) {
        localRunDataSource.deleteRun(id)
    }


    override suspend fun deleteAllRuns() {
        localRunDataSource.deleteAllRuns()
    }


}
package com.arrudeia.core.data.repository

import com.arrudeia.core.data.Synchronizer
import com.arrudeia.core.datastore.ChangeListVersions
import com.arrudeia.core.datastore.ArrudeiaPreferencesDataSource

/**
 * Test synchronizer that delegates to [ArrudeiaPreferencesDataSource]
 */
class TestSynchronizer(
    private val niaPreferences: ArrudeiaPreferencesDataSource,
) : Synchronizer {
    override suspend fun getChangeListVersions(): ChangeListVersions =
        niaPreferences.getChangeListVersions()

    override suspend fun updateChangeListVersions(
        update: ChangeListVersions.() -> ChangeListVersions,
    ) = niaPreferences.updateChangeListVersion(update)
}

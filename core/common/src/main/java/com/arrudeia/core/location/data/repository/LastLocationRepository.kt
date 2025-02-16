package com.arrudeia.core.location.data.repository

interface LastLocationRepository {
    suspend fun updateLastLocation(uuid: String, lastCity: String, lastCountry: String)
}
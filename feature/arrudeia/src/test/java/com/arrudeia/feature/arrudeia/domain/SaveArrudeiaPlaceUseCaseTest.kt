package com.arrudeia.feature.arrudeia.domain

import android.net.Uri
import com.arrudeia.core.places.data.ArrudeiaPlaceRepositoryImpl
import com.arrudeia.core.places.data.FirebaseArrudeiaMapRepositoryImpl
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaAvailablePlaceUiModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.arrudeia.core.result.Result
import com.arrudeia.feature.arrudeia.presentation.ui.AvailableOptions
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull

class SaveArrudeiaPlaceUseCaseTest {

    private val repository = mock(ArrudeiaPlaceRepositoryImpl::class.java)
    private val firebaseArrudeiaMapRepositoryImpl =
        mock(FirebaseArrudeiaMapRepositoryImpl::class.java)
    private val firebaseAuth = mock(FirebaseAuth::class.java)
    private val useCase =
        SaveArrudeiaPlaceUseCase(repository, firebaseArrudeiaMapRepositoryImpl, firebaseAuth)

    @Test
    fun `invoke calls repository`() = runBlocking {
        val uri = mock(Uri::class.java)
        val availables = listOf(ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI))
        val location = LatLng(1.0, 1.0)

        `when`(
            firebaseArrudeiaMapRepositoryImpl.savePlaceImage(
                anyString(),
                anyOrNull()
            )
        ).thenReturn("testImage")
        `when`(
            repository.saveArrudeiaPlace(
                anyString(),
                anyString(),
                anyString(),
                anyDouble(),
                anyDouble(),
                anyString(),
                anyString(),
                anyInt(),
                anyInt(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString()
            )
        ).thenReturn(Result.Success("testResult"))
        `when`(
            repository.saveArrudeiaAvaliablePlace(
                anyString(),
                anyString()
            )
        ).thenReturn(Result.Success("testResult"))
        `when`(firebaseAuth.uid).thenReturn("testUid")

        val result = useCase(
            "testName",
            "testPhone",
            "testSocialNetwork",
            "testDescription",
            uri,
            availables,
            "testCategoryName",
            "testSubCategoryName",
            location,
            1,
            1,
            "", "", "",
        )

        verify(firebaseArrudeiaMapRepositoryImpl).savePlaceImage(anyString(), anyOrNull())
        verify(repository).saveArrudeiaPlace(
            anyString(),
            anyString(),
            anyString(),
            anyDouble(),
            anyDouble(),
            anyString(),
            anyString(),
            anyInt(),
            anyInt(),
            anyString(),
            anyString(),
            anyString(),
            anyString(),
            anyString(),
            anyString()
        )
        verify(repository).saveArrudeiaAvaliablePlace(anyString(), anyString())
        assertTrue(result is Result.Success)
        result as Result.Success
        assertEquals("testResult", result.data)
    }
}
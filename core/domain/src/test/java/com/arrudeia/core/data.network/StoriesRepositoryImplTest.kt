package com.arrudeia.core.data.network

import com.arrudeia.core.data.network.StoriesRepositoryImpl.Companion.ARRUDEIA_TV
import com.arrudeia.core.data.repository.StoriesRepositoryEntity
import com.arrudeia.core.data.repository.TravelRepositoryEntity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import kotlin.test.assertTrue

class StoriesRepositoryImplTest {
    private lateinit var repository: StoriesRepositoryImpl
    private lateinit var firestore: FirebaseFirestore

    @Before
    fun setup() {
        firestore = Mockito.mock()
        repository = StoriesRepositoryImpl(firestore)
    }

    @Test
    fun `getStories should return list entities`() = runBlocking {
        // Given
        val item = StoriesRepositoryEntity()
        val querySnapshot = Mockito.mock<QuerySnapshot>()
        val task = Mockito.mock<Task<QuerySnapshot>>()
        val document = Mockito.mock<DocumentSnapshot>()
        var listDocumentSnapshot = mutableListOf<DocumentSnapshot>()
        listDocumentSnapshot.add(document)

        // Simulate firestore.collection("home_travels").get() returning a non-null Task
        val collectionReference = Mockito.mock<CollectionReference>()
        Mockito.`when`(firestore.collection(ARRUDEIA_TV)).thenReturn(collectionReference)
        Mockito.`when`(collectionReference.get()).thenReturn(task)

        // Simulate successful task execution
        Mockito.`when`(task.isSuccessful).thenReturn(true)
        Mockito.`when`(task.addOnSuccessListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnSuccessListener<in QuerySnapshot>
            listener.onSuccess(querySnapshot)
            task
        }
        Mockito.`when`(task.addOnFailureListener(ArgumentMatchers.any())).thenReturn(task)
        Mockito.`when`(task.result).thenReturn(querySnapshot)

        //return object
        Mockito.`when`(querySnapshot.documents).thenReturn(listDocumentSnapshot)
        Mockito.`when`(document.toObject(StoriesRepositoryEntity::class.java)).thenReturn(item)

        // When
        val result = repository.getStories()

        // Then
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `getStoriesById should return a item `() = runBlocking {
        // Given
        val item = StoriesRepositoryEntity()
        val querySnapshot = Mockito.mock<QuerySnapshot>()
        val task = Mockito.mock<Task<QuerySnapshot>>()
        val document = Mockito.mock<DocumentSnapshot>()
        var listDocumentSnapshot = mutableListOf<DocumentSnapshot>()
        listDocumentSnapshot.add(document)

        // Simulate firestore.collection("home_travels").get() returning a non-null Task
        val collectionReference = Mockito.mock<CollectionReference>()
        Mockito.`when`(firestore.collection(ARRUDEIA_TV)).thenReturn(collectionReference)
        Mockito.`when`(collectionReference.get()).thenReturn(task)

        // Simulate successful task execution
        Mockito.`when`(task.isSuccessful).thenReturn(true)
        Mockito.`when`(task.addOnSuccessListener(ArgumentMatchers.any())).thenAnswer {
            val listener = it.arguments[0] as OnSuccessListener<in QuerySnapshot>
            listener.onSuccess(querySnapshot)
            task
        }
        Mockito.`when`(task.addOnFailureListener(ArgumentMatchers.any())).thenReturn(task)
        Mockito.`when`(task.result).thenReturn(querySnapshot)

        //return object
        Mockito.`when`(querySnapshot.documents).thenReturn(listDocumentSnapshot)
        Mockito.`when`(document.toObject(StoriesRepositoryEntity::class.java)).thenReturn(item)

        // When
        val result = repository.getStoriesById(0)

        // Then
        assertTrue(result != null)
    }
}
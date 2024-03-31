package com.arrudeia.core.data.network

import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl.Companion.HOME_ARRUDEIA_TV
import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl.Companion.HOME_TRAVELS
import com.arrudeia.core.data.entity.ArrudeiaTvRepositoryEntity
import com.arrudeia.core.data.entity.TravelRepositoryEntity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertTrue

class DefaultHomeTravelsRepositoryImplTest {
    private lateinit var repository: DefaultHomeTravelsRepositoryImpl
    private lateinit var firestore: FirebaseFirestore

    @Before
    fun setup() {
        firestore = mock()
        repository = DefaultHomeTravelsRepositoryImpl(firestore)
    }

    @Test
    fun `getAllTravels should return list of travel entities`() = runBlocking {
        // Given
        val item = TravelRepositoryEntity()
        val querySnapshot = mock<QuerySnapshot>()
        val task = mock<Task<QuerySnapshot>>()
        val document = mock<DocumentSnapshot>()
        var listDocumentSnapshot = mutableListOf<DocumentSnapshot>()
        listDocumentSnapshot.add(document)

        // Simulate firestore.collection("home_travels").get() returning a non-null Task
        val collectionReference = mock<CollectionReference>()
        `when`(firestore.collection(HOME_TRAVELS)).thenReturn(collectionReference)
        `when`(collectionReference.get()).thenReturn(task)

        // Simulate successful task execution
        `when`(task.isSuccessful).thenReturn(true)
        `when`(task.addOnSuccessListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnSuccessListener<in QuerySnapshot>
            listener.onSuccess(querySnapshot)
            task
        }
        `when`(task.addOnFailureListener(any())).thenReturn(task)
        `when`(task.result).thenReturn(querySnapshot)

        //return object
        `when`(querySnapshot.documents).thenReturn(listDocumentSnapshot)
        `when`(document.toObject(TravelRepositoryEntity::class.java)).thenReturn(item)

        // When
        val result = repository.getAllTravels()

        // Then
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `getAllArrudeiaTv should return list of arrudeia tv entities`() = runBlocking {
        // Given
        val item = ArrudeiaTvRepositoryEntity()
        val querySnapshot = mock<QuerySnapshot>()
        val task = mock<Task<QuerySnapshot>>()
        val document = mock<DocumentSnapshot>()
        var listDocumentSnapshot = mutableListOf<DocumentSnapshot>()
        listDocumentSnapshot.add(document)

        // Simulate firestore.collection("home_travels").get() returning a non-null Task
        val collectionReference = mock<CollectionReference>()
        `when`(firestore.collection(HOME_ARRUDEIA_TV)).thenReturn(collectionReference)
        `when`(collectionReference.get()).thenReturn(task)

        // Simulate successful task execution
        `when`(task.isSuccessful).thenReturn(true)
        `when`(task.addOnSuccessListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnSuccessListener<in QuerySnapshot>
            listener.onSuccess(querySnapshot)
            task
        }
        `when`(task.addOnFailureListener(any())).thenReturn(task)
        `when`(task.result).thenReturn(querySnapshot)

        //return object
        `when`(querySnapshot.documents).thenReturn(listDocumentSnapshot)
        `when`(document.toObject(ArrudeiaTvRepositoryEntity::class.java)).thenReturn(item)

        // When
        val result = repository.getAllArrudeiaTv()

        // Then
        assertTrue(result.isNotEmpty())
    }


    @Test
    fun `getTravelById should return a item travel`() = runBlocking {
        // Given
        val item = TravelRepositoryEntity()
        val querySnapshot = mock<QuerySnapshot>()
        val task = mock<Task<QuerySnapshot>>()
        val document = mock<DocumentSnapshot>()
        var listDocumentSnapshot = mutableListOf<DocumentSnapshot>()
        listDocumentSnapshot.add(document)

        // Simulate firestore.collection("home_travels").get() returning a non-null Task
        val collectionReference = mock<CollectionReference>()
        `when`(firestore.collection(HOME_TRAVELS)).thenReturn(collectionReference)
        `when`(collectionReference.get()).thenReturn(task)

        // Simulate successful task execution
        `when`(task.isSuccessful).thenReturn(true)
        `when`(task.addOnSuccessListener(any())).thenAnswer {
            val listener = it.arguments[0] as OnSuccessListener<in QuerySnapshot>
            listener.onSuccess(querySnapshot)
            task
        }
        `when`(task.addOnFailureListener(any())).thenReturn(task)
        `when`(task.result).thenReturn(querySnapshot)

        //return object
        `when`(querySnapshot.documents).thenReturn(listDocumentSnapshot)
        `when`(document.toObject(TravelRepositoryEntity::class.java)).thenReturn(item)

        // When
        val result = repository.getTravelById(0)

        // Then
        assertTrue(result!=null)
    }

}

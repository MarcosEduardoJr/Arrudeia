package com.arrudeia.feature.sign.data

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SignFirebaseUserRepositoryImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var repository: SignFirebaseUserRepositoryImpl

    @Before
    fun setup() {
        firebaseAuth = mockk(relaxed = true)
        repository = SignFirebaseUserRepositoryImpl(firebaseAuth)
    }

    @Test
    fun `createUserWithEmailAndPassword returns user when successful`() = runBlocking {
        val email = "test@test.com"
        val password = "password"
        val firebaseUser = mockk<FirebaseUser>(relaxed = true)
        val authResult = mockk<AuthResult>(relaxed = true)
        val task = mockk<Task<AuthResult>>(relaxed = true)

        coEvery { firebaseUser.email } returns email
        coEvery { authResult.user } returns firebaseUser
        coEvery { firebaseAuth.createUserWithEmailAndPassword(email, password) } returns task
        coEvery { task.isSuccessful } returns true
        coEvery { task.result } returns authResult

        every { task.addOnCompleteListener(any()) } answers {
            val listener = arg<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task)
            task
        }

        val result = repository.createUserWithEmailAndPassword(email, password)

        Assert.assertNotNull(result)
        Assert.assertEquals(email, result?.email)
    }

    @Test
    fun `signUserWithEmailAndPassword returns null when task is not successful`() = runBlocking {
        val email = "test@test.com"
        val password = "password"
        val task = mockk<Task<AuthResult>>(relaxed = true)

        coEvery { firebaseAuth.signInWithEmailAndPassword(email, password) } returns task
        coEvery { task.isSuccessful } returns false

        every { task.addOnCompleteListener(any()) } answers {
            val listener = arg<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(task)
            task
        }

        val result = repository.signUserWithEmailAndPassword(email, password)

        Assert.assertNull(result)
    }
}
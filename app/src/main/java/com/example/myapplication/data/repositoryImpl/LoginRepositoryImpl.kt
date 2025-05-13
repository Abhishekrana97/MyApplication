package com.example.myapplication.data.repositoryImpl


import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.utils.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : LoginRepository {

    override suspend fun registerUser(email: String, password: String): DataState<FirebaseUser> {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
                .await()  // await suspends the coroutine
            DataState.Success(auth.currentUser!!)  // Wrap the result in a success
        } catch (e: Exception) {
            Timber.e(e, "Registration failed")
            DataState.Error(e)  // Handle error and return an error state
        }
    }

    override suspend fun loginUser(email: String, password: String): DataState<FirebaseUser> {
        return try {
            auth.signInWithEmailAndPassword(email, password)
                .await()  // await suspends the coroutine
            DataState.Success(auth.currentUser!!)  // Wrap the result in a success
        } catch (e: Exception) {
            Timber.e(e, "Login failed")
            DataState.Error(e)  // Handle error and return an error state
        }
    }

    override suspend fun logoutUser() {
        auth.signOut()
    }
}
package com.example.myapplication.domain.repository


import com.example.myapplication.utils.DataState
import com.google.firebase.auth.FirebaseUser


interface LoginRepository {
    suspend fun registerUser(email:String,password:String): DataState<FirebaseUser>
    suspend fun loginUser(email:String,password:String):DataState<FirebaseUser>
    suspend fun logoutUser()
}


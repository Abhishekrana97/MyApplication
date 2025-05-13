package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_data")
data class UserData(
    @PrimaryKey()
    val email: String,
    val name: String,
    val password: String
)
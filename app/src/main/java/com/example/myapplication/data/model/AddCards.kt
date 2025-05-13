package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_data")
data class AddCards(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cardName: String,
    val cardNumber: String,
    val validity: String,
    val cvv: String
)

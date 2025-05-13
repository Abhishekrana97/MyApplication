package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "deposit_data",
    foreignKeys = [
        ForeignKey(
            entity = AddCards::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("card_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
data class DepositMoney(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val card_id : Int,
    val money: String,
    val date: String
)

package com.example.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.model.AddCards
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(addCard: AddCards)

    @Delete
    suspend fun deleteCard(addCard: AddCards)

    @Update
    suspend fun updateCard(addCard: AddCards)

    @Query("SELECT * FROM card_data")
    fun getAllCards(): Flow<List<AddCards>>

}
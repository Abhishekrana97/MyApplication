package com.example.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.model.DepositMoney
import kotlinx.coroutines.flow.Flow


@Dao
interface DepositMoneyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: DepositMoney)

    @Query("SELECT * FROM deposit_data")
    fun getDepositMoney(): Flow<List<DepositMoney>>

}
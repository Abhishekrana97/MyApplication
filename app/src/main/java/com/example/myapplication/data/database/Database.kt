package com.example.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.database.dao.CardDao
import com.example.myapplication.data.database.dao.DepositMoneyDao
import com.example.myapplication.data.database.dao.UserDao
import com.example.myapplication.data.model.AddCards
import com.example.myapplication.data.model.DepositMoney
import com.example.myapplication.data.model.UserData


@Database(version = 1, entities = [UserData::class,AddCards::class,DepositMoney::class], exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getCardsDao(): CardDao
    abstract fun getDepositMoneyDao(): DepositMoneyDao
}
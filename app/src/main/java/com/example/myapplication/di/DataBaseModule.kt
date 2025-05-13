package com.example.myapplication.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.database.Database
import com.example.myapplication.data.database.dao.CardDao
import com.example.myapplication.data.database.dao.DepositMoneyDao
import com.example.myapplication.data.database.dao.UserDao
import com.example.myapplication.utils.SettingsPreference
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "database.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: Database): UserDao =
        database.getUserDao()

    @Singleton
    @Provides
    fun provideCardDao(database: Database): CardDao =
        database.getCardsDao()


    @Singleton
    @Provides
    fun provideDepositMoneyDao(database: Database): DepositMoneyDao =
        database.getDepositMoneyDao()


    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideThemePreference(context: Context): SettingsPreference {
        return SettingsPreference(context)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

}
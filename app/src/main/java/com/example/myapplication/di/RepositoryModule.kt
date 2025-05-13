package com.example.myapplication.di

import com.example.myapplication.data.repositoryImpl.CardScreenRepositoryImpl
import com.example.myapplication.data.repositoryImpl.DepositMoneyRepositoryImpl
import com.example.myapplication.data.repositoryImpl.ExchangeCurrencyRepositoryImpl
import com.example.myapplication.data.repositoryImpl.ExpenseScreenRepositoryImpl
import com.example.myapplication.data.repositoryImpl.GraphScreenRepositoryImpl
import com.example.myapplication.data.repositoryImpl.LoginRepositoryImpl
import com.example.myapplication.data.repositoryImpl.MapScreenRepositoryImpl
import com.example.myapplication.data.repositoryImpl.PdfRepositoryImpl
import com.example.myapplication.data.repositoryImpl.ProfileScreenRepositoryImpl
import com.example.myapplication.data.repositoryImpl.SettingsRepositoryImpl
import com.example.myapplication.domain.repository.CardScreenRepository
import com.example.myapplication.domain.repository.DepositMoneyRepository
import com.example.myapplication.domain.repository.ExchangeCurrencyRepository
import com.example.myapplication.domain.repository.ExpenseScreenRepository
import com.example.myapplication.domain.repository.GraphScreenRepository
import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.domain.repository.MapScreenRepository
import com.example.myapplication.domain.repository.PdfRepository
import com.example.myapplication.domain.repository.ProfileScreenRepository
import com.example.myapplication.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideLoginRepository(repository: LoginRepositoryImpl): LoginRepository {
        return repository
    }

    @Provides
    fun provideProfileRepository(repository: ProfileScreenRepositoryImpl): ProfileScreenRepository {
        return repository
    }


    @Provides
    fun provideHomeScreenRepository(repository: CardScreenRepositoryImpl): CardScreenRepository {
        return repository
    }

    @Provides
    fun provideGraphScreenRepository(repository: GraphScreenRepositoryImpl): GraphScreenRepository {
        return repository
    }

    @Provides
    fun provideExpenseScreenRepository(repository: ExpenseScreenRepositoryImpl): ExpenseScreenRepository {
        return repository
    }

    @Provides
    fun provideSettingsScreenRepository(repository: SettingsRepositoryImpl): SettingsRepository {
        return repository
    }

    @Provides
    fun provideDepositScreenRepository(repository: DepositMoneyRepositoryImpl): DepositMoneyRepository {
        return repository
    }

    @Provides
    fun provideExchangeCurrencyScreenRepository(repository: ExchangeCurrencyRepositoryImpl): ExchangeCurrencyRepository {
        return repository
    }

    @Provides
    fun provideMapScreenRepository(repository: MapScreenRepositoryImpl): MapScreenRepository {
        return repository
    }

    @Provides
    fun providePdfRepository(repository: PdfRepositoryImpl): PdfRepository {
        return repository
    }

}
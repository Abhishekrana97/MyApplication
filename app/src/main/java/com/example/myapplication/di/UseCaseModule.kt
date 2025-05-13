package com.example.myapplication.di

import com.example.myapplication.data.repositoryImpl.PdfRepositoryImpl
import com.example.myapplication.domain.repository.CardScreenRepository
import com.example.myapplication.domain.repository.DepositMoneyRepository
import com.example.myapplication.domain.repository.ExchangeCurrencyRepository
import com.example.myapplication.domain.repository.ExpenseScreenRepository
import com.example.myapplication.domain.repository.GraphScreenRepository
import com.example.myapplication.domain.repository.LoginRepository
import com.example.myapplication.domain.repository.MapScreenRepository
import com.example.myapplication.domain.repository.ProfileScreenRepository
import com.example.myapplication.domain.repository.SettingsRepository
import com.example.myapplication.domain.usecase.AddCardUseCase
import com.example.myapplication.domain.usecase.CardUserCases
import com.example.myapplication.domain.usecase.DeleteCardUseCase
import com.example.myapplication.domain.usecase.DepositMoneyUseCase
import com.example.myapplication.domain.usecase.DepositUseCase
import com.example.myapplication.domain.usecase.ExchangeCurrencyUseCase
import com.example.myapplication.domain.usecase.ExpenseDetailsUseCase
import com.example.myapplication.domain.usecase.FetchUserLocationUseCase
import com.example.myapplication.domain.usecase.GetCardUseCase
import com.example.myapplication.domain.usecase.GetDarkModeStatusUseCase
import com.example.myapplication.domain.usecase.GetDepositMoneyUseCase
import com.example.myapplication.domain.usecase.GetFingerprintStatusUseCase
import com.example.myapplication.domain.usecase.GetLanguagePrefUseCase
import com.example.myapplication.domain.usecase.GetPdfPageUseCase
import com.example.myapplication.domain.usecase.GraphUseCase
import com.example.myapplication.domain.usecase.LoginUseCases
import com.example.myapplication.domain.usecase.LoginUserUseCase
import com.example.myapplication.domain.usecase.LogoutUserUseCase
import com.example.myapplication.domain.usecase.MapScreenUseCase
import com.example.myapplication.domain.usecase.PdfUseCase
import com.example.myapplication.domain.usecase.ProfileUseCase
import com.example.myapplication.domain.usecase.RegisterUserUseCase
import com.example.myapplication.domain.usecase.SelectedLocationUseCase
import com.example.myapplication.domain.usecase.SetDarkModeStatusUseCase
import com.example.myapplication.domain.usecase.SetFingerprintStatusUseCase
import com.example.myapplication.domain.usecase.SetLanguagePrefUseCase
import com.example.myapplication.domain.usecase.SettingsUseCase
import com.example.myapplication.domain.usecase.StorePdfPageUseCase
import com.example.myapplication.domain.usecase.UpdateCardUseCase
import com.example.myapplication.domain.usecase.ViewPdfUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCases(repository: LoginRepository): LoginUseCases {
        return LoginUseCases(
            registerUserUseCase = RegisterUserUseCase(repository),
            loginUserUseCase = LoginUserUseCase(repository),
            logoutUserUseCase = LogoutUserUseCase(repository)
        )
    }


    @Provides
    fun provideSettingsUseCases(repository: SettingsRepository): SettingsUseCase {
        return SettingsUseCase(
            getDarkModeStatusUseCase = GetDarkModeStatusUseCase(repository),
            setDarkModeStatusUseCase = SetDarkModeStatusUseCase(repository),
            getFingerprintStatusUseCase = GetFingerprintStatusUseCase(repository),
            setFingerprintStatusUseCase = SetFingerprintStatusUseCase(repository),
            setLanguagePrefUseCase = SetLanguagePrefUseCase(repository),
            getLanguagePrefUseCase = GetLanguagePrefUseCase(repository)
        )
    }

    @Provides
    fun provideProfileUseCase(repository: ProfileScreenRepository): ProfileUseCase {
        return ProfileUseCase(repository)
    }

    @Provides
    fun provideGraphUseCase(repository: GraphScreenRepository): GraphUseCase {
        return GraphUseCase(repository)
    }


    @Provides
    fun provideCardUseCases(repository: CardScreenRepository): CardUserCases {
        return CardUserCases(
            addCardUseCase = AddCardUseCase(repository),
            getCardUseCase = GetCardUseCase(repository),
            deleteCardUseCase = DeleteCardUseCase(repository),
            updateCardUseCase = UpdateCardUseCase(repository)
        )
    }

    @Provides
    fun provideDepositUseCases(repository: DepositMoneyRepository): DepositUseCase {
        return DepositUseCase(
            getDepositMoneyUseCase = GetDepositMoneyUseCase(repository),
            depositMoneyUseCase = DepositMoneyUseCase(repository)
        )
    }

    @Provides
    fun provideMapUseCases(repository: MapScreenRepository): MapScreenUseCase {
        return MapScreenUseCase(
            fetchUserLocationUseCase = FetchUserLocationUseCase(repository),
            selectedLocationUseCase = SelectedLocationUseCase(repository)
        )
    }


    @Provides
    fun provideExpenseUseCases(repository: ExpenseScreenRepository): ExpenseDetailsUseCase {
        return ExpenseDetailsUseCase(repository)
    }

    @Provides
    fun provideExchangeCurrencyUseCases(repository: ExchangeCurrencyRepository): ExchangeCurrencyUseCase {
        return ExchangeCurrencyUseCase(repository)
    }

    @Provides
    fun providePdfScreenUseCases(repository: PdfRepositoryImpl): PdfUseCase {
        return PdfUseCase(
            ViewPdfUseCase(repository),
            StorePdfPageUseCase(repository),
            GetPdfPageUseCase(repository)
        )
    }
}
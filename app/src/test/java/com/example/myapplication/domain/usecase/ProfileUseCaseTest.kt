package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.ProfileScreenRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ProfileUseCaseTest{

    private lateinit var profileUseCase: ProfileUseCase
    private val repository: ProfileScreenRepository = mock()

    @Before
    fun setUp(){
        profileUseCase = ProfileUseCase(repository)
    }

    @Test
    fun testGetProfileDetails() = runTest{
        profileUseCase()

        Mockito.verify(repository, Mockito.times(1)).getProfileDetails()
    }
}
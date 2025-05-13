package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.GraphScreenRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GraphUseCaseTest{

    private lateinit var useCase: GraphUseCase
    private val repository: GraphScreenRepository = mock()

    @Before
    fun setUp(){
        useCase = GraphUseCase(repository)
    }

    @Test
    fun testGetGraphDetails() = runTest{
        useCase()

        Mockito.verify(repository, Mockito.times(1)).getGraphDetails()
    }
}
package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.PdfRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.test.Test

class StorePdfPageUseCaseTest{

    private val repository: PdfRepository = mock()
    private lateinit var useCase: StorePdfPageUseCase

    @Before
    fun setUp() {
        useCase = StorePdfPageUseCase(repository)
    }

    @Test
    fun testStorePdfPage() = runTest {
        useCase(0)
        Mockito.verify(repository, Mockito.times(1)).storePdfPage(0)
    }


}
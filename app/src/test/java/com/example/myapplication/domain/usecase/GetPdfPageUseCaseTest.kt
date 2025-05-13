package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.PdfRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetPdfPageUseCaseTest{

    private lateinit var getPdfPageUseCase: GetPdfPageUseCase
    private val repository: PdfRepository = mock()

    @Before
    fun setUp(){
        getPdfPageUseCase = GetPdfPageUseCase(repository)
    }

    @Test
    fun testGetPdfPage() = runTest{
        getPdfPageUseCase()

        Mockito.verify(repository, Mockito.times(1)).getPdfPage()
    }
}
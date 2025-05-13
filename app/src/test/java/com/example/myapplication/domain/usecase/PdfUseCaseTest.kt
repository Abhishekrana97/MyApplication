package com.example.myapplication.domain.usecase

import android.content.Context
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class PdfUseCaseTest{

    private val getPdfPageUseCase: GetPdfPageUseCase = mock()
    private val storePdfPageUseCase: StorePdfPageUseCase = mock()
    private val viewPdfUseCase: ViewPdfUseCase = mock()
    private val context: Context = mock()
    private lateinit var url: String

    private lateinit var pdfUseCase: PdfUseCase

    @Before
    fun setUp() {
        url = "https://sist.sathyabama.ac.in/sist_naac/documents/1.3.4/1822-b.e-cse-batchno-224.pdf"
        pdfUseCase =
            PdfUseCase(viewPdfUseCase, storePdfPageUseCase, getPdfPageUseCase)
    }


    @Test
    fun testViewPdf() = runTest {

        pdfUseCase.viewPdfUseCase.invoke(context,url)

        verify(viewPdfUseCase, Mockito.times(1)).invoke(context,url)
    }

    @Test
    fun getPdfPage() = runTest {
        pdfUseCase.getPdfPageUseCase()

        verify(getPdfPageUseCase, Mockito.times(1)).invoke()
    }

    @Test
    fun storePdfPage() = runTest {

        pdfUseCase.storePdfPageUseCase.invoke(0)

        verify(storePdfPageUseCase, Mockito.times(1)).invoke(0)
    }


}
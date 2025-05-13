package com.example.myapplication.domain.usecase

import android.content.Context
import com.example.myapplication.domain.repository.PdfRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.test.Test

class ViewPdfUseCaseTest {
    private lateinit var viewPdfUseCase: ViewPdfUseCase

    private val repository: PdfRepository = mock() // mock repository
    private lateinit var url: String
    private val context: Context = mock()

    @Before
    fun setUp() {
        url = "https://sist.sathyabama.ac.in/sist_naac/documents/1.3.4/1822-b.e-cse-batchno-224.pdf"
        viewPdfUseCase = ViewPdfUseCase(repository)
    }

    @Test
    fun testViewPdf() = runTest {
        // Act
        viewPdfUseCase(context, url)

        // Assert
        verify(repository, times(1)).getPdfFromCacheOrUrl(context, url)
    }

}
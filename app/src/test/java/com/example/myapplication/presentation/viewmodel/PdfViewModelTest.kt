package com.example.myapplication.presentation.viewmodel


import android.content.Context
import com.example.myapplication.domain.usecase.GetPdfPageUseCase
import com.example.myapplication.domain.usecase.PdfUseCase
import com.example.myapplication.domain.usecase.StorePdfPageUseCase
import com.example.myapplication.domain.usecase.ViewPdfUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import java.io.File

@ExperimentalCoroutinesApi
class PdfViewModelTest {


    private lateinit var mockPdfUseCase: PdfUseCase
    private val getPdfPageUseCase: GetPdfPageUseCase = mock()
    private val storePdfPageUseCase: StorePdfPageUseCase = mock()
    private val viewPdfUseCase: ViewPdfUseCase = mock()
    private lateinit var pdfViewModel: PdfViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        mockPdfUseCase = PdfUseCase(viewPdfUseCase, storePdfPageUseCase, getPdfPageUseCase)
        pdfViewModel = PdfViewModel(mockPdfUseCase)

        // Set up a test dispatcher for coroutines
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test loadPdf should update pdfFile and isLoading`() = runTest {
        // Arrange
        val mockContext = mock(Context::class.java)
        val mockPdfFile = mock(File::class.java)
        val url = "http://example.com/sample.pdf"

        // When the use case is called, it should return a mock file
        whenever(viewPdfUseCase(mockContext, url)).thenReturn(mockPdfFile)

        // Act
        pdfViewModel.loadPdf(mockContext, url)

        // Wait for the coroutine to finish
        advanceUntilIdle()

        // Assert
        assert(pdfViewModel.pdfFile.value == mockPdfFile)
        assert(!pdfViewModel.isLoading.value)
        verify(mockPdfUseCase.viewPdfUseCase).invoke(mockContext, url)

    }

    @Test
    fun `test storePdfPage should call use case method`() = runTest {
        // Arrange
        val page = 5
        whenever(storePdfPageUseCase(page)).thenReturn(Unit)

        // Act
        pdfViewModel.storePdfPage(page)
        advanceUntilIdle()

        // Assert
        verify(mockPdfUseCase.storePdfPageUseCase).invoke(page)
    }

    @Test
    fun `test getPdfPage should update pdfPage with collected value`() = runTest {
        // Arrange
        val mockPageFlow = MutableStateFlow(3) // Simulating page flow from use case
        whenever(getPdfPageUseCase()).thenReturn(mockPageFlow)

        // Act
        pdfViewModel.getPdfPage()

        // Wait for the flow to emit
        advanceUntilIdle()

        // Assert
        assert(pdfViewModel.pdfPage.value == 3)
        verify(mockPdfUseCase.getPdfPageUseCase).invoke()

    }
}

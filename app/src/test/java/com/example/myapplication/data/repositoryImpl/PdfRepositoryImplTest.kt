package com.example.myapplication.data.repositoryImpl


import android.content.Context
import com.example.myapplication.utils.SettingsPreference
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File
import javax.net.ssl.HttpsURLConnection
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class PdfRepositoryImplTest {

    private lateinit var pdfRepository: PdfRepositoryImpl
    private lateinit var settingsPreference: SettingsPreference
    private lateinit var context: Context
    private lateinit var url: String

    @Before
    fun setup() {
        // Mocking the dependencies
        settingsPreference = mock()
        context = mock()
        url = "https://source.android.com/docs/compatibility/5.0/android-5.0-cdd.pdf"

        // Create the PdfRepositoryImpl with mocked dependencies
        pdfRepository = PdfRepositoryImpl(settingsPreference)
    }

    @Test
    fun `getPdfFromCacheOrUrl returns cached file when it exists`() = runBlocking {
        // Mock the cache directory and file
        val cacheDir = File("mockCacheDir")
        val cachedFile = File(cacheDir, url)

        // Use 'whenever' to mock the context.cacheDir method
        whenever(context.cacheDir).thenReturn(cacheDir)

        // Use 'whenever' to mock cachedFile.exists() to return true
        whenever(cachedFile.exists()).thenReturn(true)

        // Calling the function
        val result = pdfRepository.getPdfFromCacheOrUrl(context, url)

        // Assertions
        assertNotNull(result) // The result should not be null if the cached file exists
        assert(result == cachedFile) // The result should be the cached file
    }

    @Test
    fun `getPdfFromCacheOrUrl returns null when file cannot be downloaded`() = runBlocking {
        // Mocking the cache directory and HttpURLConnection
        val cacheDir = File("mockCacheDir")

        // Use 'whenever' to mock the context.cacheDir method
        whenever(context.cacheDir).thenReturn(cacheDir)

        // Mock the HttpURLConnection to simulate failure
        val urlConnection = mock<HttpsURLConnection>()
        whenever(urlConnection.responseCode).thenReturn(500) // Simulate a server error (non-200 response code)
        whenever(urlConnection.inputStream).thenThrow(Exception("Failed to download"))

        // Calling the function
        val result = pdfRepository.getPdfFromCacheOrUrl(context, url)

        // Assertions
        assertNull(result) // The result should be null when the download fails
    }

    @Test
    fun `storePdfPage saves page number in settingsPreference`() = runBlocking {
        // Mocking the settingsPreference
        val pageNumber = 1
        pdfRepository.storePdfPage(pageNumber)

        // Verify that the savePdfPage method was called with the correct page number
        verify(settingsPreference).savePdfPage(pageNumber)
    }

    @Test
    fun `getPdfPage returns a Flow of Int`() = runBlocking {
        // Mock the settingsPreference to return a flow
        whenever(settingsPreference.getPdfPage).thenReturn(flowOf(1))

        // Calling the function
        val result = pdfRepository.getPdfPage()

        // Assertions
        result.collect { page ->
            assert(page == 1) // The page number should be 1 as mocked
        }
    }
}

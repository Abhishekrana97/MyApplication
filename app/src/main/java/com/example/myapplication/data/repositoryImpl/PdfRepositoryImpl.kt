package com.example.myapplication.data.repositoryImpl

import android.content.Context
import com.example.myapplication.domain.repository.PdfRepository
import com.example.myapplication.utils.SettingsPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class PdfRepositoryImpl @Inject constructor(private val settingsPreference: SettingsPreference) :
    PdfRepository {

    override suspend fun getPdfFromCacheOrUrl(context: Context, url: String): File? {
        return retrievePdfFromCacheOrUrl(context, url)
    }

    override suspend fun storePdfPage(page: Int) {
        settingsPreference.savePdfPage(page)
    }

    override suspend fun getPdfPage(): Flow<Int> {
        return settingsPreference.getPdfPage
    }

    private suspend fun retrievePdfFromCacheOrUrl(context: Context, url: String): File? {
        return withContext(Dispatchers.IO) { // Switch to the IO thread for network operations
            try {
                // Create or use a cache directory in the app's internal storage
                val cacheDir = File(context.cacheDir, "pdf_cache")
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs() // Create the directory if it doesn't exist
                }

                // Extract the filename from the URL or use a default name
                // https://source.android.com/docs/compatibility/5.0/android-5.0-cdd.pdf
                val fileName = url.substringAfterLast("/") ?: "temp.pdf"
                val cachedFile = File(cacheDir, fileName)
                /*
                 pdf_cache
                   source.android.com/docs/compatibility/5.0/android-5.0-cdd.pdf
                */

                // Check if the file is already in the cache
                if (cachedFile.exists()) {
                    Timber.d("PdfView", "Loading PDF from cache") // Log message for debugging
                    return@withContext cachedFile // Return the cached file
                }

                // Open a connection to download the PDF from the URL
                val urlConnection: HttpURLConnection =
                    (URL(url).openConnection() as HttpsURLConnection)

                // If the response code is 200 (OK), download the file
                if (urlConnection.responseCode == 200) {
                    val inputStream =
                        BufferedInputStream(urlConnection.inputStream) // Read the data
                    val outputStream =
                        FileOutputStream(cachedFile) // This opens a file (represented by cachedFile) on your device where the data will be saved. FileOutputStream is used to write data to this file.

                    // Copy the input stream to the output stream
                    inputStream.copyTo(outputStream)
                    outputStream.close()
                    inputStream.close()

                    Timber.d("PdfView", "PDF cached successfully") // Log message for debugging
                    return@withContext cachedFile // Return the cached file
                }
            } catch (e: Exception) {
                e.printStackTrace() // Print the exception if something goes wrong
            }
            null // Return null if the file could not be downloaded
        }
    }

}
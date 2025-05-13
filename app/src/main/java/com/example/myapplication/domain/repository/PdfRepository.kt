package com.example.myapplication.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PdfRepository {
    suspend fun getPdfFromCacheOrUrl(context: Context, url: String): File?
    suspend fun storePdfPage(page:Int)
    suspend fun getPdfPage(): Flow<Int>
}
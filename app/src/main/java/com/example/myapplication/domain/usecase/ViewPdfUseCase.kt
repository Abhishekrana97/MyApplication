package com.example.myapplication.domain.usecase

import android.content.Context
import com.example.myapplication.domain.repository.PdfRepository
import java.io.File

class ViewPdfUseCase(private val repository: PdfRepository) {
    suspend operator fun invoke(context: Context, url: String): File? {
      return  repository.getPdfFromCacheOrUrl(context, url)
    }
}
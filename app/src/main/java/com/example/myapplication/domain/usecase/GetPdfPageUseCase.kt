package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.PdfRepository
import kotlinx.coroutines.flow.Flow

class GetPdfPageUseCase(private val repository: PdfRepository) {
    suspend operator fun invoke(): Flow<Int> {
        return  repository.getPdfPage()
    }
}
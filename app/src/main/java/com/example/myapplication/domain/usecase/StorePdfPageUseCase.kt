package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.PdfRepository

class StorePdfPageUseCase(private val repository: PdfRepository) {
    suspend operator fun invoke(page: Int) {
        repository.storePdfPage(page)
    }
}
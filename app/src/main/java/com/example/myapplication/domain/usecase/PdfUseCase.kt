package com.example.myapplication.domain.usecase

data class PdfUseCase(val viewPdfUseCase: ViewPdfUseCase,
    val storePdfPageUseCase: StorePdfPageUseCase,
    val getPdfPageUseCase: GetPdfPageUseCase
    )

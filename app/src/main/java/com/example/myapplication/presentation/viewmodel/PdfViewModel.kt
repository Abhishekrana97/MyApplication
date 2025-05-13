package com.example.myapplication.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.PdfUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    private val useCase: PdfUseCase
) : ViewModel() {

    var isLoading = mutableStateOf(true)
    var pdfFile = mutableStateOf<File?>(null)
    val pdfPage = MutableStateFlow(0)

    fun loadPdf(context: Context, url: String) {
        viewModelScope.launch {
            val file = useCase.viewPdfUseCase(context, url)
            pdfFile.value = file
            isLoading.value = false
        }
    }

    fun storePdfPage(page: Int) {
        viewModelScope.launch {
            useCase.storePdfPageUseCase(page)
        }
    }

    fun getPdfPage() {
        viewModelScope.launch {
            useCase.getPdfPageUseCase().collect {
                pdfPage.value = it
            }
        }
    }
}

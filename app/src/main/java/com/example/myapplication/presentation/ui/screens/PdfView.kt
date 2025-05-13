package com.example.myapplication.presentation.ui.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presentation.viewmodel.PdfViewModel
import com.example.myapplication.utils.Constants.PDF_URL
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import timber.log.Timber

@Composable
fun PdfViewScreen() {
    val pdfViewModel = hiltViewModel<PdfViewModel>()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        pdfViewModel.getPdfPage()
        pdfViewModel.loadPdf(context, PDF_URL)
    }

    val pdfPage = pdfViewModel.pdfPage.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (pdfViewModel.isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
            )
        } else {
            pdfViewModel.pdfFile.value?.let { file ->
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 30.dp),
                    factory = { context ->
                        PDFView(context, null).apply {
                            fromFile(file)
                                .scrollHandle(DefaultScrollHandle(context))
                                .defaultPage(pdfPage.value)
                                .enableAntialiasing(true)
                                .onPageChange { page, pageCount ->
                                    pdfViewModel.storePdfPage(page)
                                    Timber.e(">>>>>>>>>>>>>>> $page $pageCount")
                                }
                                .load()
                        }
                    }
                )
            }
        }
    }
}

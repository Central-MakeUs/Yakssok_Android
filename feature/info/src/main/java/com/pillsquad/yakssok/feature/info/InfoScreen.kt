package com.pillsquad.yakssok.feature.info

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.customInsets

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun InfoRoute(
    title: String,
    url: String,
    onNavigateBack: () -> Unit
) {
    val webViewState = remember { mutableStateOf<WebView?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    BackHandler(enabled = true) {
        val webView = webViewState.value
        if (webView?.canGoBack() == true) {
            webView.goBack()
        } else {
            onNavigateBack()
        }
    }

    DisposableEffect(webViewState) {
        onDispose {
            webViewState.value = null
        }
    }

    InfoScreen(
        title = title,
        url = url,
        isLoading = isLoading,
        webViewState = webViewState,
        onNavigateBack = onNavigateBack,
        onUpdateLoading = { isLoading = it }
    )
}

@Composable
private fun InfoScreen(
    title: String,
    url: String,
    isLoading: Boolean,
    webViewState: MutableState<WebView?>,
    onNavigateBack: () -> Unit,
    onUpdateLoading: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YakssokTheme.color.grey100)
            .customInsets(top = true, bottom = true)
    ) {
        YakssokTopAppBar(
            title = title,
            modifier = Modifier.padding(horizontal = 16.dp),
            onBackClick = onNavigateBack
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    color = YakssokTheme.color.grey600
                )
            }
        }

        YakssokWebView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            url = url,
            webViewState = webViewState,
            onPageStarted = {
                onUpdateLoading(true)
            },
            onPageFinished = {
                onUpdateLoading(false)
            },
            onError = { errorMessage ->
                Log.e("WebViewScreen", errorMessage)
                onUpdateLoading(false)
            },
        )
    }
}
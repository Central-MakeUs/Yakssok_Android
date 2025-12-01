package com.pillsquad.yakssok.feature.main

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.pillsquad.yakssok.core.model.DomainException
import com.pillsquad.yakssok.core.ui.compositionlocal.LocalShowErrorSnackBar
import com.pillsquad.yakssok.core.ui.compositionlocal.LocalUpdateSnackBar
import com.pillsquad.yakssok.feature.main.component.MainNavHost
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@Composable
internal fun MainRoute(
    navigator: MainNavigator = rememberMainNavigator(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val localContextResource = LocalContext.current.resources
    val onShowErrorSnackBar: (throwable: Throwable?) -> Unit = { throwable ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                when (throwable) {
                    is DomainException -> {
                        throwable.errorCode.message
                            ?: localContextResource.getString(R.string.error_message_network)
                    }

                    else -> localContextResource.getString(R.string.error_message_unknown)
                }
            )
        }
    }
    val onShowUpdateSnackBar: (action: () -> Unit) -> Unit = { action ->
        coroutineScope.launch {
            val snackbarResult = snackBarHostState.showSnackbar(
                message = localContextResource.getString(R.string.update_ready_to_install),
                actionLabel = localContextResource.getString(R.string.update_action_restart),
                duration = SnackbarDuration.Indefinite
            )

            if (snackbarResult == SnackbarResult.ActionPerformed) {
                action()
            }
        }
    }

    MainScreen(
        navigator = navigator,
        snackBarHostState = snackBarHostState,
        onShowErrorSnackBar = onShowErrorSnackBar,
        onShowUpdateSnackBar = onShowUpdateSnackBar
    )
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    snackBarHostState: SnackbarHostState,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onShowUpdateSnackBar: (() -> Unit) -> Unit
) {
    CompositionLocalProvider(
        LocalShowErrorSnackBar provides onShowErrorSnackBar,
        LocalUpdateSnackBar provides onShowUpdateSnackBar
    ) {
        Scaffold(
            modifier = modifier,
            snackbarHost = { SnackbarHost(snackBarHostState) }
        ) { padding ->
            MainNavHost(
                navigator = navigator,
                padding = padding
            )
        }
    }
}

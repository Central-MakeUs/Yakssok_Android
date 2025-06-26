package com.pillsquad.yakssok.feature.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pillsquad.yakssok.feature.main.component.MainBottomBar
import com.pillsquad.yakssok.feature.main.component.MainNavHost
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@Composable
internal fun MainScreen(
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
                    is UnknownHostException -> localContextResource.getString(R.string.error_message_network)
                    else -> localContextResource.getString(R.string.error_message_unknown)
                }
            )
        }
    }

    MainScreenContent(
        navigator = navigator,
        onShowErrorSnackBar = onShowErrorSnackBar,
        snackBarHostState = snackBarHostState
    )
}

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    Scaffold(
        modifier = modifier,
        content = { padding ->
            MainNavHost(
                navigator = navigator,
                padding = padding,
                onShowErrorSnackBar = onShowErrorSnackBar,
            )
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 8.dp, end = 8.dp, bottom = 28.dp),
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    )
}

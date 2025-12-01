package com.pillsquad.yakssok.core.ui.compositionlocal

import androidx.compose.runtime.staticCompositionLocalOf

val LocalShowErrorSnackBar = staticCompositionLocalOf<(throwable: Throwable?) -> Unit> {
    { _ -> error("No Snackbar provider") }
}

val LocalUpdateSnackBar = staticCompositionLocalOf<(action: () -> Unit) -> Unit> {
    { error("No Snackbar provider") }
}
package com.pillsquad.yakssok.feature.intro

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.CollectEvent
import com.pillsquad.yakssok.core.ui.ext.OnResumeEffect
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.intro.component.SettingAlertDialog
import com.pillsquad.yakssok.feature.intro.component.TestAccountDialog
import com.pillsquad.yakssok.feature.intro.util.isNotificationGranted

@Composable
internal fun IntroRoute(
    viewModel: IntroViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var isTestDialogShow by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalView.current.findViewTreeLifecycleOwner()
    val context = LocalContext.current
    val activity = LocalView.current.context as Activity

    var showSetting by remember { mutableStateOf(false) }
    var pendingCheck by remember { mutableStateOf(false) }

    var permissionRequested by rememberSaveable { mutableStateOf(false) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (granted) {
                viewModel.postPushAgreement(true)
            } else {
                showSetting = true
            }
        } else {
            viewModel.postPushAgreement(true)
        }
    }

    BackHandler {
        if (uiState.isHaveToSignup && !uiState.isLoading) {
            viewModel.deleteLoginInfo()
        }
    }

    CollectEvent(viewModel.event) {
        when(it) {
            IntroEvent.NavigateHome -> onNavigateHome()
            is IntroEvent.ShowToast -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && pendingCheck) {
                pendingCheck = false
                viewModel.postPushAgreement(isNotificationGranted(context))
            }
        }
        lifecycleOwner?.lifecycle?.addObserver(observer)
        onDispose { lifecycleOwner?.lifecycle?.removeObserver(observer) }
    }

    LaunchedEffect(uiState.loginSuccess) {
        if (!uiState.loginSuccess) return@LaunchedEffect
        if (permissionRequested) return@LaunchedEffect

        if (isNotificationGranted(context)) {
            viewModel.postPushAgreement(true)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionRequested = true
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                permissionRequested = true
                viewModel.postPushAgreement(true)
            }
        }
    }

    if (showSetting) {
        SettingAlertDialog(
            onDismiss = {
                showSetting = false
                viewModel.postPushAgreement(false)
            },
            onConfirm = {
                showSetting = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
                    }
                    pendingCheck = true
                    activity.startActivity(intent)
                }
            }
        )
    }

    if (isTestDialogShow) {
        TestAccountDialog(
            onDismiss = { isTestDialogShow = false },
            onConfirm = {
                isTestDialogShow = false
                viewModel.testLoginUser()
            }
        )
    }

    when {
        uiState.isLoading -> {
            val (loadingBackground, loadingIcon) = if (uiState.token.isBlank()) {
                YakssokTheme.color.primary400 to R.drawable.img_splash_logo
            } else {
                YakssokTheme.color.grey50 to R.drawable.img_signup_loading
            }

            LoadingScreen(
                backgroundColor = loadingBackground,
                iconId = loadingIcon
            )
        }

        uiState.isHaveToSignup -> {
            SignupScreen(
                nickName = uiState.nickName,
                onValueChange = viewModel::changeNickName,
                enabled = uiState.isEnabled,
                onBackClick = viewModel::deleteLoginInfo,
                onSignupClick = viewModel::putUserInitial
            )
        }

        else -> {
            LoginScreen(
                onClick = { viewModel.handleSignIn(context) },
                onLongClick = { isTestDialogShow = true }
            )
        }
    }
}

@Composable
private fun LoginScreen(
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey50)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .width(92.dp)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = onLongClick,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                painter = painterResource(R.drawable.img_login_logo),
                contentDescription = "yakssok logo",
                tint = Color.Unspecified
            )
        }

        YakssokButton(
            modifier = Modifier.fillMaxWidth(),
            icon = R.drawable.ic_kakao,
            text = stringResource(R.string.kakao),
            round = 12.dp,
            backgroundColor = Color(0xFFFDDC3F),
            contentColor = YakssokTheme.color.grey950,
            onClick = onClick
        )
    }
}
package com.pillsquad.yakssok.feature.intro

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.component.YakssokDialog
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.intro.component.NotificationAlertDialog
import com.pillsquad.yakssok.feature.intro.component.SettingAlertDialog

@Composable
internal fun IntroRoute(
    viewModel: IntroViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalView.current.findViewTreeLifecycleOwner()
    val context = LocalContext.current
    val activity = LocalView.current.context as Activity

    var showSetting by remember { mutableStateOf(false) }
    var isPermissionGranted by remember { mutableStateOf(false) }
    var pendingCheck by remember { mutableStateOf(false) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                isGranted -> isPermissionGranted = true
                else -> showSetting = true
            }
        } else {
            isPermissionGranted = true
        }
    }

    if (showSetting) {
        SettingAlertDialog(
            onDismiss = {
                showSetting = false
                if (uiState.loginSuccess) {
                    onNavigateHome()
                } else {
                    viewModel.signupUser(false)
                }
            },
            onConfirm = {
                showSetting = false

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val intent = Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, activity.packageName)
                    }
                    pendingCheck = true
                    activity.startActivity(intent)
                }
            }
        )
    }

    BackHandler {
        if (uiState.isHaveToSignup && !uiState.isLoading) {
            viewModel.deleteLoginInfo()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && pendingCheck) {
                pendingCheck = false

                val isGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                } else {
                    true
                }

                Log.d("PermissionCheck", "After settings, granted = $isGranted")

                if (uiState.loginSuccess) {
                    onNavigateHome()
                } else {
                    viewModel.signupUser(isGranted)
                }
            }
        }

        lifecycleOwner?.lifecycle?.addObserver(observer)
        onDispose {
            lifecycleOwner?.lifecycle?.removeObserver(observer)
        }
    }

    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                onNavigateHome()
            }
        }
    }

    LaunchedEffect(isPermissionGranted) {
        if (!isPermissionGranted) return@LaunchedEffect

        if (uiState.loginSuccess) {
            onNavigateHome()
        } else {
            viewModel.signupUser(true)
        }
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
                onSignupClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        viewModel.signupUser(true)
                    }
                }
            )
        }

        else -> {
            LoginScreen(
                onClick = { viewModel.handleSignIn(context) }
            )
        }
    }
}

@Composable
private fun LoginScreen(
    onClick: () -> Unit = {}
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
                modifier = Modifier.width(92.dp),
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
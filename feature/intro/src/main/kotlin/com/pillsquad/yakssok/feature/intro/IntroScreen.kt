package com.pillsquad.yakssok.feature.intro

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.compositionlocal.LocalShowErrorSnackBar
import com.pillsquad.yakssok.core.ui.compositionlocal.LocalUpdateSnackBar
import com.pillsquad.yakssok.core.ui.ext.CollectEvent
import com.pillsquad.yakssok.core.ui.ext.OnResumeEffect
import com.pillsquad.yakssok.core.ui.ext.isNotificationGranted
import com.pillsquad.yakssok.core.ui.ext.openPlayStore
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.intro.component.SettingAlertDialog
import com.pillsquad.yakssok.feature.intro.component.TestAccountDialog
import com.pillsquad.yakssok.feature.intro.component.UpdateDialog
import com.pillsquad.yakssok.feature.intro.util.await

private const val FLEXIBLE = 0
private const val IMMEDIATE = 1
private const val FLEXDIALOG = 2
private const val IMMEDIALOG = 3

@Composable
internal fun IntroRoute(
    viewModel: IntroViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit = {},
    onNavigateMate: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val activity = context as? Activity ?: return
    val appUpdateManager: AppUpdateManager = remember { AppUpdateManagerFactory.create(context) }

    val showErrorSnackBar = LocalShowErrorSnackBar.current
    val showUpdateSnackBar = LocalUpdateSnackBar.current

    var updateMode by remember { mutableStateOf<Int?>(null) }
    var showSetting by remember { mutableStateOf(false) }
    var pendingCheck by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf<Int?>(null) }
    var showTestDialogShow by remember { mutableStateOf(false) }
    var permissionRequested by rememberSaveable { mutableStateOf(false) }

    val appUpdateResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        // 사용자가 업데이트를 취소하는 경우, 앱 종료
        if (result.resultCode == Activity.RESULT_CANCELED) {
            if (updateMode == AppUpdateType.IMMEDIATE) {
                activity.finish()
            } else {
                viewModel.checkToken()
            }
        }
    }

    val installStateUpdatedListener = remember {
        InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                showUpdateSnackBar {
                    appUpdateManager.completeUpdate()
                }
            }
        }
    }

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

    DisposableEffect(Unit) {
        appUpdateManager.registerListener(installStateUpdatedListener)
        onDispose { appUpdateManager.unregisterListener(installStateUpdatedListener) }
    }

    OnResumeEffect {
        viewModel.checkAppUpdate()
        if (pendingCheck) {
            pendingCheck = false
            viewModel.postPushAgreement(isNotificationGranted(context))
        }
    }

    BackHandler {
        if (uiState.isHaveToSignup && !uiState.isLoading) {
            viewModel.deleteLoginInfo()
        } else if (!uiState.loginSuccess || !uiState.token.isBlank()) {
            activity.finish()
        }
    }

    CollectEvent(viewModel.event) {
        when (it) {
            IntroEvent.NavigateHome -> onNavigateHome()
            is IntroEvent.NavigateHomeThenMate -> {
                onNavigateHome()
                onNavigateMate(it.code)
            }
            is IntroEvent.ShowErrorSnackbar -> showErrorSnackBar(it.throwable)
            IntroEvent.ShowSoftUpdate -> updateMode = FLEXIBLE
            IntroEvent.ShowForceUpdate -> updateMode = IMMEDIATE
            IntroEvent.ShowNetworkDialog -> showUpdateDialog = FLEXDIALOG
            IntroEvent.ShowErrorDialog -> showUpdateDialog = IMMEDIALOG
        }
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

    LaunchedEffect(updateMode) {
        val type = if (updateMode != null) {
            if (updateMode == FLEXIBLE) AppUpdateType.FLEXIBLE else AppUpdateType.IMMEDIATE
        } else {
            return@LaunchedEffect
        }

        try {
            val appUpdateInfo = appUpdateManager.appUpdateInfo.await()

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                if (appUpdateInfo.isUpdateTypeAllowed(type)) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        appUpdateResultLauncher,
                        AppUpdateOptions.newBuilder(type).build()
                    )
                } else {
                    showUpdateDialog = updateMode
                }
            } else {
                showUpdateDialog = updateMode
            }
        } catch (e: Exception) {
            Log.e("IntroRoute", "Error checking app update", e)
            showUpdateDialog = updateMode
        }
    }

    LaunchedEffect(Unit) {
        val uri = activity.intent?.data
        val inviteCode = uri?.getQueryParameter("code")

        if (!inviteCode.isNullOrBlank()) viewModel.setPendingInviteCode(inviteCode)

        val fromOneLink =
            uri?.host.equals("yakssok.onelink.me", ignoreCase = true) ||
                    uri?.scheme.equals("yakssok", ignoreCase = true)
        viewModel.markLaunchedFromOneLink(fromOneLink)
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

    if (showTestDialogShow) {
        TestAccountDialog(
            onDismiss = { showTestDialogShow = false },
            onConfirm = {
                showTestDialogShow = false
                viewModel.testLoginUser()
            }
        )
    }

    showUpdateDialog?.let { type ->
        UpdateDialog(
            type = type,
            onConfirm = {
                if (type == 0 || type == 1) {
                    openPlayStore(activity)
                } else {
                    activity.finish()
                }
            },
            onDismiss = {
                if (type == 0) {
                    showUpdateDialog = null
                } else {
                    activity.finish()
                }
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
                onClick = { viewModel.handleSignIn(activity) },
                onLongClick = { showTestDialogShow = true }
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
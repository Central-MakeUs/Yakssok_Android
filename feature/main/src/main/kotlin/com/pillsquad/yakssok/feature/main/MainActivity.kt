package com.pillsquad.yakssok.feature.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        appUpdateManager = AppUpdateManagerFactory.create(this)
        checkUpdateAndLaunchImmediate()

        setContent {
            val navigator: MainNavigator = rememberMainNavigator()
            YakssokTheme {
                MainScreen(
                    navigator = navigator,
                    viewModel = viewModel
                )
            }
        }
    }

    private fun checkUpdateAndLaunchImmediate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            val updateAvailable =
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val allowedImmediate =
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)

            if (updateAvailable && allowedImmediate) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
                        UPDATE_REQUEST_CODE
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "업데이트를 시작할 수 없습니다. 앱을 종료합니다.", Toast.LENGTH_LONG).show()
                    finishAffinity()
                }
            }
        }.addOnFailureListener {
            it.printStackTrace()
            Toast.makeText(this, "업데이트를 확인할 수 없습니다. 앱을 종료합니다.", Toast.LENGTH_LONG).show()
            finishAffinity()
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() ==
                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    this,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
                    UPDATE_REQUEST_CODE
                )
            }
        }
    }

    companion object {
        private const val UPDATE_REQUEST_CODE = 1234
    }
}
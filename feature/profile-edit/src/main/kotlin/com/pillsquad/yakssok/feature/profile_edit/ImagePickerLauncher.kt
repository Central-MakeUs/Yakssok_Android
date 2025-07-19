package com.pillsquad.yakssok.feature.profile_edit

import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberImagePickerLauncher(): ImagePickerLauncher {
    val context = LocalContext.current
    val launcher = remember { ImagePickerLauncher(context) }

    launcher.ProvideLauncher(
        imagePickerProvider = { onSucceed ->
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = {
                    onSucceed(it)
                }
            )
        }, photoPickerProvider = { onSucceed ->
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    onSucceed(it)
                }
            )
        }
    )

    return launcher
}

class ImagePickerLauncher(
    context: Context
) {
    private var onSucceed: (Uri?) -> Unit = {}
    private var imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>? = null
    private var photoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>? =
        null
    val isPhotoPickerAvailable = ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable(context)

    @Composable
    fun ProvideLauncher(
        imagePickerProvider: @Composable ((Uri?) -> Unit) -> ManagedActivityResultLauncher<String, Uri?>,
        photoPickerProvider: @Composable ((Uri?) -> Unit) -> ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
    ) {
        imagePickerLauncher = imagePickerProvider { uri ->
            onSucceed(uri)
        }
        photoPickerLauncher = photoPickerProvider { uri ->
            onSucceed(uri)
        }
    }


    fun launch(onFinished: (Uri?) -> Unit) {
        onSucceed = { uri -> onFinished(uri) }
        if (isPhotoPickerAvailable) {
            photoPickerLauncher?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) ?: return
        } else {
            imagePickerLauncher?.launch("image/*") ?: return
        }
    }
}
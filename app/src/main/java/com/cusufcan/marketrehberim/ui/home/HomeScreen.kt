package com.cusufcan.marketrehberim.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.cusufcan.marketrehberim.BuildConfig
import com.cusufcan.marketrehberim.common.createImageFile
import com.cusufcan.marketrehberim.ui.home.components.CatalogueItemCard
import com.cusufcan.marketrehberim.ui.home.components.ImagePickerBottomSheet
import com.cusufcan.marketrehberim.ui.home.components.ImagePickerCard
import com.cusufcan.marketrehberim.ui.home.components.TitleText
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToResult: (uri: Uri?, catalogueItem: String?) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val catalogueItems by homeViewModel.catalogueItems.collectAsState()
    val isImagePickerVisible by homeViewModel.isImagePickerVisible.collectAsState()

    val context = LocalContext.current
    val imageUri = remember {
        context.createImageFile().let {
            FileProvider.getUriForFile(
                Objects.requireNonNull(context),
                BuildConfig.APPLICATION_ID + ".provider",
                it,
            )
        }
    }

    val cameraLauncher = createCameraLauncher(
        onImageCaptured = { success ->
            homeViewModel.setImageClicked(false)
            if (success) {
                onNavigateToResult(imageUri, null)
            }
        },
    )

    val permissionLauncher = createCameraPermissionLauncher(cameraLauncher, imageUri)

    val galleryLauncher = createGalleryLauncher(
        onImageSelected = { uri ->
            homeViewModel.setImageClicked(false)
            uri?.let {
                onNavigateToResult(it, null)
            }
        },
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Anasayfa")
                },
            )
        }) { padding ->
        if (isImagePickerVisible) {
            ImagePickerBottomSheet(
                onDismiss = {
                    homeViewModel.setImageClicked(false)
                },
                onCameraClick = {
                    checkCameraPermissionAndLaunch(
                        context,
                        permissionLauncher,
                        cameraLauncher,
                        imageUri,
                    )
                },
                onGalleryClick = {
                    galleryLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                },
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp, start = 12.dp, end = 12.dp),
        ) {
            item {
                TitleText(text = "Resim Yükle")
            }
            item {
                ImagePickerCard(onClick = {
                    homeViewModel.setImageClicked(true)
                })
            }
            item {
                TitleText(text = "Ürünler")
            }
            items(
                items = catalogueItems,
                key = { item -> item.name },
            ) { item ->
                CatalogueItemCard(
                    item = item,
                    onClick = { onNavigateToResult(null, it.itemName) },
                )
            }
        }
    }
}

private fun checkCameraPermissionAndLaunch(
    context: Context,
    permissionLauncher: ActivityResultLauncher<String>,
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    imageUri: Uri,
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(
        context, Manifest.permission.CAMERA,
    )
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        cameraLauncher.launch(imageUri)
    } else {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}

@Composable
private fun createCameraLauncher(onImageCaptured: (Boolean) -> Unit): ManagedActivityResultLauncher<Uri, Boolean> {
    return rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        onImageCaptured(success)
    }
}

@Composable
private fun createCameraPermissionLauncher(
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    imageUri: Uri,
): ActivityResultLauncher<String> {
    return rememberLauncherForActivityResult(RequestPermission()) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(imageUri)
        }
    }
}

@Composable
private fun createGalleryLauncher(onImageSelected: (Uri?) -> Unit): ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> {
    return rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        onImageSelected(uri)
    }
}
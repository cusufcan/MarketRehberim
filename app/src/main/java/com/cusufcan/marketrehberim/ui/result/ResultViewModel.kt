package com.cusufcan.marketrehberim.ui.result

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cusufcan.marketrehberim.common.Resource
import com.cusufcan.marketrehberim.data.model.ResultItem
import com.cusufcan.marketrehberim.data.repository.ResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val resultRepository: ResultRepository,
) : ViewModel() {
    private var _resultList = MutableStateFlow<Resource<List<ResultItem>>>(Resource.Idle())
    val resultList: StateFlow<Resource<List<ResultItem>>> get() = _resultList.asStateFlow()

    fun fetchDataFromImage(uri: Uri, contentResolver: ContentResolver) = viewModelScope.launch {
        try {
            _resultList.emit(Resource.Loading())
            val convertedImage = uriToMultipartBodyPart(
                uri = uri,
                contentResolver = contentResolver,
            )
            Log.d("MercanLogger", "fetchDataFromImage: $convertedImage")
            val result = resultRepository.fetchDataFromImage(convertedImage)
            _resultList.emit(Resource.Success(result))
        } catch (e: HttpException) {
            _resultList.emit(Resource.Error("${e.localizedMessage}"))
        } catch (e: IOException) {
            _resultList.emit(Resource.Error("${e.localizedMessage}"))
        }
    }

    fun fetchDataFromName(name: String) = viewModelScope.launch {
        try {
            _resultList.emit(Resource.Loading())
            val result = resultRepository.fetchDataFromName(name).sortedBy { it.price.toDouble() }
            _resultList.emit(Resource.Success(result))
        } catch (e: HttpException) {
            _resultList.emit(Resource.Error("${e.localizedMessage}"))
        } catch (e: IOException) {
            _resultList.emit(Resource.Error("${e.localizedMessage}"))
        }
    }

    fun uriToMultipartBodyPart(
        uri: Uri,
        contentResolver: ContentResolver,
        partName: String = "image",
    ): MultipartBody.Part {
        val fileName = getFileNameFromUri(contentResolver, uri)
        val type = contentResolver.getType(uri) ?: "application/octet-stream"

        val inputStream = contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.readBytes()
        inputStream?.close()

        val requestBody = fileBytes?.toRequestBody(type.toMediaTypeOrNull())
            ?: throw IOException("Request body is null")
        return MultipartBody.Part.createFormData(partName, fileName, requestBody)
    }

    fun getFileNameFromUri(contentResolver: ContentResolver, uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    result = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/') ?: -1
            if (cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result
    }
}
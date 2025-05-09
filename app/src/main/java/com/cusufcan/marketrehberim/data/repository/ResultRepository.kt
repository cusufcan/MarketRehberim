package com.cusufcan.marketrehberim.data.repository

import com.cusufcan.marketrehberim.data.source.remote.ApiService
import okhttp3.MultipartBody
import javax.inject.Inject

class ResultRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun fetchDataFromImage(image: MultipartBody.Part) = apiService.fetchDataFromImage(image)

    suspend fun fetchDataFromName(name: String) = apiService.fetchDataFromName(name)
}
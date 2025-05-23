package com.cusufcan.marketrehberim.data.source.remote

import com.cusufcan.marketrehberim.data.model.ResultItem
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun fetchDataFromImage(@Part image: MultipartBody.Part): List<ResultItem>

    @GET("{name}")
    suspend fun fetchDataFromName(@Path("name") name: String): List<ResultItem>
}
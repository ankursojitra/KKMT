package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UploadSelfieService {
    @POST("api-selfie-document")
    @FormUrlEncoded
    fun UploadSelfieDoc(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<UploadDoc_Model>

}
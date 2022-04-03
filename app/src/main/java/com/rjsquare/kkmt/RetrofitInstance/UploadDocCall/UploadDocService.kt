package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UploadDocService {
    @POST("api-upload-document")
    @FormUrlEncoded
    fun UploadDoc(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<UploadDoc_Model>

}
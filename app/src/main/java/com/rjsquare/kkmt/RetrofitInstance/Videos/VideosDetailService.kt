package com.rjsquare.kkmt.RetrofitInstance.Events


import retrofit2.Call
import retrofit2.http.*

interface VideosDetailService {
    @POST("api-video-details")
    @FormUrlEncoded
    fun GetVideosDetail(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<VideoDetail_Model>
}
package com.rjsquare.kkmt.RetrofitInstance.Events


import retrofit2.Call
import retrofit2.http.*

interface VideosService {
    @POST("api-video-list")
    @FormUrlEncoded
    fun GetVideosData(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<Videos_Model>
}
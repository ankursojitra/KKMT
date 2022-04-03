package com.rjsquare.kkmt.RetrofitInstance.Events


import retrofit2.Call
import retrofit2.http.*

interface VideoQuestionCompleteService {
    @POST("api-complete-questionery")
    @FormUrlEncoded
    fun GetVideoQuestionComplete(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<VideoQuestionComplete_Model>
}
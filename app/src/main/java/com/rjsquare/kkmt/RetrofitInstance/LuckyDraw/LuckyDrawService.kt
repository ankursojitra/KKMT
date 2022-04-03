package com.rjsquare.kkmt.RetrofitInstance.Events


import retrofit2.Call
import retrofit2.http.*

interface LuckyDrawService {
    @POST("api-lucky-draw")
    @FormUrlEncoded
    fun GetLuckyDrawData(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<LuckyDraw_Model>
}
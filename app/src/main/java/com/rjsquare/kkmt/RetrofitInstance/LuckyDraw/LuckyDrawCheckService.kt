package com.rjsquare.kkmt.RetrofitInstance.Events


import retrofit2.Call
import retrofit2.http.*

interface LuckyDrawCheckService {
    @POST("api-lucky-draw-check")
    @FormUrlEncoded
    fun GetLuckyDrawCheckData(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<LuckyDrawCheck_Model>
}
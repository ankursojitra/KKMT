package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import retrofit2.Call
import retrofit2.http.*

interface LogInCallService {
    @POST("api-login")
    @FormUrlEncoded
    fun GetLoginData(@FieldMap params: Map<String, String>): Call<UserLogIn_Model>
}
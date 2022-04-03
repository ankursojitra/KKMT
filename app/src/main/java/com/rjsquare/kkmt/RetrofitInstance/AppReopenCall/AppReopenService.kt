package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import retrofit2.Call
import retrofit2.http.*

interface AppReopenService {
    @POST("api-add-app-histroy")
    @FormUrlEncoded
    fun GetUserData(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<UserInfoData_Model>

}
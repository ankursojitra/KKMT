package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import retrofit2.Call
import retrofit2.http.*

interface RegisterUserService {

    @POST("api-register")
    @FormUrlEncoded
        fun GetRegisterUserData(@FieldMap params: Map<String, String>): Call<UserInfoData_Model>

}
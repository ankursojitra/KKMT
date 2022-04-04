package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.CustomerHistoryModel
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import retrofit2.Call
import retrofit2.http.*

interface CustomerHistoryService {
    @POST("api-customer-history")
    @FormUrlEncoded
    fun GetCustomerHistoryData(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<CustomerHistoryModel>

}
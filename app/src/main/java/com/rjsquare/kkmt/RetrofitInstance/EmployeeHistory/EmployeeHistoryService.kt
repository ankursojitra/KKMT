package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.CustomerHistoryModel
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.EmployeeHistoryModel
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import retrofit2.Call
import retrofit2.http.*

interface EmployeeHistoryService {
    @POST("api-employee-history")
    @FormUrlEncoded
    fun GetEmployeeHistoryData(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<EmployeeHistoryModel>

}
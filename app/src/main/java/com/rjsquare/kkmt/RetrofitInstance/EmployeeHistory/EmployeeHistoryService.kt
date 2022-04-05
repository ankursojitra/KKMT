package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.EmployeeHistoryModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface EmployeeHistoryService {
    @POST("api-employee-history")
    @FormUrlEncoded
    fun GetEmployeeHistoryData(
        @FieldMap params: Map<String, String>,
        @Header("token") token: String
    ): Call<EmployeeHistoryModel>

}
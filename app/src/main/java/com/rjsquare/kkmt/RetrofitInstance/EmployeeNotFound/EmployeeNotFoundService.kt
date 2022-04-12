package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.BusinessNotFoundModel
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.EmployeeNotFoundModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface EmployeeNotFoundService {
    @FormUrlEncoded
    @POST("api-employee-not-found")
    fun GetEmployeeNotFoundData(
        @FieldMap params: Map<String, String>,
        @Header("token") token: String
    ): Call<EmployeeNotFoundModel>

}
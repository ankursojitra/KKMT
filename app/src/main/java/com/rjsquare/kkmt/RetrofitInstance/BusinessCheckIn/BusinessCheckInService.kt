package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.BusinessCheckInModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface BusinessCheckInService {
    @FormUrlEncoded
    @POST("api-business-check-in")
    fun GetBusinessCheckInData(
        @FieldMap params: Map<String, String>,
        @Header("token") token: String
    ): Call<BusinessCheckInModel>

}
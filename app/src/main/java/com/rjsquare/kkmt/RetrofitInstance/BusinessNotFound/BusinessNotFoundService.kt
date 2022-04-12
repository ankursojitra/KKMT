package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.BusinessNotFoundModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface BusinessNotFoundService {
    @FormUrlEncoded
    @POST("api-business-not-found")
    fun GetBusinessNotFoundData(
        @FieldMap params: Map<String, String>,
        @Header("token") token: String
    ): Call<BusinessNotFoundModel>

}
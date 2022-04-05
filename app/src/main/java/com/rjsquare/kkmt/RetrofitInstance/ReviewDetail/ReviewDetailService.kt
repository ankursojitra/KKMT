package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.ReviewDetailModel
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ReviewDetailService {
    @POST("api-review-details")
    @FormUrlEncoded
    fun GetReviewDetailData(
        @FieldMap params: Map<String, String>,
        @Header("token") token: String
    ): Call<ReviewDetailModel>

}
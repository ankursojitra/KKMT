package com.rjsquare.kkmt.RetrofitInstance.PickUpLocation

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PickUpLocationService {
    @POST("api-peckup-location")
    @FormUrlEncoded
    fun GetPickUpLocationData(): Call<PickUpLocation_Model>
}
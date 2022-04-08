package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.MasterBeaconModel
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.SlaveBeaconModel
import retrofit2.Call
import retrofit2.http.*

interface SlaveBeaconService {
    @FormUrlEncoded
    @POST("api-get-employee-beacon-details")
    fun GetSlaveBeaconData(
        @FieldMap params: Map<String, String>,
        @Field("becon_list[]") list: ArrayList<String>,
        @Header("token") token: String
    ): Call<SlaveBeaconModel>

}
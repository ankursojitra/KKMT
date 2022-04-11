package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.MasterBeaconModel
import retrofit2.Call
import retrofit2.http.*

interface MasterBeaconService {
    @FormUrlEncoded
    @POST("api-get-bussiness-beacon")
    fun GetBusinessBeaconData(
        @FieldMap params: Map<String, String>,
        @Field("becon_list[]") list: ArrayList<String>,
        @Header("token") token: String
    ): Call<MasterBeaconModel>

}
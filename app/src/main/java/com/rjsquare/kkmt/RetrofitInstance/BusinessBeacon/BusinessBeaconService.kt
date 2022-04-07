package com.rjsquare.kkmt.RetrofitInstance.LogInCall


import com.rjsquare.kkmt.RetrofitInstance.OTPCall.BusinessBeaconModel
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.CustomerHistoryModel
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*

interface BusinessBeaconService {
    @FormUrlEncoded
    @POST("api-get-bussiness-beacon")
    fun GetBusinessBeaconData(@FieldMap params: Map<String, String>, @Field("becon_list[]") list: ArrayList<String>, @Header("token") token:String): Call<BusinessBeaconModel>

}
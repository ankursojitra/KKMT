package com.rjsquare.kkmt.RetrofitInstance.Leaderboard


import retrofit2.Call
import retrofit2.http.*

interface LeaderboardCustomerService {
    @POST("api-customer-leaderboard")
    @FormUrlEncoded
    fun GetLeaderBoardData(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<LeaderboardCustomer_Model>
}
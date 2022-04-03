package com.rjsquare.kkmt.RetrofitInstance.Leaderboard


import retrofit2.Call
import retrofit2.http.*

interface LeaderboardEmployeeService {
    @POST("api-employee-leaderboard")
    @FormUrlEncoded
    fun GetLeaderBoardEmpData(@FieldMap params: Map<String, String>,@Header("token") token:String): Call<LeaderboardEmployee_Model>
}
package com.rjsquare.kkmt.RetrofitInstance.Events


import retrofit2.Call
import retrofit2.http.*

interface EventsService {
    @POST("api-get-event-list")
    @FormUrlEncoded
    fun GetEventsData(@FieldMap params: Map<String, String>): Call<Events_Model>
}
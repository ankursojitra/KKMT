package com.rjsquare.kkmt.RetrofitInstance.Events

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Events_Model : Serializable {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: ArrayList<EventsData>? = null

    class EventsData {

        @SerializedName("title")
        var title: String? = null

        @SerializedName("id")
        var id: String? = null

        @SerializedName("description")
        var description: String? = null

        @SerializedName("location")
        var location: String? = null

        @SerializedName("date")
        var date: String? = null

        @SerializedName("created_at")
        var created_at: String? = null

        @SerializedName("updated_at")
        var updated_at: String? = null

        @SerializedName("time")
        var time: String? = null

        @SerializedName("image")
        var image: ArrayList<String>? = null

    }

}

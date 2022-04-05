package com.rjsquare.kkmt.RetrofitInstance.PickUpLocation

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PickUpLocation_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: ArrayList<LocationData>? = null

    class LocationData {

        @SerializedName("id")
        var id: String? = null

        @SerializedName("location")
        var location: String? = null

        @SerializedName("created_timestamp")
        var created_timestamp: String? = null

        @SerializedName("updated_timestamp")
        var updated_timestamp: String? = null

    }
}

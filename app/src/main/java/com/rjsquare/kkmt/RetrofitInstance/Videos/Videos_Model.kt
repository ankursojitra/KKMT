package com.rjsquare.kkmt.RetrofitInstance.Events

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Videos_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: ArrayList<VideoData>? = null

    class VideoData {

        @SerializedName("id")
        var id: String? = null

        @SerializedName("title")
        var title: String? = null

        @SerializedName("features")
        var features: String? = null

        @SerializedName("tag")
        var tag: String? = null

        @SerializedName("credit")
        var credit: String? = null

        @SerializedName("video")
        var video: String? = null

        @SerializedName("description")
        var description: String? = null

        @SerializedName("type")
        var type: String? = null

        @SerializedName("is_deleted")
        var is_deleted: String? = null

        @SerializedName("created_at")
        var created_at: String? = null

        @SerializedName("updated_at")
        var updated_at: String? = null

    }
}

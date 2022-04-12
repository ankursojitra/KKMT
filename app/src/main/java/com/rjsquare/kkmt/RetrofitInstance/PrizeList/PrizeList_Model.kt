package com.rjsquare.kkmt.RetrofitInstance.PickUpLocation

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class PrizeList_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: ArrayList<PrizeData>? = null

    class PrizeData {

        @SerializedName("id")
        var id: String? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("rankid")
        var rankid: String? = null

        @SerializedName("prize_list")
        var prize_list: ArrayList<Prize>? = null

        class Prize {

            @SerializedName("id")
            var id: String? = null

            @SerializedName("prize_id")
            var prize_id: String? = null

            @SerializedName("title")
            var title: String? = null

            @SerializedName("category")
            var category: String? = null

            @SerializedName("brand")
            var brand: String? = null

            @SerializedName("description")
            var description: String? = null

            @SerializedName("is_deleted")
            var is_deleted: String? = null

            @SerializedName("created_at")
            var created_at: String? = null

            @SerializedName("updated_at")
            var updated_at: String? = null

            @SerializedName("created_timestamp")
            var created_timestamp: String? = null

            @SerializedName("updated_timestamp")
            var updated_timestamp: String? = null

            @SerializedName("rank")
            var rank: String? = null

            @SerializedName("image")
            var image: ArrayList<String>? = null

        }

    }
}

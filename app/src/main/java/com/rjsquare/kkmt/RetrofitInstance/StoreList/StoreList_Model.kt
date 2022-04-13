package com.rjsquare.kkmt.RetrofitInstance.PickUpLocation

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class StoreList_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: ArrayList<StoreItemData>? = null

    class StoreItemData {

        @SerializedName("id")
        var id: String? = null

        @SerializedName("level")
        var level: String? = null

        @SerializedName("store_item")
        var store_item: ArrayList<StoreItem>? = null

        class StoreItem {

            @SerializedName("id")
            var id: String? = null

            @SerializedName("title")
            var title: String? = null

            @SerializedName("category")
            var category: String? = null

            @SerializedName("brand")
            var brand: String? = null

            @SerializedName("description")
            var description: String? = null

            @SerializedName("level")
            var level: String? = null

            @SerializedName("credit_required")
            var credit_required: String? = null

            @SerializedName("quantity")
            var quantity: String? = null

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

            @SerializedName("status")
            var status: String? = null

            @SerializedName("is_redeem")
            var is_redeem: String? = null

            @SerializedName("image")
            var image: ArrayList<String>? = null

        }
    }
}

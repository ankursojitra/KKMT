package com.rjsquare.kkmt.RetrofitInstance.OTPCall

import com.google.gson.annotations.SerializedName

class ChallangesModel {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: ArrayList<Challange>? = null

    class Challange {

        @SerializedName("id")
        var id: String? = null

        @SerializedName("total_credit")
        var total_credit: String? = null

        @SerializedName("challange_status")
        var challange_status: String? = null

        @SerializedName("sub_challanges")
        var sub_challanges: ArrayList<SubChallanges>? = null

        class SubChallanges {

            @SerializedName("id")
            var id: String? = null

            @SerializedName("challangetype")
            var challangetype: String? = null

            @SerializedName("name")
            var name: String? = null

            @SerializedName("bussinesstype")
            var bussinesstype: String? = null

            @SerializedName("level")
            var level: String? = null

            @SerializedName("duration")
            var duration: String? = null

            @SerializedName("count")
            var count: String? = null

            @SerializedName("radioPin")
            var radioPin: String? = null

            @SerializedName("description")
            var description: String? = null

            @SerializedName("is_deleted")
            var is_deleted: String? = null

            @SerializedName("credit")
            var credit: String? = null

            @SerializedName("title")
            var title: String? = null

            @SerializedName("groupid")
            var groupid: String? = null

            @SerializedName("usertype")
            var usertype: String? = null

            @SerializedName("created_timestamp")
            var created_timestamp: String? = null

            @SerializedName("main_challange_type")
            var main_challange_type: String? = null

            @SerializedName("sub_challange_id")
            var sub_challange_id: String? = null

        }

    }
}


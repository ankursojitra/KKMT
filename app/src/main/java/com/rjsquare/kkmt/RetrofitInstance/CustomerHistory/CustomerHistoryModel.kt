package com.rjsquare.kkmt.RetrofitInstance.OTPCall

import com.google.gson.annotations.SerializedName

class CustomerHistoryModel {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: reviewData? = null

    class reviewData {
        @SerializedName("total_star_count")
        var total_star_count: String? = null

        @SerializedName("total_star_type_wise")
        var total_star_type_wise: saperateReview? = null

        class saperateReview {
            @SerializedName("one_star")
            var one_star: String? = null

            @SerializedName("Good")
            var Good: String? = null

            @SerializedName("Bad")
            var Bad: String? = null

            @SerializedName("five_star")
            var five_star: String? = null
        }

        @SerializedName("total_spend_amount")
        var total_spend_amount: String? = null

        @SerializedName("review_list")
        var review_itemInfo: ArrayList<reviewItemInfo>? = null

        class reviewItemInfo {
            @SerializedName("id")
            var id: String? = null

            @SerializedName("bussinessname")
            var bussinessname: String? = null

            @SerializedName("bussinessimage")
            var bussinessimage: String? = null

            @SerializedName("ratings")
            var ratings: String? = null

            @SerializedName("review_status")
            var review_status: String? = null

            @SerializedName("created_at")
            var created_at: String? = null

            @SerializedName("spend_amount")
            var spend_amount: String? = null

            @SerializedName("updated_at")
            var updated_at: String? = null

            @SerializedName("created_timestamp")
            var created_timestamp: String? = null

            @SerializedName("updated_timestamp")
            var updated_timestamp: String? = null

            @SerializedName("username")
            var username: String? = null

            @SerializedName("userimage")
            var userimage: String? = null

            @SerializedName("gain_credit_from_admin")
            var gain_credit_from_admin: String? = null

            @SerializedName("credit")
            var credit: credit_details? = null

            class credit_details {
                @SerializedName("check_in_credit")
                var check_in_credit: String? = null

                @SerializedName("employee_review_credit")
                var employee_review_credit: String? = null

                @SerializedName("proof_of_purchase")
                var proof_of_purchase: String? = null

                @SerializedName("additional_details")
                var additional_details: String? = null

                @SerializedName("bonus")
                var bonus: String? = null
            }
        }
    }
}


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

        @SerializedName("latest_review")
        var latest_review: ArrayList<ReviewInfodata>? = null

        @SerializedName("panding_review")
        var panding_review: ArrayList<ReviewInfodata>? = null

        @SerializedName("cancel_review")
        var cancel_review: ArrayList<ReviewInfodata>? = null

        @SerializedName("approve_review")
        var approve_review: ArrayList<ReviewInfodata>? = null

//        class reviewItemInfo {
//            @SerializedName("id")
//            var id: String? = null
//
//            @SerializedName("user_id")
//            var user_id: String? = null
//
//            @SerializedName("business_id")
//            var business_id: String? = null
//
//            @SerializedName("review_by")
//            var review_by: String? = null
//
//            @SerializedName("employee_id")
//            var employee_id: String? = null
//
//            @SerializedName("review")
//            var review: String? = null
//
//            @SerializedName("review_status")
//            var review_status: String? = null
//
//            @SerializedName("receipt")
//            var receipt: String? = null
//
//            @SerializedName("receipt_number")
//            var receipt_number: String? = null
//
//            @SerializedName("receipt_amount")
//            var receipt_amount: String? = null
//
//            @SerializedName("upload_recipt")
//            var upload_recipt: String? = null
//
//            @SerializedName("description")
//            var description: String? = null
//
//            @SerializedName("voice_note")
//            var voice_note: String? = null
//
//            @SerializedName("status")
//            var status: String? = null
//
//            @SerializedName("is_deleted")
//            var is_deleted: String? = null
//
//            @SerializedName("record_date")
//            var record_date: String? = null
//
//            @SerializedName("bussinessname")
//            var bussinessname: String? = null
//
//            @SerializedName("bussinessimage")
//            var bussinessimage: String? = null
//
//            @SerializedName("created_at")
//            var created_at: String? = null
//
//            @SerializedName("updated_at")
//            var updated_at: String? = null
//
//            @SerializedName("created_timestamp")
//            var created_timestamp: String? = null
//
//            @SerializedName("updated_timestamp")
//            var updated_timestamp: String? = null
//
//            @SerializedName("spend_amount")
//            var spend_amount: String? = null
//
//            @SerializedName("employee_name")
//            var employee_name: String? = null
//
//            @SerializedName("employeimage")
//            var employeimage: String? = null
//
//            @SerializedName("gain_credit_from_admin")
//            var gain_credit_from_admin: String? = null
//
//            @SerializedName("credit")
//            var credit: credit_details? = null
//
//            class credit_details {
//                @SerializedName("check_in_credit")
//                var check_in_credit: String? = null
//
//                @SerializedName("employee_review_credit")
//                var employee_review_credit: String? = null
//
//                @SerializedName("proof_of_purchase")
//                var proof_of_purchase: String? = null
//
//                @SerializedName("additional_details")
//                var additional_details: String? = null
//
//                @SerializedName("bonus")
//                var bonus: String? = null
//            }
//        }
    }
}


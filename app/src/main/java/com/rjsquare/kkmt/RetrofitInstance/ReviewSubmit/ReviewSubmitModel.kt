package com.rjsquare.kkmt.RetrofitInstance.OTPCall

import com.google.gson.annotations.SerializedName

class ReviewSubmitModel {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: ReviewInfodata? = null


}

class ReviewInfodata {

    @SerializedName("customer_name")
    var customer_name: String? = null

    @SerializedName("id")
    var id: String? = null

    @SerializedName("user_id")
    var user_id: String? = null

    @SerializedName("business_id")
    var business_id: String? = null

    @SerializedName("review_by")
    var review_by: String? = null

    @SerializedName("employee_id")
    var employee_id: String? = null

    @SerializedName("review")
    var review: String? = null

    @SerializedName("review_status")
    var review_status: String? = null

    @SerializedName("receipt")
    var receipt: String? = null

    @SerializedName("receipt_number")
    var receipt_number: String? = null

    @SerializedName("receipt_amount")
    var receipt_amount: String? = null

    @SerializedName("upload_recipt")
    var upload_recipt: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("voice_note")
    var voice_note: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("is_deleted")
    var is_deleted: String? = null

    @SerializedName("record_date")
    var record_date: String? = null

    @SerializedName("created_at")
    var created_at: String? = null

    @SerializedName("updated_at")
    var updated_at: String? = null

    @SerializedName("created_timestamp")
    var created_timestamp: String? = null

    @SerializedName("updated_timestamp")
    var updated_timestamp: String? = null

    @SerializedName("employeimage")
    var employeimage: String? = null

    @SerializedName("employee_name")
    var employee_name: String? = null

    @SerializedName("credit")
    var credit: CreditInfo? = null

    class CreditInfo {

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

        @SerializedName("receipt_status")
        var receipt_status: String? = null

        @SerializedName("description_status")
        var description_status: String? = null

        @SerializedName("voice_note_status")
        var voice_note_status: String? = null

        @SerializedName("check_in_status")
        var check_in_status: String? = null

    }

}

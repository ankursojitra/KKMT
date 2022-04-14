package com.rjsquare.kkmt.RetrofitInstance.OTPCall

import com.google.gson.annotations.SerializedName

class BusinessCheckInModel {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: BusinessCheckIn? = null

    class BusinessCheckIn {

        @SerializedName("total_credit")
        var total_credit: String? = null

        @SerializedName("check_in_credit")
        var check_in_credit: String? = null

    }
}


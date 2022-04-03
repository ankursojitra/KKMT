package com.rjsquare.kkmt.RetrofitInstance.Events

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class LuckyDrawCheck_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: LuckyDrawCheck? = null

    class LuckyDrawCheck {

        @SerializedName("is_eligible")
        var is_eligible: String? = null

        @SerializedName("last_spin")
        var last_spin: String? = null

    }
}

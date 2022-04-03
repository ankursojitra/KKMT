package com.rjsquare.kkmt.RetrofitInstance.Events

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class VideoQuestionComplete_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: VideoDetail? = null

    class VideoDetail {

    }
}

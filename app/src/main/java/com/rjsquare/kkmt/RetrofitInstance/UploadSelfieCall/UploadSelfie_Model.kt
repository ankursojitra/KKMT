package com.rjsquare.kkmt.RetrofitInstance.LogInCall

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class UploadSelfie_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: UserLoginData? = null

}


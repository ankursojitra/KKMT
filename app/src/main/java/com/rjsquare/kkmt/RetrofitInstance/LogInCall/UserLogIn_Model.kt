package com.rjsquare.kkmt.RetrofitInstance.LogInCall

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class UserLogIn_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: UserLoginData? = null
}

class UserLoginData {
    @SerializedName("userid")
    var userid: String = ""

    @SerializedName("usertype")
    var usertype: String = ""

    @SerializedName("access_token")
    var access_token: String? = null
}

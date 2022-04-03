package com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall

import com.google.gson.annotations.SerializedName
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.UserDataInfo
import java.io.Serializable


class UserInfoData_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: UserDataInfo? = null
}

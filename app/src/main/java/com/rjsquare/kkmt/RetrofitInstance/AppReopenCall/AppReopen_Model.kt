package com.rjsquare.kkmt.RetrofitInstance.LogInCall

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class AppReopen_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: userdata? = null

    @SerializedName("access_token")
    var access_token: String? = null


}

class userdata {

    @SerializedName("userid")
    var userid: String? = null

    @SerializedName("username")
    var username: String? = null

    @SerializedName("contactno")
    var contactno: String? = null

    @SerializedName("firstname")
    var firstname: String? = null

    @SerializedName("lastname")
    var lastname: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("dob")
    var dob: String? = null

    @SerializedName("usertype")
    var usertype: String? = null

    @SerializedName("userimage")
    var userimage: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("selfie")
    var selfie: String? = null

    @SerializedName("document")
    var document: String? = null

    @SerializedName("username_request")
    var username_request: String? = null

    @SerializedName("user_imgae_request")
    var user_imgae_request: String? = null

    @SerializedName("document_request")
    var document_request: String? = null

    @SerializedName("selfie_request")
    var selfie_request: String? = null

    @SerializedName("credit")
    var credit: String? = null

    @SerializedName("earning_credit")
    var earning_credit: String? = null

    @SerializedName("level")
    var level: String? = null
}


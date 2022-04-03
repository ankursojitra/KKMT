package com.rjsquare.kkmt.RetrofitInstance.OTPCall

import com.google.gson.annotations.SerializedName

class UserDataInfo {

    @SerializedName("userid")
    var userid: String? = null

    @SerializedName("username")
    var username: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("userimage")
    var userimage: String? = null

    @SerializedName("contactno")
    var contactno: String? = null

    @SerializedName("usertype")
    var usertype: String? = null

    @SerializedName("is_deleted")
    var is_deleted: String? = null

    @SerializedName("verify_email")
    var verify_email: String? = null

    @SerializedName("approve")
    var approve: String? = null

    @SerializedName("username_request")
    var username_request: String? = null

    @SerializedName("reason_username")
    var reason_username: String? = null

    @SerializedName("user_imgae_request")
    var user_imgae_request: String? = null

    @SerializedName("reason_userimage")
    var reason_userimage: String? = null

    @SerializedName("document_request")
    var document_request: String? = null

    @SerializedName("reason_document")
    var reason_document: String? = null

    @SerializedName("selfie")
    var selfie: String? = null

    @SerializedName("reason_selfie")
    var reason_selfie: String? = null

    @SerializedName("firstname")
    var firstname: String? = null

    @SerializedName("lastname")
    var lastname: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("credit")
    var credit: String? = null

    @SerializedName("bussiness")
    var bussiness: String? = null

    @SerializedName("beacon")
    var beacon: String? = null

    @SerializedName("kkmtid")
    var kkmtid: String? = null

    @SerializedName("employeeid")
    var employeeid: String? = null

    @SerializedName("earning_credit")
    var earning_credit: String? = null

    @SerializedName("dob")
    var dob: String? = null

    @SerializedName("worked")
    var worked: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("landmark")
    var landmark: String? = null

    @SerializedName("city")
    var city: String? = null

    @SerializedName("region")
    var region: String? = null

    @SerializedName("latitude")
    var latitude: String? = null

    @SerializedName("longitude")
    var longitude: String? = null

    @SerializedName("national_id")
    var national_id: String? = null

    @SerializedName("document")
    var document: String? = null

    @SerializedName("created_at")
    var created_at: String? = null

    @SerializedName("updated_at")
    var updated_at: String? = null

    @SerializedName("user_image_request")
    var user_image_request: String? = null

    @SerializedName("selfie_request")
    var selfie_request: String? = null

    @SerializedName("credit_details")
    var credit_details: credit_details? = null

    @SerializedName("access_token")
    var access_token: String? = null
}

class credit_details {

    @SerializedName("credit")
    var credit: String? = null

    @SerializedName("earning_credit")
    var earning_credit: String? = null

    @SerializedName("level")
    var level: String? = null

}

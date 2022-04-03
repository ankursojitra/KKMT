package com.rjsquare.kkmt.RetrofitInstance.Leaderboard

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class LeaderboardEmployee_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: EmpUserData? = null

}

class EmpUserData {

    @SerializedName("current_business")
    var current_business: CurrentBusiness? = null

    @SerializedName("overall_business")
    var overall_business: overallBusiness? = null

    class CurrentBusiness {
        @SerializedName("current_employee")
        var current_employee: ArrayList<EmpInfo>? = null

        @SerializedName("current_business_top_20")
        var current_business_top_20: ArrayList<EmpInfo>? = null
    }

    class overallBusiness {
        @SerializedName("overall_business_top_20")
        var overall_business_top_20: ArrayList<EmpInfo>? = null

        @SerializedName("current_employee")
        var current_employee: ArrayList<EmpInfo>? = null
    }
}

class EmpInfo {
    @SerializedName("id")
    var id: String = ""

    @SerializedName("username")
    var username: String = ""

    @SerializedName("email")
    var email: String = ""

    @SerializedName("password")
    var password: String = ""

    @SerializedName("userimage")
    var userimage: String = ""

    @SerializedName("contactno")
    var contactno: String = ""

    @SerializedName("usertype")
    var usertype: String = ""

    @SerializedName("is_deleted")
    var is_deleted: String = ""

    @SerializedName("verify_email")
    var verify_email: String = ""

    @SerializedName("approve")
    var approve: String = ""

    @SerializedName("username_request")
    var username_request: String = ""

    @SerializedName("reason_username")
    var reason_username: String = ""

    @SerializedName("user_imgae_request")
    var user_imgae_request: String = ""

    @SerializedName("reason_userimage")
    var reason_userimage: String = ""

    @SerializedName("document_request")
    var document_request: String = ""

    @SerializedName("reason_document")
    var reason_document: String = ""

    @SerializedName("selfie")
    var selfie: String = ""

    @SerializedName("reason_selfie")
    var reason_selfie: String = ""

    @SerializedName("created_at")
    var created_at: String = ""

    @SerializedName("updated_at")
    var updated_at: String = ""

    @SerializedName("firstname")
    var firstname: String = ""

    @SerializedName("lastname")
    var lastname: String = ""

    @SerializedName("gender")
    var gender: String = ""

    @SerializedName("bussiness")
    var bussiness: String = ""

    @SerializedName("beacon")
    var beacon: String = ""

    @SerializedName("kkmtid")
    var kkmtid: String = ""

    @SerializedName("employeeid")
    var employeeid: String = ""

    @SerializedName("credit")
    var credit: String = ""

    @SerializedName("dob")
    var dob: String = ""

    @SerializedName("worked")
    var worked: String = ""

    @SerializedName("address")
    var address: String = ""

    @SerializedName("landmark")
    var landmark: String = ""

    @SerializedName("city")
    var city: String = ""

    @SerializedName("region")
    var region: String = ""

    @SerializedName("latitude")
    var latitude: String = ""

    @SerializedName("longitude")
    var longitude: String = ""

    @SerializedName("national_id")
    var national_id: String = ""

    @SerializedName("document")
    var document: String = ""

    @SerializedName("rank")
    var rank: String = ""
}
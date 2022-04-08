package com.rjsquare.kkmt.RetrofitInstance.OTPCall

import com.google.gson.annotations.SerializedName

class SlaveBeaconModel {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: ArrayList<SlaveBescon>? = null

    class SlaveBescon {
        @SerializedName("macid")
        var macid: String? = null

        @SerializedName("beacontype")
        var beacontype: String? = null

        @SerializedName("bussinesname")
        var bussinesname: String? = null

        @SerializedName("ownername")
        var ownername: String? = null

        @SerializedName("description")
        var description: String? = null

        @SerializedName("address")
        var address: String? = null

        @SerializedName("latitude")
        var latitude: String? = null

        @SerializedName("longitude")
        var longitude: String? = null

        @SerializedName("landmark")
        var landmark: String? = null

        @SerializedName("weekstart")
        var weekstart: String? = null

        @SerializedName("weekend")
        var weekend: String? = null

        @SerializedName("signdate")
        var signdate: String? = null

        @SerializedName("mappin")
        var mappin: String? = null

        @SerializedName("bussinesid")
        var bussinesid: String? = null

        @SerializedName("kkmtid")
        var kkmtid: String? = null

        @SerializedName("is_active")
        var is_active: String? = null

        @SerializedName("username")
        var username: String? = null

        @SerializedName("firstname")
        var firstname: String? = null

        @SerializedName("employeeid_db")
        var employeeid_db: String? = null

        @SerializedName("lastname")
        var lastname: String? = null

        @SerializedName("gender")
        var gender: String? = null

        @SerializedName("credit")
        var credit: String? = null

        @SerializedName("earning_credit")
        var earning_credit: String? = null

        @SerializedName("dob")
        var dob: String? = null

        @SerializedName("worked")
        var worked: String? = null

        @SerializedName("region")
        var region: String? = null

        @SerializedName("city")
        var city: String? = null

        @SerializedName("bussinesid_db")
        var bussinesid_db: String? = null

    }
}


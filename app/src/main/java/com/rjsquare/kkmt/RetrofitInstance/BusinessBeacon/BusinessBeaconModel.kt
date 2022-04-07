package com.rjsquare.kkmt.RetrofitInstance.OTPCall

import com.google.gson.annotations.SerializedName

class BusinessBeaconModel {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: ArrayList<BusinessBescon>? = null

    class BusinessBescon {
        @SerializedName("macid")
        var macid: String? = null

        @SerializedName("beacontype")
        var beacontype: String? = null

        @SerializedName("address")
        var address: String? = null

        @SerializedName("landmark")
        var landmark: String? = null

        @SerializedName("region")
        var region: String? = null

        @SerializedName("city")
        var city: String? = null

        @SerializedName("latitude")
        var latitude: String? = null

        @SerializedName("longitude")
        var longitude: String? = null

        @SerializedName("weekstart")
        var weekstart: String? = null

        @SerializedName("weekend")
        var weekend: String? = null

        @SerializedName("mappin")
        var mappin: String? = null

        @SerializedName("signdate")
        var signdate: String? = null

        @SerializedName("bussinesid")
        var bussinesid: String? = null

        @SerializedName("kkmtid")
        var kkmtid: String? = null

        @SerializedName("businessstatus")
        var businessstatus: String? = null

        @SerializedName("beacon_status")
        var beacon_status: String? = null

        @SerializedName("businesstype")
        var businesstype: String? = null

        @SerializedName("businesstreadetype")
        var businesstreadetype: String? = null

        @SerializedName("ownername")
        var ownername: String? = null

        @SerializedName("description")
        var description: String? = null

        @SerializedName("bussiness_name")
        var bussiness_name: String? = null

        @SerializedName("businessid_db")
        var businessid_db: String? = null

        @SerializedName("check_in")
        var check_in: String? = null

        @SerializedName("updated_timestamp")
        var updated_timestamp: String? = null

        @SerializedName("created_timestamp")
        var created_timestamp: String? = null

    }
}


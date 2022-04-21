package com.rjsquare.kkmt.RetrofitInstance.PickUpLocation

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ItemRedeem_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: StoreItemRedeem? = null

    class StoreItemRedeem
}

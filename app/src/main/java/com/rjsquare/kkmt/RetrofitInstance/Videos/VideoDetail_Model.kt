package com.rjsquare.kkmt.RetrofitInstance.Events

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class VideoDetail_Model : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: VideoDetail? = null

    class VideoDetail {
        @SerializedName("id")
        var id: String? = null

        @SerializedName("title")
        var title: String? = null

        @SerializedName("features")
        var features: String? = null

        @SerializedName("usertype")
        var usertype: String? = null

        @SerializedName("tag")
        var tag: String? = null

        @SerializedName("credit")
        var credit: String? = null

        @SerializedName("video")
        var video: String? = null

        @SerializedName("description")
        var description: String? = null

        @SerializedName("type")
        var type: String? = null

        @SerializedName("is_deleted")
        var is_deleted: String? = null

        @SerializedName("created_at")
        var created_at: String? = null

        @SerializedName("updated_at")
        var updated_at: String? = null

        @SerializedName("question")
        var question: ArrayList<QuestionData>? = null

        class QuestionData {

            @SerializedName("id")
            var id: String? = null

            @SerializedName("videoid")
            var videoid: String? = null

            @SerializedName("question")
            var question: String? = null

            @SerializedName("answer1")
            var answer1: String? = null

            @SerializedName("answer2")
            var answer2: String? = null

            @SerializedName("answer3")
            var answer3: String? = null

            @SerializedName("answer4")
            var answer4: String? = null

            @SerializedName("answer")
            var answer: String? = null

            @SerializedName("created_at")
            var created_at: String? = null

            @SerializedName("updated_at")
            var updated_at: String? = null

            @SerializedName("created_timestamp")
            var created_timestamp: String? = null

            @SerializedName("updated_timestamp")
            var updated_timestamp: String? = null

        }

    }
}

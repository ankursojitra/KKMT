package com.rjsquare.kkmt.RetrofitInstance.OTPCall

import com.google.gson.annotations.SerializedName

class EmployeeHistoryModel {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("data")
    var data: EmployeeReviewData? = null

    class EmployeeReviewData {
        @SerializedName("overall")
        var overall: ReviewsInfo? = null

        @SerializedName("last_week")
        var last_week: ReviewsInfo? = null

        @SerializedName("last_month")
        var last_month: ReviewsInfo? = null

        class ReviewsInfo {
            @SerializedName("total_sales")
            var total_sales: String? = null

            @SerializedName("total_star_count")
            var total_star_count: String? = null

            @SerializedName("total_star_type_wise")
            var total_star_type_wise: RatingInfo? = null

            class RatingInfo {
                @SerializedName("one_star")
                var one_star: String? = null

                @SerializedName("Good")
                var Good: String? = null

                @SerializedName("Bad")
                var Bad: String? = null

                @SerializedName("five_star")
                var five_star: String? = null
            }
        }
    }
}


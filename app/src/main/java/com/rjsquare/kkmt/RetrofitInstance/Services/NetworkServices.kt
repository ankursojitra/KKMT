package com.rjsquare.kkmt.RetrofitInstance.Events


import com.rjsquare.kkmt.RetrofitInstance.Leaderboard.LeaderboardCustomer_Model
import com.rjsquare.kkmt.RetrofitInstance.Leaderboard.LeaderboardEmployee_Model
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.UploadDoc_Model
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.UserLogIn_Model
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.*
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.ItemRedeem_Model
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.PickUpLocation_Model
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.PrizeList_Model
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.StoreList_Model
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import retrofit2.Call
import retrofit2.http.*

object NetworkServices {
//    companion object {

    //Register User Service
    interface RegisterUserService {
        @POST("api-register")
        @FormUrlEncoded
        fun GetRegisterUserData(@FieldMap params: Map<String, String>): Call<UserInfoData_Model>
    }

    //LogIn Service
    interface LogInCallService {
        @POST("api-login")
        @FormUrlEncoded
        fun GetLoginData(@FieldMap params: Map<String, String>): Call<UserLogIn_Model>
    }

    //LuckyDraw Data get Check Service
    interface LuckyDrawcreditService {
        @POST("api-lucky-spin-credit-list")
        @FormUrlEncoded
        fun GetLuckyDrawCheckData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<LuckyDrawCheck_Model>
    }

    //LuckyDraw Check Service
    interface LuckyDrawCheckService {
        @POST("api-lucky-draw-check")
        @FormUrlEncoded
        fun GetLuckyDrawCheckData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<LuckyDrawCheck_Model>
    }

    //LuckyDraw Service
    interface LuckyDrawService {
        @POST("api-lucky-draw")
        @FormUrlEncoded
        fun GetLuckyDrawData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<LuckyDraw_Model>
    }

    //MasterBeacon Service
    interface MasterBeaconService {
        @FormUrlEncoded
        @POST("api-get-bussiness-beacon")
        fun GetBusinessBeaconData(
            @FieldMap params: Map<String, String>,
            @Field("becon_list[]") list: ArrayList<String>,
            @Header("token") token: String
        ): Call<MasterBeaconModel>

    }

    //OTPCall Service
    interface OTPCallService {
        @POST("api-check-otp")
        @FormUrlEncoded
        fun GetOTPData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<UserInfoData_Model>
    }

    //AppReopen Service
    interface AppReopenService {
        @POST("api-add-app-histroy")
        @FormUrlEncoded
        fun GetUserData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<UserInfoData_Model>

    }

    //Business CheckIn Service
    interface BusinessCheckInService {
        @FormUrlEncoded
        @POST("api-business-check-in")
        fun GetBusinessCheckInData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<BusinessCheckInModel>

    }

    //Business Not found Service
    interface BusinessNotFoundService {
        @FormUrlEncoded
        @POST("api-business-not-found")
        fun GetBusinessNotFoundData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<BusinessNotFoundModel>

    }

    //Customer History Service
    interface CustomerHistoryService {
        @POST("api-customer-history")
        @FormUrlEncoded
        fun GetCustomerHistoryData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<CustomerHistoryModel>

    }

    //Employee History Service
    interface EmployeeHistoryService {
        @POST("api-employee-history")
        @FormUrlEncoded
        fun GetEmployeeHistoryData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<EmployeeHistoryModel>

    }

    //Employee NotFound Service
    interface EmployeeNotFoundService {
        @FormUrlEncoded
        @POST("api-employee-not-found")
        fun GetEmployeeNotFoundData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<EmployeeNotFoundModel>

    }

    //Events Service
    interface EventsService {
        @POST("api-get-event-list")
        @FormUrlEncoded
        fun GetEventsData(@FieldMap params: Map<String, String>): Call<Events_Model>
    }

    //Events Service
    interface EventsByMonthService {
        @POST("api-get-event-list-by-month")
        @FormUrlEncoded
        fun GetEventsByMonthData(@FieldMap params: Map<String, String>): Call<Events_Model>
    }

    //Leaderboard Customer Service
    interface LeaderboardCustomerService {
        @POST("api-customer-leaderboard")
        @FormUrlEncoded
        fun GetLeaderBoardData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<LeaderboardCustomer_Model>
    }

    //Leaderboard Employee Service
    interface LeaderboardEmployeeService {
        @POST("api-employee-leaderboard")
        @FormUrlEncoded
        fun GetLeaderBoardEmpData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<LeaderboardEmployee_Model>
    }

    //VideoList Service
    interface VideosService {
        @POST("api-video-list")
        @FormUrlEncoded
        fun GetVideosData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<Videos_Model>
    }

    //Video Details Service
    interface VideosDetailService {
        @POST("api-video-details")
        @FormUrlEncoded
        fun GetVideosDetail(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<VideoDetail_Model>
    }

    //Video Questions Service
    interface VideoQuestionCompleteService {
        @POST("api-complete-questionery")
        @FormUrlEncoded
        fun GetVideoQuestionComplete(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<VideoQuestionComplete_Model>
    }

    //PickUp Location Service
    interface PickUpLocationService {
        @GET("api-peckup-location")
        fun GetPickUpLocationData(): Call<PickUpLocation_Model>
    }

    //ReviewDetail Service
    interface ReviewDetailService {
        @POST("api-review-details")
        @FormUrlEncoded
        fun GetReviewDetailData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<ReviewDetailModel>

    }

    //SlaveBeacon Service
    interface SlaveBeaconService {
        @FormUrlEncoded
        @POST("api-get-employee-beacon-details")
        fun GetSlaveBeaconData(
            @FieldMap params: Map<String, String>,
            @Field("becon_list[]") list: ArrayList<String>,
            @Header("token") token: String
        ): Call<SlaveBeaconModel>

    }

    //UploadDoc Service
    interface UploadDocService {
        @POST("api-upload-document")
        @FormUrlEncoded
        fun UploadDoc(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<UploadDoc_Model>

    }

    //UploadSelfie Service
    interface UploadSelfieService {
        @POST("api-selfie-document")
        @FormUrlEncoded
        fun UploadSelfieDoc(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<UploadDoc_Model>

    }

    //Prize List Service
    interface PriceListService {
        @POST("api-price-list")
        @FormUrlEncoded
        fun PriceListData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<PrizeList_Model>

    }

    //Store List Service
    interface StoreListService {
        @POST("api-store")
        @FormUrlEncoded
        fun StoreListData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<StoreList_Model>

    }

    //Item Redeem Service
    interface ItemRedeemService {
        @POST("api-item-redeem")
        @FormUrlEncoded
        fun ItemRedeemData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<ItemRedeem_Model>

    }

    //Item Redeem Service
    interface EmployeeReportService {
        @POST("api-employee-report")
        @FormUrlEncoded
        fun EmployeeReportData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<ReviewSubmitModel>

    }

    //Item Redeem Service
    interface EditProfileService {
        @POST("api-edit-profile")
        @FormUrlEncoded
        fun EditProfileData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<UserInfoData_Model>

    }

    //Item Redeem Service
    interface DisplayNameService {
        @POST("api-display-name-hide-show")
        @FormUrlEncoded
        fun DisplayNameData(
            @FieldMap params: Map<String, String>,
            @Header("token") token: String
        ): Call<DisplayNameModel>

    }


//    }
}
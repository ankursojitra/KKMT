package com.rjsquare.kkmt.AppConstant

import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Login.Login
import com.rjsquare.kkmt.Helpers.Preferences
import com.rjsquare.kkmt.Model.*
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.Events_Model
import com.rjsquare.kkmt.RetrofitInstance.Events.Videos_Model
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.AppReopenService
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.UserLogIn_Model
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.CustomerHistoryModel
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import retrofit2.Call
import retrofit2.Callback
import java.util.regex.Pattern


class ApplicationClass : Application(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        //App in background
        APP_BACKGROUND = true
//        Log.e("Applicationxx", " On Background")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        // App in foreground
        APP_BACKGROUND = false
//        Log.e("Applicationxx", " On Foreground")
    }


    companion object {
        lateinit var ApplicationContext: Application
        var IsRegisterFlow = false
        var HomeScreenLoad = false
        var UserSpineTime = 0L
        lateinit var mReviewEmp_Model: ReviewEmp_Model
        lateinit var Selected_ReviewEmp_Model: ReviewEmp_Model
        lateinit var ArrayList_mReviewEmp_Model: ArrayList<ReviewEmp_Model>
        lateinit var mUserLoginInfo_Model: UserLoginInfo_Model
        lateinit var mLogInInfo_Model: UserLogIn_Model
        lateinit var userInfoModel: UserInfoData_Model
        var AutorisedUser = true

        var BASE_URL: String = "https://kkmtapp.com/developing/api/"
        var BaseUrl = "https://kkmtapp.com/developing/api/"
        val Email_Pattern: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        val Password_Pattern: Pattern =
            Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%]).{6,20})")
        val FirstName__Pattern: Pattern =
            Pattern.compile("(.[^A-Za-z ].)")
        val MobileNo__Pattern: Pattern =
            Pattern.compile(" ^[0-9+]{0,1}+[0-9]{5,16}\$")

        var mBiconReviewImageX: Bitmap? = null
        lateinit var mReviewModel: ReviewModel
        lateinit var mArray_ReviewModel: ArrayList<CustomerHistoryModel.reviewData.reviewItemInfo>
        lateinit var mList_StoreListModel: ArrayList<StoreListModel>
        lateinit var mList_StoreItemDetailModel: ArrayList<StoreItemDetailModel>
        var mStoreLevelListModelSelected: StoreListModel? = null
        var IsUserEmployee: Boolean = false
        var APP_BACKGROUND: Boolean = false
        var UserLogedIn: Boolean = false
        var IsNewReview = true
        var empReviewModelSelected: CustomerHistoryModel.reviewData.reviewItemInfo? = null
        var mSearchModelSelected: SearchModel? = null
        var mEventsModelSelected: Events_Model.EventsData? = null
        var mVideoesModelSelected: Videos_Model.VideoData? = null

        lateinit var SharedPref: SharedPreferences
        lateinit var PrefEditor: SharedPreferences.Editor

        //-----------------------Constant ids-------------------------------------

        val Pending = "panding"
        val Approve = "approve"
        val paramKey_MobileNo = "phoneno"
        val paramKey_FirstName = "firstname"
        val paramKey_LastName = "lastname"
        val paramKey_EmailAddress = "email"
        val paramKey_DOB = "dob"
        val paramKey_Gender = "gender"
        val paramKey_Userid = "userid"
        val paramKey_OPT = "otp"
        val paramKey_CustomerId = "customer_id"
        val paramKey_BussinessId = "business_id"
        val paramKey_EmployeeId = "employee_id"
        val paramKey_Document = "document"
        val paramKey_Selfie = "selfie"
        val paramKey_PageNo = "pageno"
        val paramKey_ReviewId = "review_id"
        val paramKey_UserId = "user_id"
        val paramKey_VideoId = "video_id"
        val paramKey_Credit = "credit"
        val paramKey_Usertype = "usertype"
        val paramKey_limit = "limit"
        val paramKey_BeconList = "becon_list[]"
        val User = "U"

        //User Preference data Store Key
        val Pref_UserDataModel: String = "userDataModel"
        val Pref_UserLogedIn: String = "user_logedin"

        //Api Status Code
        var ResponseSucess = "200"
        var ResponseUnauthorized = "203"
        var ResponseEmpltyList = "202"

        var UserLevel = 4

        var PrefName = "KKMTDATA"
        var SpinTime = "SpinTime"

        //-----------------------------END---------------------------------------

        fun Gender(gender: String): String {
            if (gender.equals("M", true)) {
                return "Male"
            } else if (gender.equals("F", true)) {
                return "Female"
            } else if (gender.equals("O", true)) {
                return "Other"
            } else {
                return ""
            }
        }

        fun GenderParam(gender: String): String {
            if (gender.equals("Male", true)) {
                return "M"
            } else if (gender.equals("Female", true)) {
                return "F"
            } else if (gender.equals("Other", true)) {
                return "O"
            } else {
                return ""
            }
        }

        private fun GetUserPrefData(): UserInfoData_Model {
            var mUserInfoData_Model = UserInfoData_Model()
            if (!SharedPref.getString(Pref_UserDataModel, "").equals("", true)) {
                mUserInfoData_Model = Gson().fromJson(
                    SharedPref.getString(Pref_UserDataModel, ""),
                    UserInfoData_Model::class.java
                )
                Log.e("TAG", "ModelCHECK")
            }
            return mUserInfoData_Model
        }

        fun ResetAllData() {
            mLogInInfo_Model = UserLogIn_Model()
            mUserLoginInfo_Model = UserLoginInfo_Model()
            mReviewEmp_Model = ReviewEmp_Model()
            Selected_ReviewEmp_Model = ReviewEmp_Model()
            ArrayList_mReviewEmp_Model = ArrayList()
            userInfoModel = UserInfoData_Model()
            UserLogedIn = false
        }

        fun UserLogout(activity:Activity) {
            Preferences.Cleardata()
            ResetAllData()
            var LogInIntent = Intent(activity, Login::class.java)
            activity.startActivity(LogInIntent)
            activity.finish()
            activity.overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
        }

        fun IsEmployee(): Boolean {
            return !userInfoModel.data!!.usertype.equals(User, true)
        }

        fun StatusTextWhite(activity: Activity, StatusWhite: Boolean) {
            if (StatusWhite) {
                TransparentStatusbar(activity)
            } else {
                activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }


        fun TransparentStatusbar(activity: Activity) {
            try {
                //make translucent statusBar on kitkat devices
                if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
                    setWindowFlag(
                        activity,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        true
                    )
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    activity.window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                //make fully Android Transparent Status bar
                if (Build.VERSION.SDK_INT >= 21) {
                    setWindowFlag(
                        activity,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        false
                    )
                    activity.window.statusBarColor = Color.TRANSPARENT
                }
            } catch (NE: NullPointerException) {
                NE.printStackTrace()
            } catch (IE: IndexOutOfBoundsException) {
                IE.printStackTrace()
            } catch (AE: ActivityNotFoundException) {
                AE.printStackTrace()
            } catch (E: IllegalArgumentException) {
                E.printStackTrace()
            } catch (RE: RuntimeException) {
                RE.printStackTrace()
            } catch (E: Exception) {
                E.printStackTrace()
            }
        }

        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            try {
                val win = activity.window
                val winParams = win.attributes
                if (on) {
                    winParams.flags = winParams.flags or bits
                } else {
                    winParams.flags = winParams.flags and bits.inv()
                }
                win.attributes = winParams
            } catch (NE: NullPointerException) {
                NE.printStackTrace()
            } catch (IE: IndexOutOfBoundsException) {
                IE.printStackTrace()
            } catch (AE: ActivityNotFoundException) {
                AE.printStackTrace()
            } catch (E: IllegalArgumentException) {
                E.printStackTrace()
            } catch (RE: RuntimeException) {
                RE.printStackTrace()
            } catch (E: Exception) {
                E.printStackTrace()
            }
        }

        fun hideKeyboard(activity: Activity) {
            try {
                val imm: InputMethodManager =
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                //Find the currently focused view, so we can grab the correct window token from it.
                var view = activity.currentFocus
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = View(activity)
                }
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (NE: NullPointerException) {
                NE.printStackTrace()
            } catch (IE: IndexOutOfBoundsException) {
                IE.printStackTrace()
            } catch (AE: ActivityNotFoundException) {
                AE.printStackTrace()
            } catch (E: IllegalArgumentException) {
                E.printStackTrace()
            } catch (RE: RuntimeException) {
                RE.printStackTrace()
            } catch (E: Exception) {
                E.printStackTrace()
            }
        }

        fun HiddenKeyBoard(activity: Activity, mView: View) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mView.windowToken, 0)
        }

        fun SetLayoutWidthHeight(view: View, width: Int, height: Int) {
            try {
                val Universal_Params = view.layoutParams
                Universal_Params.width = width
                Universal_Params.height = height
                view.layoutParams = Universal_Params
            } catch (NE: NullPointerException) {
                NE.printStackTrace()
            } catch (IE: IndexOutOfBoundsException) {
                IE.printStackTrace()
            } catch (AE: ActivityNotFoundException) {
                AE.printStackTrace()
            } catch (E: IllegalArgumentException) {
                E.printStackTrace()
            } catch (RE: RuntimeException) {
                RE.printStackTrace()
            } catch (E: Exception) {
                E.printStackTrace()
            }
        }

        fun SetLayoutWidth(view: View, width: Int) {
            try {
                val Universal_Params = view.layoutParams
                Universal_Params.width = width
                view.layoutParams = Universal_Params
            } catch (NE: NullPointerException) {
                NE.printStackTrace()
            } catch (IE: IndexOutOfBoundsException) {
                IE.printStackTrace()
            } catch (AE: ActivityNotFoundException) {
                AE.printStackTrace()
            } catch (E: IllegalArgumentException) {
                E.printStackTrace()
            } catch (RE: RuntimeException) {
                RE.printStackTrace()
            } catch (E: Exception) {
                E.printStackTrace()
            }
        }

        fun SetLayoutHeight(view: View, height: Int) {
            try {
                val Universal_Params = view.layoutParams
                Universal_Params.height = height
                view.layoutParams = Universal_Params
            } catch (NE: NullPointerException) {
                NE.printStackTrace()
            } catch (IE: IndexOutOfBoundsException) {
                IE.printStackTrace()
            } catch (AE: ActivityNotFoundException) {
                AE.printStackTrace()
            } catch (E: IllegalArgumentException) {
                E.printStackTrace()
            } catch (RE: RuntimeException) {
                RE.printStackTrace()
            } catch (E: Exception) {
                E.printStackTrace()
            }
        }

        fun updateUserInfo() {
            try {
                Log.e("TAG", "MSG : " + userInfoModel.data!!.userid.toString())
                Log.e("TAG", "TKN : " + userInfoModel.data?.access_token.toString())
                //Here the json data is add to a hash map with key data
                val params: MutableMap<String, String> =
                    HashMap()

                params[paramKey_UserId] =
                    userInfoModel.data!!.userid.toString()

                val service =
                    ApiCallingInstance.retrofitInstance.create<AppReopenService>(
                        AppReopenService::class.java
                    )
                val call = service.GetUserData(
                    params,
                    userInfoModel.data?.access_token.toString()
                )
                call.enqueue(object : Callback<UserInfoData_Model> {
                    override fun onFailure(call: Call<UserInfoData_Model>, t: Throwable) {
                        Log.e("GetResponseXX", ": " + t)
                    }

                    override fun onResponse(
                        call: Call<UserInfoData_Model>,
                        response: retrofit2.Response<UserInfoData_Model>
                    ) {
//                        Log.e("GetResponsesas", ": " + Gson().toJson(response.body()!!))

                        if (response.body()!!.status.equals(ResponseSucess, true)) {

                            userInfoModel = response.body()!!
                            Preferences.StoreString(
                                Pref_UserDataModel,
                                Gson().toJson(userInfoModel)
                            )

                            IsUserEmployee = IsEmployee()
                            AutorisedUser = true
                        } else if (response.body()!!.status.equals(ResponseUnauthorized, true)) {
                            AutorisedUser = false
                        } else {
                            ShowToast(ApplicationContext, response.body()!!.message)
                        }

                    }

                })
            } catch (E: Exception) {
                print(E)
            } catch (NE: NullPointerException) {
                print(NE)
            } catch (IE: IndexOutOfBoundsException) {
                print(IE)
            } catch (IE: IllegalStateException) {
                print(IE)
            } catch (AE: ActivityNotFoundException) {
                print(AE)
            } catch (KNE: KotlinNullPointerException) {
                print(KNE)
            } catch (CE: ClassNotFoundException) {
                print(CE)
            }
        }

        fun ShowToast(applicationContext: Application, message: String?) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }

        fun ShowToast(thisActivity: Activity, message: String?) {
            Toast.makeText(thisActivity, message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate() {
        super.onCreate()
        try {
            ApplicationContext = this

            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
            SharedPref = getSharedPreferences(PrefName, 0)
            PrefEditor = SharedPref.edit()
            UserLogedIn = Preferences.ReadBoolean(Pref_UserLogedIn, false)
            Log.e("TAG", "Login" + UserLogedIn)
            InitModels()


        } catch (NE: NullPointerException) {
            NE.printStackTrace()
        } catch (IE: IndexOutOfBoundsException) {
            IE.printStackTrace()
        } catch (AE: ActivityNotFoundException) {
            AE.printStackTrace()
        } catch (E: IllegalArgumentException) {
            E.printStackTrace()
        } catch (RE: RuntimeException) {
            RE.printStackTrace()
        } catch (E: Exception) {
            E.printStackTrace()
        }
    }

    private fun InitModels() {
        mLogInInfo_Model = UserLogIn_Model()
        mUserLoginInfo_Model = UserLoginInfo_Model()
        mReviewEmp_Model = ReviewEmp_Model()
        Selected_ReviewEmp_Model = ReviewEmp_Model()
        ArrayList_mReviewEmp_Model = ArrayList()
        userInfoModel = UserInfoData_Model()
        userInfoModel = GetUserPrefData()

//        if (userInfoModel != UserInfoData_Model()) {
//            IsUserEmployee = IsEmployee()
//        }

        if (UserLogedIn) {
//            updateUserInfo()
            IsUserEmployee = IsEmployee()
        }

    }
}
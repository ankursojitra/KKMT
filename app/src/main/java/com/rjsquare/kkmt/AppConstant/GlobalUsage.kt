package com.rjsquare.kkmt.AppConstant

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Activity.Login.Login
import com.rjsquare.kkmt.Helpers.Preferences
import com.rjsquare.kkmt.Model.SearchModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.EventsByMonth_Model
import com.rjsquare.kkmt.RetrofitInstance.Events.Events_Model
import com.rjsquare.kkmt.RetrofitInstance.Events.Videos_Model
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.UserLogIn_Model
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.CustomerHistoryModel
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.MasterBeaconModel
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.ReviewInfodata
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.SlaveBeaconModel
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.StoreList_Model
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import java.text.DecimalFormat
import java.util.regex.Pattern

object GlobalUsage {
    lateinit var mPendingReviewList: ArrayList<CustomerHistoryModel.reviewData.reviewItemInfo>
    lateinit var mApproveReviewList: ArrayList<CustomerHistoryModel.reviewData.reviewItemInfo>
    lateinit var mCancelReviewList: ArrayList<CustomerHistoryModel.reviewData.reviewItemInfo>
    lateinit var mList_StoreListModel: ArrayList<StoreList_Model.StoreItemData>
    var mStoreLevelListModelSelected: StoreList_Model.StoreItemData? = null
    var isUserEmployee: Boolean = false
    var appBackground: Boolean = false
    var userLogedIn: Boolean = false
    var isNewReview = true
    var empReviewModelSelected: CustomerHistoryModel.reviewData.reviewItemInfo? = null
    var mSearchModelSelected: SearchModel? = null
    var mEventsModelSelected: Events_Model.EventsData? = null
    var mEventsByMonthModelSelected: EventsByMonth_Model.EventsData.DateWiseEvents? = null
    var mVideoesModelSelected: Videos_Model.VideoData? = null
    var IsRegisterFlow = false
    var HomeScreenLoad = false

    var clickInterval = 500L
    lateinit var ReviewInfoModel: ReviewInfodata
    lateinit var mLogInInfo_Model: UserLogIn_Model
    lateinit var userInfoModel: UserInfoData_Model
    lateinit var selectedMasterModel: MasterBeaconModel.BusinessBescon
    lateinit var slaveModellist: ArrayList<SlaveBeaconModel.SlaveBescon>
    lateinit var empSlaveModel: SlaveBeaconModel.SlaveBescon
    var authorisedUser = true
    var isApprove = true
    var isHomeScreenVisible = false
    var lastClick = 0L

    lateinit var sharedPref: SharedPreferences
    lateinit var prefEditor: SharedPreferences.Editor

    val email_Pattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    val password_Pattern: Pattern =
        Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%]).{6,20})")
    val firstName__Pattern: Pattern =
        Pattern.compile("(.[^A-Za-z ].)")
    val mobileNo__Pattern: Pattern =
        Pattern.compile(" ^[0-9+]{0,1}+[0-9]{5,16}\$")

    var mBiconReviewImageX: Bitmap? = null


    //Functions
    fun NextScreen(activity: Activity, NextScreenIntent: Intent) {
        activity.startActivity(NextScreenIntent)
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

    fun NextScreenForResult(activity: Activity, code: Int, NextScreenIntent: Intent) {
        activity.startActivityForResult(NextScreenIntent, code)
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

    fun formatNumber(number: Int): String {
        val df = DecimalFormat("#,###,###")
        return df.format(number)
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

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

    fun ShowToast(applicationContext: Application, message: String?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun ShowToast(thisActivity: Activity, message: String?) {
        Toast.makeText(thisActivity, message, Toast.LENGTH_SHORT).show()
    }

    fun UserLogout(activity: Activity) {
        Preferences.Cleardata()
        ResetAllData()
        var LogInIntent = Intent(activity, HomeActivity::class.java)
        LogInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        activity.startActivity(LogInIntent)
        activity.finish()
//            activity.overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    fun ResetAllData() {
        mLogInInfo_Model = UserLogIn_Model()
        userInfoModel = UserInfoData_Model()
        userLogedIn = false
    }


    fun UserLogIn(activity: Activity) {
        var LogInIntent = Intent(activity, Login::class.java)
        activity.startActivity(LogInIntent)
//            activity.finish()
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

    fun IsEmployee(): Boolean {
        return !userInfoModel.data!!.usertype.equals(Constants.User, true)
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

    @SuppressLint("ResourceType")
    fun GetPin(activity: Activity, pinColors: String): Int {
        val pinColor = "#ff" + pinColors.replace("#", "")
        if (activity.resources.getString(R.color.gray_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_gray
        } else if (activity.resources.getString(R.color.orange_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_orange
        } else if (activity.resources.getString(R.color.green_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_green
        } else if (activity.resources.getString(R.color.pink_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_pink
        } else if (activity.resources.getString(R.color.blue_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_blue
        } else if (activity.resources.getString(R.color.red_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_red
        } else if (activity.resources.getString(R.color.yellow_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_yellow
        } else if (activity.resources.getString(R.color.purple_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_purple
        } else if (activity.resources.getString(R.color.black_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_black
        } else {
            return R.drawable.ic_pin_red
        }
    }

    @SuppressLint("ResourceType")
    fun GetPinView(activity: Activity, pinColors: String): Int {
        val pinColor = "#ff" + pinColors.replace("#", "")
        if (activity.resources.getString(R.color.gray_pin).equals(pinColor, true)) {
            return R.drawable.map_pin_back_gray
        } else if (activity.resources.getString(R.color.orange_pin).equals(pinColor, true)) {
            return R.drawable.map_pin_back_orange
        } else if (activity.resources.getString(R.color.green_pin).equals(pinColor, true)) {
            return R.drawable.map_pin_back_green
        } else if (activity.resources.getString(R.color.pink_pin).equals(pinColor, true)) {
            return R.drawable.map_pin_back_pink
        } else if (activity.resources.getString(R.color.blue_pin).equals(pinColor, true)) {
            return R.drawable.map_pin_back_blue
        } else if (activity.resources.getString(R.color.red_pin).equals(pinColor, true)) {
            return R.drawable.map_pin_back_red
        } else if (activity.resources.getString(R.color.yellow_pin).equals(pinColor, true)) {
            return R.drawable.map_pin_back_yellow
        } else if (activity.resources.getString(R.color.purple_pin).equals(pinColor, true)) {
            return R.drawable.map_pin_back_purple
        } else if (activity.resources.getString(R.color.black_pin).equals(pinColor, true)) {
            return R.drawable.map_pin_back_black
        } else {
            return R.drawable.map_pin_back_red
        }
    }


}
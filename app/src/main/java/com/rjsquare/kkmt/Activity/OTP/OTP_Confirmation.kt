package com.rjsquare.kkmt.Activity.OTP

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Activity.Login.Login
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.IsEmployee
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.ShowToast
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.UserLogout
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.isUserEmployee
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.userInfoModel
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.Helpers.Preferences
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import com.rjsquare.kkmt.databinding.ActivityOTPConfirmationBinding
import retrofit2.Call
import retrofit2.Callback


class OTP_Confirmation : AppCompatActivity(), View.OnClickListener {

    lateinit var sb: StringBuilder
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var OTPActivity: Activity
        lateinit var DB_OTPConfirmation: ActivityOTPConfirmationBinding
        var FinalOTPCode = ""
        fun GetOTP(): String {
            return DB_OTPConfirmation.otp1.text.toString() + DB_OTPConfirmation.otp2.text.toString() + DB_OTPConfirmation.otp3.text.toString() + DB_OTPConfirmation.otp4.text.toString()
        }

        fun GOTO_HomeScreen() {
            Preferences.StoreBoolean(Constants.Pref_UserLogedIn, true)
            var HomeIntent = Intent(OTPActivity, HomeActivity::class.java)
            OTPActivity.startActivity(HomeIntent)
            Login.LoginActivity.finish()
            OTPActivity.finish()
            OTPActivity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        }

        fun User_OTPConfirmation(FinalOTPCode: String, UserId: String) {
            try {
                LoaderVisible(true)
                //Here the json data is add to a hash map with key data
                val params: MutableMap<String, String> =
                    HashMap()
                params[Constants.paramKey_OPT] = FinalOTPCode
                params[Constants.paramKey_UserId] = UserId
                val service =
                    ApiCallingInstance.retrofitInstance.create<NetworkServices.OTPCallService>(
                        NetworkServices.OTPCallService::class.java
                    )
                val call = service.GetOTPData(
                    params,
                    ApplicationClass.mLogInInfo_Model.data?.access_token.toString()
                )
                call.enqueue(object : Callback<UserInfoData_Model> {
                    override fun onFailure(call: Call<UserInfoData_Model>, t: Throwable) {
                        Log.e("GetResponse", ": " + t)
                        ShowToast(OTPActivity, "Something went wrong")
                        DB_OTPConfirmation.cntLoader.visibility = View.GONE
                    }

                    override fun onResponse(
                        call: Call<UserInfoData_Model>,
                        response: retrofit2.Response<UserInfoData_Model>
                    ) {
                        if (response.body()!!.status.equals(
                                Constants.ResponseSucess, true
                            )
                        ) {
                            userInfoModel = UserInfoData_Model()
                            userInfoModel = response.body()!!
                            Preferences.StoreString(
                                Constants.Pref_UserDataModel,
                                Gson().toJson(userInfoModel)
                            )
                            isUserEmployee = IsEmployee()

                            GOTO_HomeScreen()
                        } else if (response.body()!!.status.equals(
                                Constants.ResponseUnauthorized, true
                            )
                        ) {
                            DB_OTPConfirmation.cntUnAuthorized.visibility = View.VISIBLE
                        } else {
                            DB_OTPConfirmation.txtOtpAlertmsg.text = response.body()!!.message
                            DB_OTPConfirmation.cntAlert.visibility = View.VISIBLE
                        }

                        LoaderVisible(false)

//                        if (userInfoModel.status.equals(Constants.ResponseSucess, true)) {
//
//                        } else {
//                            ShowToast(OTPActivity, userInfoModel.message)
//                        }
//                        DB_OTPConfirmation.cntLoader.visibility = View.GONE
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

        private fun LoaderVisible(loading: Boolean) {
            DB_OTPConfirmation.cntLoader.visibility = if (loading) View.VISIBLE else View.GONE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_OTPConfirmation =
            DataBindingUtil.setContentView(this, R.layout.activity_o_t_p__confirmation)

//        setContentView(R.layout.activity_o_t_p__confirmation)
        try {
            OTPActivity = this
            ApplicationClass.StatusTextWhite(this, true)
            this.window
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED)

            LoaderVisible(false)
//            DB_OTPConfirmation.cntLoader.visibility = View.GONE
            DB_OTPConfirmation.cntSent.visibility = View.GONE

            DB_OTPConfirmation.txtVerify.setOnClickListener(this)
            DB_OTPConfirmation.txtResend.setOnClickListener(this)
            DB_OTPConfirmation.cntLoader.setOnClickListener(this)
            DB_OTPConfirmation.cntSent.setOnClickListener(this)
            DB_OTPConfirmation.txtOtpAlertok.setOnClickListener(this)
            DB_OTPConfirmation.txtUnauthOk.setOnClickListener(this)

            sb = StringBuilder()
            DB_OTPConfirmation.otp1.addTextChangedListener(
                GenericTextWatcher(
                    DB_OTPConfirmation.otp1,
                    DB_OTPConfirmation.otp2
                )
            )
            DB_OTPConfirmation.otp2.addTextChangedListener(
                GenericTextWatcher(
                    DB_OTPConfirmation.otp2,
                    DB_OTPConfirmation.otp3
                )
            )
            DB_OTPConfirmation.otp3.addTextChangedListener(
                GenericTextWatcher(
                    DB_OTPConfirmation.otp3,
                    DB_OTPConfirmation.otp4
                )
            )
            DB_OTPConfirmation.otp4.addTextChangedListener(
                GenericTextWatcher(
                    DB_OTPConfirmation.otp4,
                    DB_OTPConfirmation.textView
                )
            )

            DB_OTPConfirmation.otp1.setOnKeyListener(GenericKeyEvent(DB_OTPConfirmation.otp1, null))
            DB_OTPConfirmation.otp2.setOnKeyListener(
                GenericKeyEvent(
                    DB_OTPConfirmation.otp2,
                    DB_OTPConfirmation.otp1
                )
            )
            DB_OTPConfirmation.otp3.setOnKeyListener(
                GenericKeyEvent(
                    DB_OTPConfirmation.otp3,
                    DB_OTPConfirmation.otp2
                )
            )
            DB_OTPConfirmation.otp4.setOnKeyListener(
                GenericKeyEvent(
                    DB_OTPConfirmation.otp4,
                    DB_OTPConfirmation.otp3
                )
            )

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

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < ApplicationClass.lastClick) return else {
                ApplicationClass.lastClick =
                    System.currentTimeMillis() + ApplicationClass.clickInterval
                if (view == DB_OTPConfirmation.txtVerify) {
                    FinalOTPCode = GetOTP()
                    User_OTPConfirmation(
                        FinalOTPCode,
                        ApplicationClass.mLogInInfo_Model.data!!.userid
                    )
                } else if (view == DB_OTPConfirmation.txtOtpAlertok) {
                    DB_OTPConfirmation.cntAlert.visibility = View.GONE
                } else if (view == DB_OTPConfirmation.txtUnauthOk) {
                    DB_OTPConfirmation.cntUnAuthorized.visibility = View.GONE
                    UserLogout(this)
                } else if (view == DB_OTPConfirmation.txtResend) {
                    LoaderVisible(true)
//                DB_OTPConfirmation.cntLoader.visibility = View.VISIBLE
                    val handler = Handler()
                    val runnablex = Runnable {
                        LoaderVisible(false)
//                    DB_OTPConfirmation.cntLoader.visibility = View.GONE
                        DB_OTPConfirmation.cntSent.visibility = View.VISIBLE
                        val handler = Handler()
                        val runnable = Runnable {
                            DB_OTPConfirmation.cntSent.visibility = View.GONE
                        }
                        handler.postDelayed(runnable, 1500)
                    }
                    handler.postDelayed(runnablex, 1000)
                }
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

    class GenericKeyEvent internal constructor(
        private val currentView: EditText,
        private val previousView: EditText?
    ) : View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != DB_OTPConfirmation.otp1.id && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView!!.text = null
                previousView.requestFocus()
                return true
            }
            return false
        }
    }

    class GenericTextWatcher internal constructor(
        private val currentView: View,
        private val nextView: View?
    ) :
        TextWatcher {
        override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
            val text = editable.toString()
            when (currentView.id) {
                DB_OTPConfirmation.otp1.id -> if (text.length == 1) nextView!!.requestFocus()
                DB_OTPConfirmation.otp2.id -> if (text.length == 1) nextView!!.requestFocus()
                DB_OTPConfirmation.otp3.id -> if (text.length == 1) nextView!!.requestFocus()
                DB_OTPConfirmation.otp4.id -> {
                    if (text.length == 1) {
                        ApplicationClass.hideKeyboard(OTPActivity)
                        nextView!!.requestFocus()
                        FinalOTPCode = GetOTP()
                        User_OTPConfirmation(
                            FinalOTPCode,
                            ApplicationClass.mLogInInfo_Model.data!!.userid
                        )
                    } else {

                    }

                }
            }
        }

        override fun beforeTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) {
        }

        override fun onTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) {
        }

    }
}
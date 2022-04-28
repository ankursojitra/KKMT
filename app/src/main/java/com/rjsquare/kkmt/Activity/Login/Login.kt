package com.rjsquare.kkmt.Activity.Login

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Alert
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.OTP.OTP_Confirmation
import com.rjsquare.kkmt.Activity.Register.Register_User
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.UserLogIn_Model
import com.rjsquare.kkmt.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    var MobileNo = ""
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Login = DataBindingUtil.setContentView(this, R.layout.activity_login)
        try {
            GlobalUsage.StatusTextWhite(this, true)
            LoginActivity = this

            // Set an editor action listener for edit text
            DB_Login.edtPhoneNum.setOnEditorActionListener(object :
                TextView.OnEditorActionListener {
                override fun onEditorAction(
                    textView: TextView?,
                    i: Int,
                    keyEvent: KeyEvent?
                ): Boolean {
                    // If user press done key
                    if (i == EditorInfo.IME_ACTION_DONE) {
                        Loader.showLoader(this@Login)
                        GlobalUsage.HiddenKeyBoard(this@Login, DB_Login.imgBack)
                        if (GetValidationConfirmation()) {
                            Loader.showLoader(this@Login)
                            LogInExistingUser()
                        }
                        return true
                    }
                    return false
                }
            })

            DB_Login.swEmp.setOnCheckedChangeListener(this)
            DB_Login.swEmp.isChecked = false
            DB_Login.txtProceed.setOnClickListener(this)
            DB_Login.txtSignup.setOnClickListener(this)
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

    private fun LogInExistingUser() {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_PhoneNo] = DB_Login.edtPhoneNum.text.toString()
            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.LogInCallService>(
                    NetworkServices.LogInCallService::class.java
                )
            val call = service.GetLoginData(params)
            call.enqueue(object : Callback<UserLogIn_Model> {
                override fun onFailure(call: Call<UserLogIn_Model>, t: Throwable) {
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<UserLogIn_Model>,
                    response: Response<UserLogIn_Model>
                ) {
                    Loader.hideLoader()
                    if (!response.body()!!.status.equals("200")) {
                        Alert.showDialog(this@Login, response.body()!!.message!!)
                    } else {
                        GlobalUsage.mLogInInfo_Model =
                            UserLogIn_Model()
                        GlobalUsage.mLogInInfo_Model = response.body()!!
                        if (GlobalUsage.mLogInInfo_Model.status.equals(
                                Constants.ResponseSucess,
                                true
                            )
                        ) {
                            GOTO_OTP()
                        } else {
                            Toast.makeText(
                                this@Login,
                                GlobalUsage.mLogInInfo_Model.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
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

    private fun GOTO_OTP() {
        GlobalUsage.NextScreen(this, Intent(this, OTP_Confirmation::class.java))
    }

    private fun GetValidationConfirmation(): Boolean {
        var Valid = false
        try {
            MobileNo = DB_Login.edtPhoneNum.text.toString()
            if (MobileNo.equals("") || MobileNo.length < 6 && MobileNo.length >= 13
            ) {
                Alert.showDialog(this,"Invalid Mobile Number.")
                Valid = false
            } else {
                Valid = true
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
        return Valid
    }

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_Login.txtProceed) {
                    if (GetValidationConfirmation()) {
                        Loader.showLoader(this)
                        LogInExistingUser()
                    }
                } else if (view == DB_Login.txtSignup) {
                    GlobalUsage.NextScreen(this, Intent(this, Register_User::class.java))
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

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (buttonView == DB_Login.swEmp) {
            GlobalUsage.isUserEmployee = isChecked
        }
    }

    companion object {
        lateinit var LoginActivity: Activity
        lateinit var DB_Login: ActivityLoginBinding
    }
}
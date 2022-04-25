package com.rjsquare.kkmt.Activity.Register

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.Helpers.Preferences
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.RegisterUserCall.UserInfoData_Model
import com.rjsquare.kkmt.databinding.ActivityRegisterUserBinding
import kotlinx.android.synthetic.main.layout_gender_list.view.*
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*


class Register_User : AppCompatActivity(), View.OnClickListener, OnSelectDateListener {

    private lateinit var mGenderListView: View

    var DatePickerVISIBLE = false
    var fNameErrorMsg = "Enter first name, atleast 3 character"
    var lNameErrorMsg = "Enter last name, atleast 3 character"
    var mobileErrorMsg = "Invalid Mobile Number."
    var emailErrorMsg = "Invalid e-mail address."
    var FirstName = ""
    var LastName = ""
    var MobileNo = ""
    var EmailAddress = ""
    var DOB = ""
    var Gender = ""
    lateinit var datePicker: DatePicker

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_RegisterUser = DataBindingUtil.setContentView(this, R.layout.activity_register__user)
        try {
            GlobalUsage.StatusTextWhite(this, true)
            Register_UserActivity = this

            mGenderListView = findViewById<View>(R.id.genderlist)

            mGenderListView.visibility = View.GONE

            DB_RegisterUser.txtDob.setOnClickListener(this)
            DB_RegisterUser.txtGender.setOnClickListener(this)
            mGenderListView.txt_male.setOnClickListener(this)
            mGenderListView.txt_female.setOnClickListener(this)
            mGenderListView.txt_other.setOnClickListener(this)
            DB_RegisterUser.txtOtpAlertok.setOnClickListener(this)

            DB_RegisterUser.txtSignup.setOnClickListener(this)
            DB_RegisterUser.txtLogin.setOnClickListener(this)

            val builder = DatePickerBuilder(this, this)
                .pickerType(CalendarView.ONE_DAY_PICKER)
                .setHeaderColor(R.color.appcolor) // Color of the dialog header
                .setHeaderLabelColor(R.color.white) // Color of the header label
                .setSelectionColor(R.color.appcolor) // Color of the selection circle
//                .setSelectionLabelColor(R.color.white) // Color of the label in the circle
//                .setTodayColor(R.color.appcolor) // Color of the present day background
                .setTodayLabelColor(R.color.appcolor) // Color of the today number
//                .setDialogButtonsColor(R.color.blue_dark); // Color of "Cancel" and "OK" buttons


            datePicker = builder.build()


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
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_RegisterUser.txtSignup) {
                    if (GetValidationConfirmation()) {
                        DB_RegisterUser.cntLoader.visibility = View.VISIBLE
                        RegisterNewUser()
                    }
                } else if (view == DB_RegisterUser.txtLogin) {
                    finish()
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                } else if (view == DB_RegisterUser.txtOtpAlertok) {
                    DB_RegisterUser.cntAlert.visibility = View.GONE
                } else if (view!!.id == R.id.constraintLayougender) {

                } else if (view == mGenderListView.txt_male) {
                    DB_RegisterUser.txtGender.text = "Male"
                    mGenderListView.visibility = View.GONE
                } else if (view == mGenderListView.txt_female) {
                    DB_RegisterUser.txtGender.text = "Female"
                    mGenderListView.visibility = View.GONE
                } else if (view == mGenderListView.txt_other) {
                    DB_RegisterUser.txtGender.text = "Other"
                    mGenderListView.visibility = View.GONE
                } else if (view == DB_RegisterUser.txtGender) {
                    if (mGenderListView.visibility == View.GONE)
                        mGenderListView.visibility = View.VISIBLE
                } else if (view == DB_RegisterUser.txtDob) {
                    if (!DatePickerVISIBLE) {
                        datePicker.show()
                        DatePickerVISIBLE = true
                    }
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

    fun ShowErrorMessage(alertMsg: String) {
        DB_RegisterUser.txtAlertmsg.text = alertMsg
        DB_RegisterUser.cntAlert.visibility = View.VISIBLE
    }

    private fun GetValidationConfirmation(): Boolean {
        var Valid = false

        try {
            FirstName = DB_RegisterUser.edtFirstName.text.toString()
            LastName = DB_RegisterUser.edtLastName.text.toString()
            MobileNo = DB_RegisterUser.edtPhoneNumber.text.toString()
            EmailAddress = DB_RegisterUser.edtEmail.text.toString()
            DOB = DB_RegisterUser.txtDob.text.toString()
            Gender = GlobalUsage.GenderParam(DB_RegisterUser.txtGender.text.toString())


            if (FirstName.equals("") || FirstName.length <= 3
            ) {
                ShowErrorMessage(fNameErrorMsg)
                Valid = false
            } else if (LastName.equals("") || LastName.length <= 3
            ) {
                ShowErrorMessage(lNameErrorMsg)
                Valid = false
            } else if (MobileNo.equals("") || MobileNo.length < 9 || MobileNo.length > 13
            ) {
                ShowErrorMessage(mobileErrorMsg)
                Valid = false
            } else if (EmailAddress.length > 0) {
                ShowErrorMessage(emailErrorMsg)
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

    private fun CheckEmailValidation(): Boolean {
        if (EmailAddress.equals("", true) &&
            !GlobalUsage.email_Pattern.matcher(EmailAddress).matches()
        ) {
            ShowErrorMessage("Enter Email address proper format")
            Toast.makeText(
                this,
                "Enter Email address proper format",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {
            return true
        }
    }

    private fun RegisterNewUser() {
        try {

            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_PhoneNo] = MobileNo
            params[Constants.paramKey_FirstName] = FirstName
            params[Constants.paramKey_LastName] = LastName
            params[Constants.paramKey_EmailAddress] = EmailAddress
            params[Constants.paramKey_DOB] = DOB
            params[Constants.paramKey_Gender] = Gender

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.RegisterUserService>(
                    NetworkServices.RegisterUserService::class.java
                )
            val call = service.GetRegisterUserData(params)
            call.enqueue(object : Callback<UserInfoData_Model> {
                override fun onFailure(call: Call<UserInfoData_Model>, t: Throwable) {
                    Log.e("GetResponse", ": " + t)
//                    RELiveMatcheScore()
                    DB_RegisterUser.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<UserInfoData_Model>,
                    response: retrofit2.Response<UserInfoData_Model>
                ) {
                    Log.e("GetResponsesas", ": " + Gson().toJson(response.body()!!))
                    GlobalUsage.userInfoModel = UserInfoData_Model()
                    GlobalUsage.userInfoModel = response.body()!!
                    if (GlobalUsage.userInfoModel.status.equals(Constants.ResponseSucess, true)) {
                        GlobalUsage.prefEditor.putString(
                            Constants.Pref_UserDataModel,
                            Gson().toJson(GlobalUsage.userInfoModel)
                        )
                        GlobalUsage.prefEditor.commit()
                        Preferences.StoreBoolean(Constants.Pref_UserLogedIn, true)
                        Log.e(
                            "TAG",
                            "LoginX" + Preferences.ReadBoolean(
                                Constants.Pref_UserLogedIn,
                                false
                            )
                        )
                        GlobalUsage.isUserEmployee = GlobalUsage.IsEmployee()

                        GOTOUpload()
                    } else {
                        Toast.makeText(
                            this@Register_User,
                            GlobalUsage.userInfoModel.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    DB_RegisterUser.cntLoader.visibility = View.GONE

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

    private fun GOTOUpload() {
        GlobalUsage.IsRegisterFlow = true
        GlobalUsage.NextScreen(this,Intent(this, upload_doc::class.java))
    }

    companion object {
        lateinit var Register_UserActivity: Activity
        lateinit var DB_RegisterUser: ActivityRegisterUserBinding
    }

    override fun onSelect(calendar: MutableList<Calendar>?) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDateandTime = sdf.format(calendar!![0].time)
        DB_RegisterUser.txtDob.text = currentDateandTime
        DatePickerVISIBLE = false
    }
}
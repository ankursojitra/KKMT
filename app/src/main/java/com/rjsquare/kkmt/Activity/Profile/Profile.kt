package com.rjsquare.kkmt.Activity.Profile

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityProfileBinding


class Profile : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }
    companion object{
        lateinit var DB_Profile:ActivityProfileBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Profile = DataBindingUtil.setContentView(this,R.layout.activity_profile)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            DB_Profile.imgBack.setOnClickListener(this)

            if (!ApplicationClass.IsUserEmployee) {
                DB_Profile.cntEmpProfile.visibility = View.VISIBLE
            } else {
                DB_Profile.cntEmpProfile.visibility = View.GONE
            }
            SetProfileData()
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

    private fun SetProfileData() {
        DB_Profile.txtProfileName.setText(ApplicationClass.userInfoModel.data!!.username)
        DB_Profile.edtFirstName.setText(ApplicationClass.userInfoModel.data!!.firstname)
        DB_Profile.edtLastName.setText(ApplicationClass.userInfoModel.data!!.lastname)
        DB_Profile.edtPhoneNumber.setText(ApplicationClass.userInfoModel.data!!.contactno)
        DB_Profile.edtEmail.setText(ApplicationClass.userInfoModel.data!!.email)
        DB_Profile.edtDob.setText(ApplicationClass.userInfoModel.data!!.dob)
        DB_Profile.edtGender.setText(ApplicationClass.Gender(ApplicationClass.userInfoModel.data!!.gender!!))
        DB_Profile.txtLevel.setText("Level : "+ApplicationClass.userInfoModel.data!!.credit_details!!.level)
        DB_Profile.txtCredits.setText(ApplicationClass.userInfoModel.data!!.credit_details!!.credit)
    }

    override fun onClick(view: View?) {
        try {
            if (view == DB_Profile.imgBack) {
                onBackPressed()
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
}
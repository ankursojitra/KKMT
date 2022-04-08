package com.rjsquare.kkmt.Activity.Bussiness

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Review.SearchEmployee
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.BusinessCheckInService
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.BusinessCheckInModel
import com.rjsquare.kkmt.databinding.ActivityBussinessCheckInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BussinessCheckIn : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var thisBusinessCheckIn: Activity
        lateinit var DB_BussinessCheckIn: ActivityBussinessCheckInBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_BussinessCheckIn =
            DataBindingUtil.setContentView(this, R.layout.activity_bussiness_check_in)
//        setContentView(R.layout.activity_bussiness_check_in)
        ApplicationClass.StatusTextWhite(this, false)
        thisBusinessCheckIn = this
        DB_BussinessCheckIn.imgBack.setOnClickListener(this)
        DB_BussinessCheckIn.txtContinue.setOnClickListener(this)
        DB_BussinessCheckIn.cntCheckout.setOnClickListener(this)
        DB_BussinessCheckIn.txtUnauthOk.setOnClickListener(this)
        CheckInCredit()

    }

    private fun CheckInCredit() {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!
            params[Constants.paramKey_BussinessId] =
                ApplicationClass.selectedMasterModel.businessid_db.toString()

            val service =
                ApiCallingInstance.retrofitInstance.create<BusinessCheckInService>(
                    BusinessCheckInService::class.java
                )
            val call =
                service.GetBusinessCheckInData(
                    params, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<BusinessCheckInModel> {
                override fun onFailure(call: Call<BusinessCheckInModel>, t: Throwable) {
                    DB_BussinessCheckIn.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<BusinessCheckInModel>,
                    response: Response<BusinessCheckInModel>
                ) {
                    DB_BussinessCheckIn.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {

                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_BussinessCheckIn.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {

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

    override fun onClick(v: View?) {
        if (v == DB_BussinessCheckIn.imgBack) {
            onBackPressed()
        } else if (v == DB_BussinessCheckIn.txtContinue) {
            var ReviewIntent = Intent(this, SearchEmployee::class.java)
            startActivity(ReviewIntent)
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        } else if (v == DB_BussinessCheckIn.cntCheckout) {
            finish()
//            Bussiness_Location.Bussiness_Activity.finish()
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
        } else if (v == DB_BussinessCheckIn.txtUnauthOk) {
            ApplicationClass.UserLogout(this)
        } else if (v == DB_BussinessCheckIn.txtAlert) {
            ApplicationClass.UserLogout(this)
        }
    }
}
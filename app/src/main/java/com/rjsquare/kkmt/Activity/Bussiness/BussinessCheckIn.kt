package com.rjsquare.kkmt.Activity.Bussiness

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.Review.SearchEmployee
import com.rjsquare.kkmt.Activity.Dialog.UnAuthorized
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.BusinessCheckInModel
import com.rjsquare.kkmt.databinding.ActivityBussinessCheckInBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BussinessCheckIn : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
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
        GlobalUsage.StatusTextWhite(this, false)
        thisBusinessCheckIn = this
        DB_BussinessCheckIn.imgBack.setOnClickListener(this)
        DB_BussinessCheckIn.txtContinue.setOnClickListener(this)
        DB_BussinessCheckIn.cntCheckout.setOnClickListener(this)

        Setupdata()
        CheckInCredit()

    }

    private fun Setupdata() {
        GlobalUsage.selectedMasterModel
        Picasso.with(this).load(GlobalUsage.selectedMasterModel.businessimage!!)
            .placeholder(R.drawable.expe_logo).into(
                DB_BussinessCheckIn.imgLogo
            )

        DB_BussinessCheckIn.txtBusinessName.text =
            GlobalUsage.selectedMasterModel.bussiness_name!!


    }

    private fun CheckInCredit() {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!
            params[Constants.paramKey_BussinessId] =
                GlobalUsage.selectedMasterModel.businessid.toString()

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.BusinessCheckInService>(
                    NetworkServices.BusinessCheckInService::class.java
                )
            val call =
                service.GetBusinessCheckInData(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<BusinessCheckInModel> {
                override fun onFailure(call: Call<BusinessCheckInModel>, t: Throwable) {
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<BusinessCheckInModel>,
                    response: Response<BusinessCheckInModel>
                ) {Loader.hideLoader()
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        DB_BussinessCheckIn.txtCredit.text =
                            GlobalUsage.formatNumber(response.body()!!.data!!.check_in_credit!!.toInt())
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        UnAuthorized.showDialog(this@BussinessCheckIn)
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
            GlobalUsage.NextScreen(this, Intent(this, SearchEmployee::class.java))
        } else if (v == DB_BussinessCheckIn.cntCheckout) {
            onBackPressed()
        }
    }
}
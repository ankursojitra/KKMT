package com.rjsquare.kkmt.Activity.Review

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Bussiness.BussinessCheckIn
import com.rjsquare.kkmt.Activity.Bussiness.Bussiness_Location
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.ReviewDetailModel
import com.rjsquare.kkmt.databinding.ActivityReviewShowBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class ReviewDisplay : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_ReviewDisplay: ActivityReviewShowBinding

        //        lateinit var ReviewInfo: ReviewInfodata
        var Hour24 = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ReviewDisplay = DataBindingUtil.setContentView(this, R.layout.activity_review_show)
//        setContentView(R.layout.activity_review_show)
        try {
            ApplicationClass.StatusTextWhite(this, true)

            DB_ReviewDisplay.imgBack.setOnClickListener(this)
            DB_ReviewDisplay.cntBacktohome.setOnClickListener(this)
            DB_ReviewDisplay.cntEditdetails.setOnClickListener(this)
            DB_ReviewDisplay.txtUnauthOk.setOnClickListener(this)
            DB_ReviewDisplay.txtAlertok.setOnClickListener(this)

            SetUpUI()

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

    private fun SetUpUI() {
        if (ApplicationClass.isNewReview) {
            //Employee search Screen Call
            SetUIData()


//            if (ApplicationClass.ReviewInfoModel.credit!!.bonus!!.equals("")) {
//                DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_verified))
//            } else if (ApplicationClass.ReviewInfoModel.credit!!.voice_note_status!!.equals(Constants.Pending)
//                || ApplicationClass.ReviewInfoModel.credit!!.description_status!!.equals(Constants.Pending)) {
//                DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_sand_clock))
//            } else {
//                DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_rejected))
//            }


        } else {
            Log.e("TAG", "CHECKEditFlow")
            //Customer History Screen Call
            val SelectedEmpInfo = ApplicationClass.empReviewModelSelected
            DB_ReviewDisplay.txtReviewName.text = SelectedEmpInfo!!.username
            Picasso.with(this).load(SelectedEmpInfo.userimage)
                .placeholder(R.drawable.ic_expe_logo).into(DB_ReviewDisplay.imgProfile)
            DB_ReviewDisplay.cntLoader.visibility = View.VISIBLE

            GetLatestReviewInfo()
        }
    }

    private fun GetLatestReviewInfo() {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            params[Constants.paramKey_ReviewId] =
                ApplicationClass.empReviewModelSelected!!.id!!

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.ReviewDetailService>(
                    NetworkServices.ReviewDetailService::class.java
                )
            val call =
                service.GetReviewDetailData(
                    params, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<ReviewDetailModel> {
                override fun onFailure(call: Call<ReviewDetailModel>, t: Throwable) {
                    DB_ReviewDisplay.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ReviewDetailModel>,
                    response: Response<ReviewDetailModel>
                ) {
                    DB_ReviewDisplay.cntLoader.visibility = View.GONE

                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        Log.e("TAG", "ResponseReview : " + Gson().toJson(response.body()!!))
//                        ReviewInfo = response.body()!!.data!!
                        ApplicationClass.ReviewInfoModel = response.body()!!.data!!
                        SetUIData()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_ReviewDisplay.cntUnAuthorized.visibility = View.VISIBLE
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

    private fun SetUIData() {
        //Latest Info update in UI
        Hour24 = 1000 * 60 * 60 * 24

        val CurrentTime = System.currentTimeMillis() / 1000
        val EditExpireTime =
            (ApplicationClass.ReviewInfoModel.created_timestamp!!.toLong() + Hour24.toLong())

        if (ApplicationClass.ReviewInfoModel.created_timestamp!!.toInt() > (CurrentTime + Hour24)) {
            DB_ReviewDisplay.cntEditdetails.visibility = View.GONE
        }
        Log.e("TAG", "TimeMilies : " + CurrentTime)
        Log.e("TAG", "TimeMilies : " + ApplicationClass.ReviewInfoModel.created_timestamp!!)
        var RestTime = (EditExpireTime - CurrentTime)

        val hours = ((RestTime / (1000 * 60 * 60)))
        TimeUnit.MILLISECONDS.toMinutes(RestTime)

        DB_ReviewDisplay.txtEdit.text =
            getString(R.string.editadditiondetail) + " ($hours Hours Left)"

        if (ApplicationClass.ReviewInfoModel.review_status!!.equals(Constants.Approve, true)) {
            DB_ReviewDisplay.cntEditdetails.visibility = View.GONE
        } else {
            DB_ReviewDisplay.cntEditdetails.visibility = View.VISIBLE
        }

        DB_ReviewDisplay.txtCheckinCredit.text =
            ApplicationClass.ReviewInfoModel.credit!!.check_in_credit
        DB_ReviewDisplay.txtEmpreviewCredit.text =
            ApplicationClass.ReviewInfoModel.credit!!.employee_review_credit
        DB_ReviewDisplay.txtProofCredit.text =
            ApplicationClass.ReviewInfoModel.credit!!.proof_of_purchase
        DB_ReviewDisplay.txtDetailCredit.text =
            ApplicationClass.ReviewInfoModel.credit!!.additional_details
        DB_ReviewDisplay.txtBonusCredit.text = ApplicationClass.ReviewInfoModel.credit!!.bonus


        DB_ReviewDisplay.txtReviewName.text = ApplicationClass.ReviewInfoModel.employee_name

        Picasso.with(this).load(ApplicationClass.ReviewInfoModel.employeimage)
            .placeholder(R.drawable.ic_expe_logo).into(DB_ReviewDisplay.imgProfile)

        if (ApplicationClass.ReviewInfoModel.credit!!.check_in_status!!.equals(Constants.Approve)) {
            DB_ReviewDisplay.imgCheckin.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_verified
                )
            )
        } else if (ApplicationClass.ReviewInfoModel.credit!!.check_in_status!!.equals(Constants.Pending)) {
            DB_ReviewDisplay.imgCheckin.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_sand_clock
                )
            )
        } else {
            DB_ReviewDisplay.imgCheckin.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_rejected
                )
            )
        }

        if (ApplicationClass.ReviewInfoModel.credit!!.receipt_status!!.equals(Constants.Approve)) {
            DB_ReviewDisplay.imgProofofpurchase.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_verified
                )
            )
        } else if (ApplicationClass.ReviewInfoModel.credit!!.receipt_status!!.equals(Constants.Pending)) {
            DB_ReviewDisplay.imgProofofpurchase.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_sand_clock
                )
            )
        } else {
            DB_ReviewDisplay.imgProofofpurchase.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_rejected
                )
            )
        }

        if (ApplicationClass.ReviewInfoModel.credit!!.description_status!!.equals(Constants.Approve)) {
            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_verified
                )
            )
        } else if (ApplicationClass.ReviewInfoModel.credit!!.description_status!!.equals(Constants.Pending)) {
            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_sand_clock
                )
            )
        } else {
            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_rejected
                )
            )
        }

        if (ApplicationClass.ReviewInfoModel.credit!!.voice_note_status!!.equals(Constants.Approve)
            || ApplicationClass.ReviewInfoModel.credit!!.description_status!!.equals(Constants.Approve)
        ) {
            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_verified
                )
            )
        } else if (ApplicationClass.ReviewInfoModel.credit!!.voice_note_status!!.equals(Constants.Pending)
            || ApplicationClass.ReviewInfoModel.credit!!.description_status!!.equals(Constants.Pending)
        ) {
            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_sand_clock
                )
            )
        } else {
            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_rejected
                )
            )
        }
        if (!ApplicationClass.ReviewInfoModel.credit!!.bonus!!.equals("0")) {
            if (ApplicationClass.ReviewInfoModel.review_status.equals(Constants.Approve)
            ) {
                DB_ReviewDisplay.imgBonus.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_verified
                    )
                )
            } else if (ApplicationClass.ReviewInfoModel.review_status.equals(Constants.Pending)
            ) {
                DB_ReviewDisplay.imgBonus.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_sand_clock
                    )
                )
            } else {
                DB_ReviewDisplay.imgBonus.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_rejected
                    )
                )
            }
            DB_ReviewDisplay.cntBonus.visibility = View.VISIBLE
        } else {
            DB_ReviewDisplay.cntBonus.visibility = View.GONE
        }


    }

    override fun onClick(v: View?) {
        try {
            if (v == DB_ReviewDisplay.imgBack) {
                onBackPressed()
            } else if (v == DB_ReviewDisplay.cntBacktohome) {
                if (ApplicationClass.isNewReview) {
                    Bussiness_Location.thisBussiness_Activity.finish()
                    BussinessCheckIn.thisBusinessCheckIn.finish()
                    SearchEmployee.thisSearchEmployee.finish()
                    ReviewEdit.thisReviewEdit.finish()
                    finish()
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
                } else {
                    HomeActivity.DB_HomeActivity.HomeScreen.ContentView.cntHome.performClick()
                    ReviewList.reviewListActivity.finish()
                    finish()
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
                }

            } else if (v == DB_ReviewDisplay.cntEditdetails) {
                onBackPressed()
            } else if (v == DB_ReviewDisplay.txtUnauthOk) {
                DB_ReviewDisplay.cntUnAuthorized.visibility = View.GONE
                ApplicationClass.UserLogout(this)
            } else if (v == DB_ReviewDisplay.txtAlertok) {
                DB_ReviewDisplay.cntAlert.visibility = View.GONE
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
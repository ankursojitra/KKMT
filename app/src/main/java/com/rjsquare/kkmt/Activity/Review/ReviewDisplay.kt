package com.rjsquare.kkmt.Activity.Review

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Bussiness.BussinessCheckIn
import com.rjsquare.kkmt.Activity.Bussiness.Bussiness_Beacon_Search
import com.rjsquare.kkmt.Activity.Dialog.Alert
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.Dialog.Network
import com.rjsquare.kkmt.Activity.Dialog.UnAuthorized
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.ReviewDetailModel
import com.rjsquare.kkmt.databinding.ActivityReviewShowBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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
            GlobalUsage.StatusTextWhite(this, true)

            DB_ReviewDisplay.imgBack.setOnClickListener(this)
            DB_ReviewDisplay.cntBacktohome.setOnClickListener(this)
            DB_ReviewDisplay.cntEditdetails.setOnClickListener(this)
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
        Log.e("TAG", "CHECKEditFlow : " + GlobalUsage.isNewReview)
        if (GlobalUsage.isNewReview) {
            //Employee search Screen Call
            SetUIData()


//            if (GlobalUsage.ReviewInfoModel.credit!!.bonus!!.equals("")) {
//                DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_verified))
//            } else if (GlobalUsage.ReviewInfoModel.credit!!.voice_note_status!!.equals(Constants.Pending)
//                || GlobalUsage.ReviewInfoModel.credit!!.description_status!!.equals(Constants.Pending)) {
//                DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_sand_clock))
//            } else {
//                DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_rejected))
//            }


        } else {
            Log.e("TAG", "CHECKEditFlow")
            //Customer History Screen Call
            val SelectedEmpInfo = GlobalUsage.empReviewModelSelected
            DB_ReviewDisplay.txtReviewName.text = SelectedEmpInfo!!.employee_name
            Picasso.with(this).load(SelectedEmpInfo.employeimage)
                .placeholder(R.drawable.expe_logo).into(DB_ReviewDisplay.imgProfile)
            if (!GlobalUsage.IsNetworkAvailable(this)) {
                Network.showDialog(this)
                return
            }
            Loader.showLoader(this)
            GetLatestReviewInfo()
        }
    }

    private fun GetLatestReviewInfo() {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!

            params[Constants.paramKey_ReviewId] =
                GlobalUsage.empReviewModelSelected!!.id!!

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.ReviewDetailService>(
                    NetworkServices.ReviewDetailService::class.java
                )
            val call =
                service.GetReviewDetailData(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<ReviewDetailModel> {
                override fun onFailure(call: Call<ReviewDetailModel>, t: Throwable) {
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<ReviewDetailModel>,
                    response: Response<ReviewDetailModel>
                ) {
                    Loader.hideLoader()

                    Log.e("TAG", "ResponseReview : " + Gson().toJson(response.body()!!))
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
//                        ReviewInfo = response.body()!!.data!!
                        GlobalUsage.ReviewInfoModel = response.body()!!.data!!
                        SetUIData()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        UnAuthorized.showDialog(this@ReviewDisplay)
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {
                        Alert.showDialog(this@ReviewDisplay, response.body()!!.message!!)
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

        val differHours = differenceTime(GlobalUsage.ReviewInfoModel.created_at!!)
        Log.e("TAG", "TimeMiliessa : " + differHours)
        DB_ReviewDisplay.txtEdit.text =
            getString(R.string.editadditiondetail) + " ($differHours Hours Left)"

        if (GlobalUsage.ReviewInfoModel.review_status!!.equals(
                Constants.Approve,
                true
            ) || GlobalUsage.isReviewChange
        ) {
            DB_ReviewDisplay.cntEditdetails.visibility = View.GONE
        } else {
            if (differHours < GlobalUsage.EditReviewHours) {
                DB_ReviewDisplay.cntEditdetails.visibility = View.VISIBLE
            } else {
                DB_ReviewDisplay.cntEditdetails.visibility = View.GONE
            }
        }

        DB_ReviewDisplay.txtCheckinCredit.text =
            GlobalUsage.ReviewInfoModel.credit!!.check_in_credit
        DB_ReviewDisplay.txtEmpreviewCredit.text =
            GlobalUsage.ReviewInfoModel.credit!!.employee_review_credit
        DB_ReviewDisplay.txtProofCredit.text =
            GlobalUsage.ReviewInfoModel.credit!!.proof_of_purchase
        DB_ReviewDisplay.txtDetailCredit.text =
            GlobalUsage.ReviewInfoModel.credit!!.additional_details
        DB_ReviewDisplay.txtBonusCredit.text = GlobalUsage.ReviewInfoModel.credit!!.bonus


        DB_ReviewDisplay.txtReviewName.text = GlobalUsage.ReviewInfoModel.employee_name

        Picasso.with(this).load(GlobalUsage.ReviewInfoModel.employeimage)
            .placeholder(R.drawable.expe_logo).into(DB_ReviewDisplay.imgProfile)

        if (GlobalUsage.ReviewInfoModel.credit!!.check_in_status!!.equals(Constants.Approve)) {
            DB_ReviewDisplay.imgCheckin.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_verified
                )
            )
        } else if (GlobalUsage.ReviewInfoModel.credit!!.check_in_status!!.equals(Constants.Pending)) {
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

        if (GlobalUsage.ReviewInfoModel.review_status!!.equals(Constants.Approve)) {
            DB_ReviewDisplay.imgCheckin.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_verified
                )
            )
        } else if (GlobalUsage.ReviewInfoModel.review_status!!.equals(Constants.Pending)) {
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

        if (GlobalUsage.ReviewInfoModel.credit!!.receipt_status!!.equals(Constants.Approve)) {
            DB_ReviewDisplay.imgProofofpurchase.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_verified
                )
            )
        } else if (GlobalUsage.ReviewInfoModel.credit!!.receipt_status!!.equals(Constants.Pending)) {
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

        if (GlobalUsage.ReviewInfoModel.credit!!.additional_status!!.equals(Constants.Approve)) {
            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_verified
                )
            )
        } else if (GlobalUsage.ReviewInfoModel.credit!!.additional_status!!.equals(Constants.Pending)) {
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

//        if (GlobalUsage.ReviewInfoModel.credit!!.voice_note_status!!.equals(Constants.Approve)
//            || GlobalUsage.ReviewInfoModel.credit!!.description_status!!.equals(Constants.Approve)
//        ) {
//            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.ic_verified
//                )
//            )
//        } else if (GlobalUsage.ReviewInfoModel.credit!!.voice_note_status!!.equals(Constants.Pending)
//            || GlobalUsage.ReviewInfoModel.credit!!.description_status!!.equals(Constants.Pending)
//        ) {
//            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.ic_sand_clock
//                )
//            )
//        } else {
//            DB_ReviewDisplay.imgAdditionaldetail.setImageDrawable(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.ic_rejected
//                )
//            )
//        }

        if (!GlobalUsage.ReviewInfoModel.credit!!.bonus!!.equals("0")) {
            if (GlobalUsage.ReviewInfoModel.review_status.equals(Constants.Approve)
            ) {
                DB_ReviewDisplay.imgBonus.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_verified
                    )
                )
            } else if (GlobalUsage.ReviewInfoModel.review_status.equals(Constants.Pending)
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

    private fun differenceTime(createdAt: String): Int {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        var dateCreate = simpleDateFormat.parse(createdAt)
        val df =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss") // pass the format pattern that you like and done.

        var dateCreateds = df.format(dateCreate)

        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = System.currentTimeMillis()
        val serverDateFormate: String = DateFormat.format("yyyy-MM-dd HH:mm:ss", cal).toString()
        var currentDate = df.parse(serverDateFormate)
        var reviewDate = df.parse(dateCreateds)

        val difference: Long = currentDate.time - reviewDate.time
        val seconds = (difference / 1000).toInt()
        val minutes = (seconds / 60).toInt()
        val hours = (minutes / 60).toInt()
        val days = (hours / 24).toInt()

//        var days = (difference / (1000 * 60 * 60 * 24)).toInt()
//        var hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
//       var hours = ((((difference) / (1000 * 60 * 60)).toInt())-1000 * 60 * 60 * 24 * days)
//        var min = (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
//        hours = if (hours < 0) -hours else hours
        return hours
    }

    override fun onClick(v: View?) {
        try {
            if (v == DB_ReviewDisplay.imgBack) {
                onBackPressed()
            } else if (v == DB_ReviewDisplay.cntBacktohome) {
                if (GlobalUsage.isNewReview) {
//                    var LogInIntent = Intent(this, HomeActivity::class.java)
//                    LogInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                    startActivity(LogInIntent)
//                    finish()
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
                    Bussiness_Beacon_Search.thisBussiness_Activity.finish()
//                    BussinessCheckIn.thisBusinessCheckIn.finish()
                    SearchEmployee.thisSearchEmployee.finish()
                    ReviewEdit.thisReviewEdit.finish()
                    finish()
                } else {
                    HomeActivity.DB_HomeActivity.HomeScreen.ContentView.cntHome.performClick()
                    ReviewList.reviewListActivity.finish()
                    finish()
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
                }

            } else if (v == DB_ReviewDisplay.cntEditdetails) {
                GlobalUsage.isNewReview = false
                GlobalUsage.NextScreen(this, Intent(this, ReviewEdit::class.java))
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
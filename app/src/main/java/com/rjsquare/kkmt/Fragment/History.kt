package com.rjsquare.kkmt.Fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Activity.Review.ReviewList
import com.rjsquare.kkmt.Activity.Dialog.UnAuthorized
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.CustomerHistoryModel
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.ReviewInfodata
import com.rjsquare.kkmt.databinding.FragmentHistoryBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class History : Fragment(), View.OnClickListener {

    lateinit var latestReviewList: ArrayList<ReviewInfodata>
    lateinit var pendingReviewList: ArrayList<ReviewInfodata>
    lateinit var approveReviewList: ArrayList<ReviewInfodata>
    lateinit var rejectedReviewList: ArrayList<ReviewInfodata>
    lateinit var ReviewModel: CustomerHistoryModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    lateinit var DB_FHistory: FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DB_FHistory = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        try {
            DB_FHistory.cpFiveStar.maxProgress = 100.0
            DB_FHistory.cpFiveStar.setCurrentProgress(0.0)
            DB_FHistory.cpGood.maxProgress = 100.0
            DB_FHistory.cpGood.setCurrentProgress(0.0)

            DB_FHistory.cpOneStar.maxProgress = 100.0
            DB_FHistory.cpOneStar.setCurrentProgress(0.0)

            DB_FHistory.cpBad.maxProgress = 100.0
            DB_FHistory.cpBad.setCurrentProgress(0.0)

            DB_FHistory.cardViewHistory1.setOnClickListener(this)
            DB_FHistory.cardViewHistory2.setOnClickListener(this)
            DB_FHistory.cardViewHistory3.setOnClickListener(this)

            latestReviewList = ArrayList()
            pendingReviewList = ArrayList()
            approveReviewList = ArrayList()
            rejectedReviewList = ArrayList()


            if (GlobalUsage.userLogedIn) {
                HistoryReviewData()
            }

            HistoryView = true
//            Loader.hideLoader()
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
        return DB_FHistory.root
    }

    private fun HistoryReviewData() {
        try {
//            Loader.showLoader(requireActivity())
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.CustomerHistoryService>(
                    NetworkServices.CustomerHistoryService::class.java
                )
            val call =
                service.GetCustomerHistoryData(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<CustomerHistoryModel> {
                override fun onFailure(call: Call<CustomerHistoryModel>, t: Throwable) {
                    Loader.hideLoader()
                    Log.e("TAG","HistoryReviewData t: "+t)
                }

                override fun onResponse(
                    call: Call<CustomerHistoryModel>,
                    response: Response<CustomerHistoryModel>
                ) {
                    Loader.hideLoader()
                    Log.e("TAG","HistoryReviewData : ")
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        ReviewModel = response.body()!!
                        if (!response.body()!!.data!!.latest_review.isNullOrEmpty()) {
                            latestReviewList.addAll(response.body()!!.data!!.latest_review!!)
                        }
                        if (!response.body()!!.data!!.panding_review.isNullOrEmpty()) {
                            pendingReviewList.addAll(response.body()!!.data!!.panding_review!!)
                        }
                        if (!response.body()!!.data!!.approve_review.isNullOrEmpty()) {
                            approveReviewList.addAll(response.body()!!.data!!.approve_review!!)
                        }
                        if (!response.body()!!.data!!.cancel_review.isNullOrEmpty()) {
                            rejectedReviewList.addAll(response.body()!!.data!!.cancel_review!!)
                        }

                        FillData()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        UnAuthorized.showDialog(requireActivity())
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

    companion object {
        var HistoryView = false

        @JvmStatic
        fun newInstance(

        ) =
            History().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun FillData() {
        try {
            SetRatingData()
            GlobalUsage.mPendingReviewList = pendingReviewList
            GlobalUsage.mApproveReviewList = approveReviewList
            GlobalUsage.mCancelReviewList = rejectedReviewList

            var ReviewCount = latestReviewList.size
            if (ReviewCount >= 3) {
                DB_FHistory.cardViewHistory1.visibility = View.VISIBLE
                DB_FHistory.cardViewHistory2.visibility = View.VISIBLE
                DB_FHistory.cardViewHistory3.visibility = View.VISIBLE
                DB_FHistory.cardViewReview.visibility = View.VISIBLE
                DB_FHistory.txtNoReviews.visibility = View.GONE

                SetFirstReview(latestReviewList[0])
                SetSecondReview(latestReviewList[1])
                SetThirdReview(latestReviewList[2])


            } else if (ReviewCount == 2) {
                DB_FHistory.cardViewHistory1.visibility = View.VISIBLE
                DB_FHistory.cardViewHistory2.visibility = View.VISIBLE
                DB_FHistory.cardViewHistory3.visibility = View.GONE
                DB_FHistory.cardViewReview.visibility = View.VISIBLE
                DB_FHistory.txtNoReviews.visibility = View.GONE

                SetFirstReview(latestReviewList[0])
                SetSecondReview(latestReviewList[1])
            } else if (ReviewCount == 1) {
                DB_FHistory.cardViewHistory1.visibility = View.VISIBLE
                DB_FHistory.cardViewHistory2.visibility = View.GONE
                DB_FHistory.cardViewHistory3.visibility = View.GONE
                DB_FHistory.cardViewReview.visibility = View.VISIBLE
                DB_FHistory.txtNoReviews.visibility = View.GONE

                SetFirstReview(latestReviewList[0])
            } else if (ReviewCount == 0) {
                DB_FHistory.cardViewHistory1.visibility = View.GONE
                DB_FHistory.cardViewHistory2.visibility = View.GONE
                DB_FHistory.cardViewHistory3.visibility = View.GONE
                DB_FHistory.cardViewReview.visibility = View.GONE
                DB_FHistory.txtNoReviews.visibility = View.VISIBLE
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

    private fun SetRatingData() {

        var TotalAmount = ReviewModel.data!!.total_spend_amount.toString().toInt()
        var TotalReview = ReviewModel.data!!.total_star_count.toString().toInt()
        var FiveStar = ReviewModel.data!!.total_star_type_wise!!.five_star.toString().toInt()
        var Good = ReviewModel.data!!.total_star_type_wise!!.Good.toString().toInt()
        var OneStar = ReviewModel.data!!.total_star_type_wise!!.one_star.toString().toInt()
        var Bad = ReviewModel.data!!.total_star_type_wise!!.Bad.toString().toInt()

        var FiveStarPer = ((FiveStar * 100.0) / TotalReview)
        var GoodPer = ((Good * 100.0) / TotalReview)
        var OneStarPer = ((OneStar * 100.0) / TotalReview)
        var BadPer = ((Bad * 100.0) / TotalReview)

        if (FiveStar == 0) FiveStarPer = 0.0
        if (Good == 0) GoodPer = 0.0
        if (OneStar == 0) OneStarPer = 0.0
        if (Bad == 0) BadPer = 0.0

        DB_FHistory.txtTotalAmount.text = "Support Total : $$TotalAmount"

        DB_FHistory.cpFiveStar.setCurrentProgress(FiveStarPer)
        DB_FHistory.cpGood.setCurrentProgress(GoodPer)
        DB_FHistory.cpOneStar.setCurrentProgress(OneStarPer)
        DB_FHistory.cpBad.setCurrentProgress(BadPer)

        DB_FHistory.txtFiveStar.text = ("$FiveStarPer%")
        DB_FHistory.txtGood.text = ("$GoodPer%")
        DB_FHistory.txtOneStar.text = ("$OneStarPer%")
        DB_FHistory.txtBad.text = ("$BadPer%")
    }

    private fun SetThirdReview(reviewItemInfoModel: ReviewInfodata) {
        Picasso.with(requireActivity()).load(reviewItemInfoModel.employeimage)
            .placeholder(R.drawable.expe_logo).into(DB_FHistory.imgEmployeeHistory3)
        DB_FHistory.txtEmpname1History3.text = reviewItemInfoModel.employee_name
        DB_FHistory.txtEmpratingHistory3.text = reviewItemInfoModel.review
        DB_FHistory.txtEmpTotalSupportHistory3.text = ("$${reviewItemInfoModel.receipt_amount}")
    }

    private fun SetSecondReview(reviewItemInfoModel: ReviewInfodata) {
        Picasso.with(requireActivity()).load(reviewItemInfoModel.employeimage)
            .placeholder(R.drawable.expe_logo).into(DB_FHistory.imgEmployeeHistory2)
        DB_FHistory.txtEmpname1History2.text = reviewItemInfoModel.employee_name
        DB_FHistory.txtEmpratingHistory2.text = reviewItemInfoModel.review
        DB_FHistory.txtEmpTotalSupportHistory2.text = ("$${reviewItemInfoModel.receipt_amount}")
    }

    private fun SetFirstReview(reviewItemInfoModel: ReviewInfodata) {
        Picasso.with(requireActivity()).load(reviewItemInfoModel.employeimage)
            .placeholder(R.drawable.expe_logo).into(DB_FHistory.imgEmployee)
        DB_FHistory.txtEmpname1.text = reviewItemInfoModel.employee_name
        DB_FHistory.txtEmprating.text = reviewItemInfoModel.review
        DB_FHistory.txtEmpTotalSupport.text = ("$${reviewItemInfoModel.receipt_amount}")
    }

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_FHistory.cardViewHistory1 || view == DB_FHistory.cardViewHistory2 || view == DB_FHistory.cardViewHistory3) {
                    GlobalUsage.NextScreen(
                        requireActivity(),
                        Intent(requireActivity(), ReviewList::class.java)
                    )
//                    var HistoryReviewIntent = Intent(requireActivity(), ReviewList::class.java)
//                    requireActivity().startActivity(HistoryReviewIntent)
//                    requireActivity().overridePendingTransition(
//                        R.anim.activity_in,
//                        R.anim.activity_out
//                    )
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
}
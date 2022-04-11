package com.rjsquare.kkmt.Fragment

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.EmployeeHistoryService
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.EmployeeHistoryModel
import com.rjsquare.kkmt.databinding.FragmentEmployeeHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EmployeeHistory : Fragment() {

    lateinit var EmpHistory: EmployeeHistoryModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    lateinit var DB_FEmployeeHistory: FragmentEmployeeHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        DB_FEmployeeHistory =
            DataBindingUtil.inflate(inflater, R.layout.fragment_employee_history, container, false)
        try {

            DB_FEmployeeHistory.cpOverallFiveStar.maxProgress = 100.0
            DB_FEmployeeHistory.cpOverallFiveStar.setCurrentProgress(0.0)

            DB_FEmployeeHistory.cpOverallGood.maxProgress = 100.0
            DB_FEmployeeHistory.cpOverallGood.setCurrentProgress(0.0)

            DB_FEmployeeHistory.cpOverallOneStar.maxProgress = 100.0
            DB_FEmployeeHistory.cpOverallOneStar.setCurrentProgress(0.0)

            DB_FEmployeeHistory.cpOverallBad.maxProgress = 100.0
            DB_FEmployeeHistory.cpOverallBad.setCurrentProgress(0.0)

            HomeActivity.mCntLoader.visibility = View.GONE
            EmpHistoryView = true

            EmpHistory = EmployeeHistoryModel()
            DB_FEmployeeHistory.cntLoader.visibility = View.VISIBLE
            HistoryEmpReviewData()
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
        return DB_FEmployeeHistory.root
    }

    private fun HistoryEmpReviewData() {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_EmployeeId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<EmployeeHistoryService>(
                    EmployeeHistoryService::class.java
                )
            val call =
                service.GetEmployeeHistoryData(
                    params,
                    ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<EmployeeHistoryModel> {
                override fun onFailure(call: Call<EmployeeHistoryModel>, t: Throwable) {
                    DB_FEmployeeHistory.cntLoader.visibility = View.GONE
                    Log.e("TAG", "EMPHistoryDataError : "+t)
                }

                override fun onResponse(
                    call: Call<EmployeeHistoryModel>,
                    response: Response<EmployeeHistoryModel>
                ) {
                    DB_FEmployeeHistory.cntLoader.visibility = View.GONE
                    Log.e("TAG", "EMPHistoryData : " + Gson().toJson(response.body()!!))

                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        EmpHistory = response.body()!!
                        SetOverallRatingData()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        HomeActivity.UnauthorizedUser()
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

    private fun SetOverallRatingData() {
        var OverallTotalAmount = EmpHistory.data!!.overall!!.toal_sales.toString().toInt()
        var OverallTotalReview = EmpHistory.data!!.overall!!.total_star_count.toString().toInt()
        var OverallFiveStar =
            EmpHistory.data!!.overall!!.total_star_type_wise!!.five_star.toString().toInt()
        var OverallGood = EmpHistory.data!!.overall!!.total_star_type_wise!!.Good.toString().toInt()
        var OverallOneStar =
            EmpHistory.data!!.overall!!.total_star_type_wise!!.one_star.toString().toInt()
        var OverallBad = EmpHistory.data!!.overall!!.total_star_type_wise!!.Bad.toString().toInt()

        var OverallFiveStarPer = ((OverallFiveStar * 100.0) / OverallTotalReview)
        var OverallGoodPer = ((OverallGood * 100.0) / OverallTotalReview)
        var OverallOneStarPer = ((OverallOneStar * 100.0) / OverallTotalReview)
        var OverallBadPer = ((OverallBad * 100.0) / OverallTotalReview)

        if (OverallFiveStar == 0) OverallFiveStarPer = 0.0
        if (OverallGood == 0) OverallGoodPer = 0.0
        if (OverallOneStar == 0) OverallOneStarPer = 0.0
        if (OverallBad == 0) OverallBadPer = 0.0

        DB_FEmployeeHistory.txtTotalAmount.text = "Sales Total : $$OverallTotalAmount"

        DB_FEmployeeHistory.cpOverallFiveStar.setCurrentProgress(OverallFiveStarPer)
        DB_FEmployeeHistory.cpOverallGood.setCurrentProgress(OverallGoodPer)
        DB_FEmployeeHistory.cpOverallOneStar.setCurrentProgress(OverallOneStarPer)
        DB_FEmployeeHistory.cpOverallBad.setCurrentProgress(OverallBadPer)

        DB_FEmployeeHistory.txtFiveStar.text = String.format("%.1f",OverallFiveStarPer)+"%"
        DB_FEmployeeHistory.txtGood.text = String.format("%.1f",OverallGoodPer)+"%"
        DB_FEmployeeHistory.txtOneStar.text = String.format("%.1f",OverallOneStarPer)+"%"
        DB_FEmployeeHistory.txtBad.text = String.format("%.1f",OverallBadPer)+"%"

        //----Set Last week Data----
        var LastWeekTotalReview = EmpHistory.data!!.last_week!!.total_star_count.toString().toInt()
        var LastWeekFiveStar =
            EmpHistory.data!!.last_week!!.total_star_type_wise!!.five_star.toString().toInt()
        var LastWeekGood =
            EmpHistory.data!!.last_week!!.total_star_type_wise!!.Good.toString().toInt()
        var LastWeekOneStar =
            EmpHistory.data!!.last_week!!.total_star_type_wise!!.one_star.toString().toInt()
        var LastWeekBad =
            EmpHistory.data!!.last_week!!.total_star_type_wise!!.Bad.toString().toInt()

        var LastWeekFiveStarPer = ((LastWeekFiveStar * 100.0) / LastWeekTotalReview)
        var LastWeekGoodPer = ((LastWeekGood * 100.0) / LastWeekTotalReview)
        var LastWeekOneStarPer = ((LastWeekOneStar * 100.0) / LastWeekTotalReview)
        var LastWeekBadPer = ((LastWeekBad * 100.0) / LastWeekTotalReview)

        if (LastWeekFiveStar == 0) LastWeekFiveStarPer = 0.0
        if (LastWeekGood == 0) LastWeekGoodPer = 0.0
        if (LastWeekOneStar == 0) LastWeekOneStarPer = 0.0
        if (LastWeekBad == 0) LastWeekBadPer = 0.0

        DB_FEmployeeHistory.cpLastweekFiveStar.setCurrentProgress(LastWeekFiveStarPer)
        DB_FEmployeeHistory.cpLastweekGood.setCurrentProgress(LastWeekGoodPer)
        DB_FEmployeeHistory.cpLastweekOneStar.setCurrentProgress(LastWeekOneStarPer)
        DB_FEmployeeHistory.cpLastweekBad.setCurrentProgress(LastWeekBadPer)

        //----Set Monthly Data----
        var MonthlyTotalReview = EmpHistory.data!!.last_month!!.total_star_count.toString().toInt()
        var MonthlyFiveStar =
            EmpHistory.data!!.last_month!!.total_star_type_wise!!.five_star.toString().toInt()
        var MonthlyGood =
            EmpHistory.data!!.last_month!!.total_star_type_wise!!.Good.toString().toInt()
        var MonthlyOneStar =
            EmpHistory.data!!.last_month!!.total_star_type_wise!!.one_star.toString().toInt()
        var MonthlyBad =
            EmpHistory.data!!.last_month!!.total_star_type_wise!!.Bad.toString().toInt()

        var MonthlyFiveStarPer = ((MonthlyFiveStar * 100.0) / MonthlyTotalReview)
        var MonthlyGoodPer = ((MonthlyGood * 100.0) / MonthlyTotalReview)
        var MonthlyOneStarPer = ((MonthlyOneStar * 100.0) / MonthlyTotalReview)
        var MonthlyBadPer = ((MonthlyBad * 100.0) / MonthlyTotalReview)

        if (MonthlyFiveStar == 0) MonthlyFiveStarPer = 0.0
        if (MonthlyGood == 0) MonthlyGoodPer = 0.0
        if (MonthlyOneStar == 0) MonthlyOneStarPer = 0.0
        if (MonthlyBad == 0) MonthlyBadPer = 0.0

        DB_FEmployeeHistory.cpLastmonthFiveStar.setCurrentProgress(MonthlyFiveStarPer)
        DB_FEmployeeHistory.cpLastmonthGood.setCurrentProgress(MonthlyGoodPer)
        DB_FEmployeeHistory.cpLastmonthOneStar.setCurrentProgress(MonthlyOneStarPer)
        DB_FEmployeeHistory.cpLastmonthBad.setCurrentProgress(MonthlyBadPer)

    }

    companion object {
        var EmpHistoryView = false

        @JvmStatic
        fun newInstance(

        ) =
            EmployeeHistory().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
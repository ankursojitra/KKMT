package com.rjsquare.kkmt.Fragment

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.EmployeeHistoryService
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.EmployeeHistoryModel
import com.rjsquare.kkmt.databinding.FragmentEmployeeHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EmployeeHistory : Fragment() {

    lateinit var EmpHistory : EmployeeHistoryModel

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

            params[ApplicationClass.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<EmployeeHistoryService>(
                    EmployeeHistoryService::class.java
                )
            val call =
                service.GetEmployeeHistoryData(
                    params, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<EmployeeHistoryModel> {
                override fun onFailure(call: Call<EmployeeHistoryModel>, t: Throwable) {
                    DB_FEmployeeHistory.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<EmployeeHistoryModel>,
                    response: Response<EmployeeHistoryModel>
                ) {
                    DB_FEmployeeHistory.cntLoader.visibility = View.GONE

                    if (response.body()!!.status.equals(ApplicationClass.ResponseSucess)) {
                        EmpHistory = response.body()!!
                        SetOverallRatingData()
                    } else if (response.body()!!.status.equals(ApplicationClass.ResponseUnauthorized)) {
                        HomeActivity.UnauthorizedUser()
                    } else if (response.body()!!.status.equals(ApplicationClass.ResponseEmpltyList)) {

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
        var TotalAmount = EmpHistory.data!!.overall!!.toal_sales.toString().toInt()
        var TotalReview = EmpHistory.data!!.overall!!.total_star_count.toString().toInt()
        var FiveStar = EmpHistory.data!!.overall!!.total_star_type_wise!!.five_star.toString().toInt()
        var Good = EmpHistory.data!!.overall!!.total_star_type_wise!!.Good.toString().toInt()
        var OneStar = EmpHistory.data!!.overall!!.total_star_type_wise!!.one_star.toString().toInt()
        var Bad = EmpHistory.data!!.overall!!.total_star_type_wise!!.Bad.toString().toInt()

        var FiveStarPer = ((FiveStar * 100.0) / TotalReview)
        var GoodPer = ((Good * 100.0) / TotalReview)
        var OneStarPer = ((OneStar * 100.0) / TotalReview)
        var BadPer = ((Bad * 100.0) / TotalReview)

        if (FiveStar == 0) FiveStarPer = 0.0
        if (Good == 0) GoodPer = 0.0
        if (OneStar == 0) OneStarPer = 0.0
        if (Bad == 0) BadPer = 0.0

        DB_FEmployeeHistory.txtTotalAmount.text = "Sales Total : $$TotalAmount"

        DB_FEmployeeHistory.cpOverallFiveStar.setCurrentProgress(FiveStarPer)
        DB_FEmployeeHistory.cpOverallGood.setCurrentProgress(GoodPer)
        DB_FEmployeeHistory.cpOverallOneStar.setCurrentProgress(OneStarPer)
        DB_FEmployeeHistory.cpOverallBad.setCurrentProgress(BadPer)

//        DB_FEmployeeHistory.txtFiveStar.text = ("$FiveStarPer%")
//        DB_FEmployeeHistory.txtGood.text = ("$GoodPer%")
//        DB_FEmployeeHistory.txtOneStar.text = ("$OneStarPer%")
//        DB_FEmployeeHistory.txtBad.text = ("$BadPer%")
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
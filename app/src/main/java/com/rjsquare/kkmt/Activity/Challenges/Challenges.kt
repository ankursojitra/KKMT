package com.rjsquare.kkmt.Activity.Challenges

import android.content.ActivityNotFoundException
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Adapter.ChallengesAdapter
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.ChallangesModel
import com.rjsquare.kkmt.databinding.ActivityChallengesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Challenges : AppCompatActivity(), View.OnClickListener {
    lateinit var dailyChallengesList: ArrayList<ChallangesModel.Challange>
    lateinit var weeklyChallengesList: ArrayList<ChallangesModel.Challange>
    lateinit var monthlyChallengesList: ArrayList<ChallangesModel.Challange>
    lateinit var DB_Challenges: ActivityChallengesBinding
    var dayPageNo = 0
    var weekPageNo = 0
    var monthPageNo = 0
    var PagePerlimit = 10
    var dailyChallangeCallavailable = true
    var weeklyChallangeCallavailable = true
    var monthlyChallangeCallavailable = true
    var dataSize = 0

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Challenges = DataBindingUtil.setContentView(this, R.layout.activity_challenges)
        try {
            GlobalUsage.StatusTextWhite(this, true)

            initListners()

            fillData()
            dailyframesAdapter()
            weeklyframesAdapter()
            monthlyframesAdapter()
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

    private fun initListners() {
        dailyChallengesList = ArrayList()
        weeklyChallengesList = ArrayList()
        monthlyChallengesList = ArrayList()

        DB_Challenges.imgBack.setOnClickListener(this)
        DB_Challenges.txtDaily.setOnClickListener(this)
        DB_Challenges.txtWeekly.setOnClickListener(this)
        DB_Challenges.txtMonthly.setOnClickListener(this)
    }

    private fun fillData() {
        try {
            DailyChallanges((++dayPageNo).toString(), PagePerlimit.toString())
            WeeklyChallanges((++weekPageNo).toString(), PagePerlimit.toString())
            MonthlyChallanges((++monthPageNo).toString(), PagePerlimit.toString())
//            var mChallengesModel = ChallengesModel()
//            mChallengesModel.Txt1 = "Watch 3 Videos"
//            mChallengesModel.Txt2 = "Check-in into 1 location"
//            mChallengesModel.Txt3 = "Leave 1 Verified Reviews"
//            dailyChallengesModel.add(mChallengesModel)
//
//            var mChallengesModelx = ChallengesModel()
//            mChallengesModelx.Txt1 = ""
//            mChallengesModelx.Txt2 = "Complete 1 Survey"
//            mChallengesModelx.Txt3 = ""
//            dailyChallengesModel.add(mChallengesModelx)
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

    private fun DailyChallanges(PageNo: String, PagePerlimit: String) {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!
            params[Constants.paramKey_Usertype] =
                GlobalUsage.userInfoModel.data!!.usertype!!
            params[Constants.paramKey_PageNo] = PageNo
            params[Constants.paramKey_limit] = PagePerlimit

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.DailyChallangeService>(
                    NetworkServices.DailyChallangeService::class.java
                )
            val call =
                service.DailyChallangeData(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<ChallangesModel> {
                override fun onFailure(call: Call<ChallangesModel>, t: Throwable) {
                    DB_Challenges.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ChallangesModel>,
                    response: Response<ChallangesModel>
                ) {
                    DB_Challenges.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        dailyChallengesList.addAll(response.body()!!.data!!)
                        dailyframesAdapter()
                        dailyChallangeCallavailable =
                            response.body()!!.data!!.size >= this@Challenges.PagePerlimit
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_Challenges.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {
                        dailyChallangeCallavailable = false
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

    private fun WeeklyChallanges(PageNo: String, PagePerlimit: String) {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!
            params[Constants.paramKey_Usertype] =
                GlobalUsage.userInfoModel.data!!.usertype!!
            params[Constants.paramKey_PageNo] = PageNo
            params[Constants.paramKey_limit] = PagePerlimit

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.WeeklyChallangeService>(
                    NetworkServices.WeeklyChallangeService::class.java
                )
            val call =
                service.WeeklyChallangeData(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<ChallangesModel> {
                override fun onFailure(call: Call<ChallangesModel>, t: Throwable) {
                    DB_Challenges.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ChallangesModel>,
                    response: Response<ChallangesModel>
                ) {
                    DB_Challenges.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        weeklyChallengesList.addAll(response.body()!!.data!!)
                        weeklyframesAdapter()
                        weeklyChallangeCallavailable =
                            response.body()!!.data!!.size >= this@Challenges.PagePerlimit
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_Challenges.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {
                        weeklyChallangeCallavailable = false
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

    private fun MonthlyChallanges(PageNo: String, PagePerlimit: String) {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid!!
            params[Constants.paramKey_Usertype] =
                GlobalUsage.userInfoModel.data!!.usertype!!
            params[Constants.paramKey_PageNo] = PageNo
            params[Constants.paramKey_limit] = PagePerlimit

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.MonthlyChallangeService>(
                    NetworkServices.MonthlyChallangeService::class.java
                )
            val call =
                service.MonthlyChallangeData(
                    params, GlobalUsage.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<ChallangesModel> {
                override fun onFailure(call: Call<ChallangesModel>, t: Throwable) {
                    DB_Challenges.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ChallangesModel>,
                    response: Response<ChallangesModel>
                ) {
                    Log.e("TAG","Monthlyresponse : "+Gson().toJson(response.body()!!))
                    DB_Challenges.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        monthlyChallengesList.addAll(response.body()!!.data!!)
                        monthlyframesAdapter()
                        monthlyChallangeCallavailable =
                            response.body()!!.data!!.size >= this@Challenges.PagePerlimit
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_Challenges.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {
                        monthlyChallangeCallavailable = false
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


    fun dailyframesAdapter() {
        try {

            if (dailyChallengesList.isNullOrEmpty()) {
                DB_Challenges.txtNoChallenges.visibility = View.VISIBLE
            } else {
                DB_Challenges.txtNoChallenges.visibility = View.GONE
            }

            DB_Challenges.rrChallengesDaily.adapter = ChallengesAdapter(
                this, dailyChallengesList
            )


            DB_Challenges.rrChallengesDaily.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 5 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached) {
                        //you have reached to the bottom of your recycler view
                    }
                    if ((totalItemCount - 1) == lastVisible && dailyChallangeCallavailable && dataSize == PagePerlimit) {
                        dailyChallangeCallavailable = true
                        DailyChallanges((++dayPageNo).toString(), PagePerlimit.toString())
                    }
                }
            })
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

    fun weeklyframesAdapter() {
        try {

            if (weeklyChallengesList.isNullOrEmpty()) {
                DB_Challenges.txtNoChallenges.visibility = View.VISIBLE
            } else {
                DB_Challenges.txtNoChallenges.visibility = View.GONE
            }

            DB_Challenges.rrChallengesWeekly.adapter = ChallengesAdapter(
                this, weeklyChallengesList
            )


            DB_Challenges.rrChallengesWeekly.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 5 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached) {
                        //you have reached to the bottom of your recycler view
                    }
                    if ((totalItemCount - 1) == lastVisible && weeklyChallangeCallavailable && dataSize == PagePerlimit) {
                        weeklyChallangeCallavailable = true
                        WeeklyChallanges((++weekPageNo).toString(), PagePerlimit.toString())
                    }
                }
            })
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

    fun monthlyframesAdapter() {
        try {

            if (monthlyChallengesList.isNullOrEmpty()) {
                DB_Challenges.txtNoChallenges.visibility = View.VISIBLE
            } else {
                DB_Challenges.txtNoChallenges.visibility = View.GONE
            }

            DB_Challenges.rrChallengesMonthly.adapter = ChallengesAdapter(
                this, monthlyChallengesList
            )


            DB_Challenges.rrChallengesMonthly.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 5 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached) {
                        //you have reached to the bottom of your recycler view
                    }
                    if ((totalItemCount - 1) == lastVisible && monthlyChallangeCallavailable && dataSize == PagePerlimit) {
                        monthlyChallangeCallavailable = true
                        MonthlyChallanges((++monthPageNo).toString(), PagePerlimit.toString())
                    }
                }
            })
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
                if (view == DB_Challenges.imgBack) {
                    onBackPressed()
                } else if (view == DB_Challenges.txtDaily) {
                    DB_Challenges.txtChallangesLbl.setText(getString(R.string.challangedaily))
                    UncheckSelection()
                    setUpDailyList()
                    DB_Challenges.rrChallengesDaily.visibility = View.VISIBLE
                    DB_Challenges.txtDaily.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.blue_dark
                        )
                    )
                    DB_Challenges.llDaily.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.blue_darklight
                        )
                    )

                } else if (view == DB_Challenges.txtWeekly) {
                    DB_Challenges.txtChallangesLbl.setText(getString(R.string.challangemonthly))
                    UncheckSelection()
                    setUpWeeklyList()
                    DB_Challenges.rrChallengesWeekly.visibility = View.VISIBLE

                    DB_Challenges.txtWeekly.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.blue_dark
                        )
                    )
                    DB_Challenges.llWeekly.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.blue_darklight
                        )
                    )

                } else if (view == DB_Challenges.txtMonthly) {
                    DB_Challenges.txtChallangesLbl.setText(getString(R.string.challangemonthly))
                    UncheckSelection()
                    setUpMonthlyList()
                    DB_Challenges.rrChallengesMonthly.visibility = View.VISIBLE
                    DB_Challenges.txtMonthly.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.blue_dark
                        )
                    )
                    DB_Challenges.llMonthly.setBackgroundColor(
                        ContextCompat.getColor(
                            this,
                            R.color.blue_darklight
                        )
                    )


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

    private fun setUpMonthlyList() {
        if (monthlyChallengesList.isNullOrEmpty()) {
            DB_Challenges.txtNoChallenges.visibility = View.VISIBLE
        } else {
            DB_Challenges.txtNoChallenges.visibility = View.GONE
        }
    }

    private fun setUpWeeklyList() {
        if (weeklyChallengesList.isNullOrEmpty()) {
            DB_Challenges.txtNoChallenges.visibility = View.VISIBLE
        } else {
            DB_Challenges.txtNoChallenges.visibility = View.GONE
        }
    }

    private fun setUpDailyList() {
        if (dailyChallengesList.isNullOrEmpty()) {
            DB_Challenges.txtNoChallenges.visibility = View.VISIBLE
        } else {
            DB_Challenges.txtNoChallenges.visibility = View.GONE
        }
    }

    private fun UncheckSelection() {
        DB_Challenges.rrChallengesDaily.visibility = View.GONE
        DB_Challenges.rrChallengesWeekly.visibility = View.GONE
        DB_Challenges.rrChallengesMonthly.visibility = View.GONE
        DB_Challenges.txtDaily.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        DB_Challenges.llDaily.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.transparent
            )
        )
        DB_Challenges.txtWeekly.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        DB_Challenges.llWeekly.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.transparent
            )
        )
        DB_Challenges.txtMonthly.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        DB_Challenges.llMonthly.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.transparent
            )
        )
    }
}
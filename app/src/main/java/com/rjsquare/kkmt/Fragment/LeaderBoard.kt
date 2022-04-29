package com.rjsquare.kkmt.Fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Challenges.Challenges
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.Dialog.Network
import com.rjsquare.kkmt.Activity.Dialog.UnAuthorized
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Activity.PrizesList.Prizes
import com.rjsquare.kkmt.Adapter.LeaderboardCustomerAdapter
import com.rjsquare.kkmt.Adapter.LeaderboardEmployeeAdapter
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.Leaderboard.EmpInfo
import com.rjsquare.kkmt.RetrofitInstance.Leaderboard.LeaderboardCustomer_Model
import com.rjsquare.kkmt.RetrofitInstance.Leaderboard.LeaderboardEmployee_Model
import com.rjsquare.kkmt.RetrofitInstance.Leaderboard.UserData
import com.rjsquare.kkmt.databinding.FragmentLeaderBoardBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeaderBoard : Fragment(), View.OnClickListener {

    lateinit var mLeaderBoardModel: UserData.CustomerInfo
    lateinit var mArray_LeaderBoardModel: ArrayList<UserData.CustomerInfo>
    lateinit var mLeaderBoardEmployeeOverallModel: ArrayList<EmpInfo>
    lateinit var mLeaderBoardEmployeeCurrentModel: ArrayList<EmpInfo>

    lateinit var DB_LeaderBoard: FragmentLeaderBoardBinding
    lateinit var mLeaderboardCustomer_Model: LeaderboardCustomer_Model
    lateinit var mLeaderboardEmployee_Model: LeaderboardEmployee_Model
    var IsCurrentBussiness = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DB_LeaderBoard =
            DataBindingUtil.inflate(inflater, R.layout.fragment_leader_board, container, false)
//        var rootView = inflater.inflate(R.layout.fragment_leader_board, container, false)
        try {
            Log.e("TAG", "CHECKEMPLOYEE : " + GlobalUsage.isUserEmployee)
            if (GlobalUsage.userLogedIn) {
                if (!GlobalUsage.IsNetworkAvailable(requireActivity())) {
                    Network.showDialog(requireActivity())
                } else {
                    if (GlobalUsage.isUserEmployee) {
                        DB_LeaderBoard.cntEmporuser.visibility = View.GONE
                        IsCurrentBussiness = false
                        LeaderBoardDataEmployee()
                    } else {
                        LeaderBoardDataCustomer()
                        DB_LeaderBoard.cntEmporuser.visibility = View.GONE
                    }
                }
            }

            DB_LeaderBoard.txtEmployee.setOnClickListener(this)
            DB_LeaderBoard.txtUser.setOnClickListener(this)


            DB_LeaderBoard.crdPrizes.setOnClickListener(this)
            DB_LeaderBoard.crdChallenges.setOnClickListener(this)
            leaderBoardView = true
            mLeaderboardCustomer_Model = LeaderboardCustomer_Model()
            Log.e("TAG","LoaderXX:leaderBoardView")
//            FillData()
//            framesAdapter()

            HomeActivity.HideLoader()
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
        return DB_LeaderBoard.root
    }

//    private fun FillData() {
//        try {
//            mArray_LeaderBoardModel = ArrayList()
//            mLeaderBoardModel = UserData.CustomerInfo()
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//            mArray_LeaderBoardModel.add(mLeaderBoardModel)
//        } catch (NE: NullPointerException) {
//            NE.printStackTrace()
//        } catch (IE: IndexOutOfBoundsException) {
//            IE.printStackTrace()
//        } catch (AE: ActivityNotFoundException) {
//            AE.printStackTrace()
//        } catch (E: IllegalArgumentException) {
//            E.printStackTrace()
//        } catch (RE: RuntimeException) {
//            RE.printStackTrace()
//        } catch (E: Exception) {
//            E.printStackTrace()
//        }
//    }

//    fun framesAdapter() {
//        try {
//
////            if (mArray_LeaderBoardModel != null && mArray_LeaderBoardModel.size > 0) {
////                mTxtNoNotifications.visibility = View.GONE
////            } else {
////                mTxtNoNotifications.visibility = View.VISIBLE
////            }
//
//            val loLeaderBoardAdapter: LeaderboardCustomerAdapter
////                if (mHomeModelArrayList_old == null) {
//            loLeaderBoardAdapter = LeaderboardCustomerAdapter(
//                requireActivity(), mLeaderboardCustomer_Model
//            )
//
////            val linearLayoutManager =
////                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
////            mRrLeaderboard.layoutManager = linearLayoutManager
////            mRrLeaderboard.layoutManager = GridLayoutManager(requireActivity(), 1)
//            DB_LeaderBoard.rrLeaderboard.adapter = loLeaderBoardAdapter
//
//
//        } catch (NE: NullPointerException) {
//            NE.printStackTrace()
//        } catch (IE: IndexOutOfBoundsException) {
//            IE.printStackTrace()
//        } catch (AE: ActivityNotFoundException) {
//            AE.printStackTrace()
//        } catch (E: IllegalArgumentException) {
//            E.printStackTrace()
//        } catch (RE: RuntimeException) {
//            RE.printStackTrace()
//        } catch (E: Exception) {
//            E.printStackTrace()
//        }
//    }


    private fun LeaderBoardDataCustomer() {
        try {
//            Loader.showLoader(requireActivity())
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_CustomerId] =
                GlobalUsage.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.LeaderboardCustomerService>(
                    NetworkServices.LeaderboardCustomerService::class.java
                )
            val call = service.GetLeaderBoardData(
                params,
                GlobalUsage.userInfoModel.data!!.access_token!!
            )
            call.enqueue(object : Callback<LeaderboardCustomer_Model> {
                override fun onFailure(call: Call<LeaderboardCustomer_Model>, t: Throwable) {
                    Log.e("TAG", "LeaderBoardDataCustomer : t" + t)
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<LeaderboardCustomer_Model>,
                    response: Response<LeaderboardCustomer_Model>
                ) {
                    Log.e("TAG", "LeaderBoardDataCustomer : ")
                    Loader.hideLoader()
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        mLeaderboardCustomer_Model = response.body()!!
                        FillData()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        UnAuthorized.showDialog(requireActivity())
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

    private fun LeaderBoardDataEmployee() {
        try {
//            Loader.showLoader(requireActivity())
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_BussinessId] =
                GlobalUsage.userInfoModel.data!!.bussiness!!
            params[Constants.paramKey_EmployeeId] =
                GlobalUsage.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.LeaderboardEmployeeService>(
                    NetworkServices.LeaderboardEmployeeService::class.java
                )
            val call = service.GetLeaderBoardEmpData(
                params,
                GlobalUsage.userInfoModel.data!!.access_token!!
            )
            call.enqueue(object : Callback<LeaderboardEmployee_Model> {
                override fun onFailure(call: Call<LeaderboardEmployee_Model>, t: Throwable) {
                    Log.e("TAG", "LeaderBoardDataCustomer t: " + t)
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<LeaderboardEmployee_Model>,
                    response: Response<LeaderboardEmployee_Model>
                ) {
                    Log.e("TAG", "LeaderBoardDataCustomer : ")
                    Loader.hideLoader()
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        mLeaderboardEmployee_Model = response.body()!!
                        FillEmpData()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        UnAuthorized.showDialog(requireActivity())
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

    private fun FillData() {
        try {
            mArray_LeaderBoardModel = ArrayList()

            mArray_LeaderBoardModel.addAll(mLeaderboardCustomer_Model.data!!.leaderboard!!)

            framesAdapter()
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

    private fun FillEmpData() {
        try {
            mLeaderBoardEmployeeOverallModel = ArrayList()
            mLeaderBoardEmployeeCurrentModel = ArrayList()

            mLeaderBoardEmployeeOverallModel.addAll(mLeaderboardEmployee_Model.data!!.overall_business!!.overall_business_top_20!!)
            mLeaderBoardEmployeeCurrentModel.addAll(mLeaderboardEmployee_Model.data!!.current_business!!.current_business_top_20!!)

            EmpDataAdapter()

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

    private fun EmpDataAdapter() {
        var listData = ArrayList<EmpInfo>()
        var EmpModel = EmpInfo()
        if (IsCurrentBussiness) {
            listData = mLeaderBoardEmployeeCurrentModel
            EmpModel = mLeaderboardEmployee_Model.data!!.current_business!!.current_employee!![0]
        } else {
            listData = mLeaderBoardEmployeeOverallModel
            EmpModel = mLeaderboardEmployee_Model.data!!.overall_business!!.current_employee!![0]
        }
        val loLeaderboardCustomerAdapter: LeaderboardEmployeeAdapter
        loLeaderboardCustomerAdapter = LeaderboardEmployeeAdapter(
            requireActivity(), listData
        )

        DB_LeaderBoard.rrLeaderboard.adapter = loLeaderboardCustomerAdapter
        DB_LeaderBoard.txtRankOwnName.text = EmpModel.username
        DB_LeaderBoard.txtRankOwn.text = EmpModel.rank
        DB_LeaderBoard.txtRankOwnAmount.text = EmpModel.credit
        Picasso.with(requireActivity()).load(EmpModel.userimage)
            .placeholder(R.drawable.expe_logo).into(DB_LeaderBoard.imgRankOwnProfile)
        Log.e("TAG", "UserImage : " + EmpModel.userimage)
    }


    fun framesAdapter() {
        try {

//            if (mArray_LeaderBoardModel != null && mArray_LeaderBoardModel.size > 0) {
//                mTxtNoNotifications.visibility = View.GONE
//            } else {
//                mTxtNoNotifications.visibility = View.VISIBLE
//            }

            val loLeaderboardCustomerAdapter: LeaderboardCustomerAdapter
//                if (mHomeModelArrayList_old == null) {
            loLeaderboardCustomerAdapter = LeaderboardCustomerAdapter(
                requireActivity(), mArray_LeaderBoardModel
            )

            DB_LeaderBoard.rrLeaderboard.adapter = loLeaderboardCustomerAdapter

            DB_LeaderBoard.txtRankOwnName.text =
                mLeaderboardCustomer_Model.data!!.current_customer!![0].username
            DB_LeaderBoard.txtRankOwn.text =
                mLeaderboardCustomer_Model.data!!.current_customer!![0].rank
            DB_LeaderBoard.txtRankOwnAmount.text =
                mLeaderboardCustomer_Model.data!!.current_customer!![0].credit
            Picasso.with(requireActivity())
                .load(mLeaderboardCustomer_Model.data!!.current_customer!![0].userimage)
                .placeholder(R.drawable.expe_logo).into(DB_LeaderBoard.imgRankOwnProfile)
            Log.e(
                "TAG",
                "UserImage : " + mLeaderboardCustomer_Model.data!!.current_customer!![0].userimage
            )
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

    companion object {
        var leaderBoardView = false
//        lateinit var mCrdPrizes: CardView
//        lateinit var mCrdChallenges: CardView

        @JvmStatic
        fun newInstance() =
            LeaderBoard().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_LeaderBoard.crdPrizes) {
                    var PrizesIntent = Intent(activity, Prizes::class.java)
                    requireActivity().startActivity(PrizesIntent)
                    requireActivity().overridePendingTransition(
                        R.anim.activity_in,
                        R.anim.activity_out
                    )
                } else if (view == DB_LeaderBoard.crdChallenges) {
                    var ChallengesIntent = Intent(activity, Challenges::class.java)
                    requireActivity().startActivity(ChallengesIntent)
//                    requireActivity().overridePendingTransition(
//                        R.anim.activity_in,
//                        R.anim.activity_out
//                    )
                } else if (view == DB_LeaderBoard.txtEmployee) {
                    DB_LeaderBoard.txtEmployee.background =
                        ContextCompat.getDrawable(requireActivity(), R.drawable.tab_selection)
                    DB_LeaderBoard.txtUser.background = null
                    DB_LeaderBoard.txtEmployee.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.black
                        )
                    )
                    DB_LeaderBoard.txtUser.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )

                } else if (view == DB_LeaderBoard.txtUser) {
                    DB_LeaderBoard.txtUser.background =
                        ContextCompat.getDrawable(requireActivity(), R.drawable.tab_selection)
                    DB_LeaderBoard.txtEmployee.background = null
                    DB_LeaderBoard.txtEmployee.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.white
                        )
                    )
                    DB_LeaderBoard.txtUser.setTextColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.black
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
}
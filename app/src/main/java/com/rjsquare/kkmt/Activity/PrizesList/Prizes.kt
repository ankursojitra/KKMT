package com.rjsquare.kkmt.Activity.PrizesList

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Adapter.PrizesListAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.PrizeList_Model
import com.rjsquare.kkmt.databinding.ActivityPrizesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Prizes : AppCompatActivity(), View.OnClickListener {
    lateinit var mList_StoreListModel: ArrayList<PrizeList_Model.PrizeData>

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_Prizes: ActivityPrizesBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Prizes = DataBindingUtil.setContentView(this, R.layout.activity_prizes)
        try {
            ApplicationClass.StatusTextWhite(this, true)

            DB_Prizes.imgBack.setOnClickListener(this)
            DB_Prizes.txtUnauthOk.setOnClickListener(this)
            DB_Prizes.txtAlertok.setOnClickListener(this)

            GetLatestPrizes()
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

    private fun GetLatestPrizes() {
        try {
            DB_Prizes.cntLoader.visibility = View.VISIBLE
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid.toString()

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.PriceListService>(
                    NetworkServices.PriceListService::class.java
                )
            val call =
                service.PriceListData(
                    params, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<PrizeList_Model> {
                override fun onFailure(call: Call<PrizeList_Model>, t: Throwable) {
                    DB_Prizes.cntLoader.visibility = View.GONE
                    Log.e("GetResponsesasXASX", "Hell: ")
                }

                override fun onResponse(
                    call: Call<PrizeList_Model>,
                    response: Response<PrizeList_Model>
                ) {
                    Log.e("GetResponsesasXASX", "responseHell: " + response.body()!!)
                    DB_Prizes.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        mList_StoreListModel = response.body()!!.data!!
                        SetUpPrizeList()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_Prizes.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {
                        DB_Prizes.txtAlertmsg.text = response.body()!!.message
                        DB_Prizes.cntAlert.visibility = View.VISIBLE
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

    private fun SetUpPrizeList() {
        framesAdapter()
    }

    fun framesAdapter() {
        try {

            if (mList_StoreListModel != null && mList_StoreListModel.size > 0) {
                DB_Prizes.txtNoPrizes.visibility = View.GONE
            } else {
                DB_Prizes.txtNoPrizes.visibility = View.VISIBLE
            }
            val loPrizesListAdapter: PrizesListAdapter
            loPrizesListAdapter = PrizesListAdapter(
                this, mList_StoreListModel
            )
            DB_Prizes.rrPrizeList.adapter = loPrizesListAdapter
            DB_Prizes.rrPrizeList.setItemViewCacheSize(mList_StoreListModel.size)

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
            if (System.currentTimeMillis() < ApplicationClass.lastClick) return else {
                ApplicationClass.lastClick =
                    System.currentTimeMillis() + ApplicationClass.clickInterval
                if (view == DB_Prizes.imgBack) {
                    onBackPressed()
                } else if (view == DB_Prizes.txtUnauthOk) {
                    DB_Prizes.cntUnAuthorized.visibility = View.GONE
                    ApplicationClass.UserLogout(this)
                } else if (view == DB_Prizes.txtAlertok) {
                    DB_Prizes.cntAlert.visibility = View.GONE
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
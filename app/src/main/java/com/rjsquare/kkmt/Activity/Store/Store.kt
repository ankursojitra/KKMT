package com.rjsquare.kkmt.Activity.Store

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Alert
import com.rjsquare.kkmt.Activity.Dialog.Loader
import com.rjsquare.kkmt.Activity.Dialog.Network
import com.rjsquare.kkmt.Activity.Dialog.UnAuthorized
import com.rjsquare.kkmt.Adapter.StoreListAdapter

import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.StoreList_Model
import com.rjsquare.kkmt.databinding.ActivityStoreBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Store : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var thisStoreActivity: Activity
        lateinit var DB_Store: ActivityStoreBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Store = DataBindingUtil.setContentView(this, R.layout.activity_store)
        try {
            GlobalUsage.StatusTextWhite(this, true)
            thisStoreActivity = this
            DB_Store.imgBack.setOnClickListener(this)
            if (!GlobalUsage.IsNetworkAvailable(this)) {
                Network.showDialog(this)
                return
            }
            StoreListData()
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

    private fun StoreListData() {
        try {
            Loader.showLoader(this)
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            var UserID = ""
            var Token = ""
            if (GlobalUsage.userInfoModel != null) {
                UserID = GlobalUsage.userInfoModel.data!!.userid!!.toString()
                Token = GlobalUsage.userInfoModel.data!!.access_token!!.toString()
            }

            params[Constants.paramKey_UserId] = UserID

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.StoreListService>(
                    NetworkServices.StoreListService::class.java
                )
            val call =
                service.StoreListData(
                    params, Token
                )

            call.enqueue(object : Callback<StoreList_Model> {
                override fun onFailure(call: Call<StoreList_Model>, t: Throwable) {
                    Loader.hideLoader()
                }

                override fun onResponse(
                    call: Call<StoreList_Model>,
                    response: Response<StoreList_Model>
                ) {
                    Loader.hideLoader()
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        GlobalUsage.mList_StoreListModel = response.body()!!.data!!
                        SetUpStoreList()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        UnAuthorized.showDialog(this@Store)
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {
                        Alert.showDialog(this@Store,response.body()!!.message!!)
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

    private fun SetUpStoreList() {
        framesAdapter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (!GlobalUsage.IsNetworkAvailable(this)) {
                Network.showDialog(this)
                return
            }
            StoreListData()
        }
    }

    fun framesAdapter() {
        try {

            if (GlobalUsage.mList_StoreListModel != null && GlobalUsage.mList_StoreListModel.size > 0) {
                DB_Store.txtNoStoredata.visibility = View.GONE
            } else {
                DB_Store.txtNoStoredata.visibility = View.VISIBLE
            }

            val loStoreListAdapter: StoreListAdapter
            loStoreListAdapter = StoreListAdapter(
                this, GlobalUsage.mList_StoreListModel
            )
            DB_Store.rrStoreList.adapter = loStoreListAdapter
            DB_Store.rrStoreList.setItemViewCacheSize(GlobalUsage.mList_StoreListModel.size)

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
                if (view == DB_Store.imgBack) {
                    onBackPressed()
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
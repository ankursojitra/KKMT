package com.rjsquare.kkmt.Activity.Store

import android.app.Activity
import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.ItemRedeem_Model
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.PickUpLocation_Model
import com.rjsquare.kkmt.databinding.ActivityStoreItemRedeemConfirmBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreItemRedeemConfirm : AppCompatActivity(), View.OnClickListener {

    lateinit var LocationList: ArrayList<PickUpLocation_Model.LocationData>
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_StoreItemRedeemConfirm: ActivityStoreItemRedeemConfirmBinding
        var DeliveryAddress = ""
        var IsPickUpStore = false
        var ItemRedeemed = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_StoreItemRedeemConfirm =
            DataBindingUtil.setContentView(this, R.layout.activity_store_item_redeem_confirm)
        try {
            ApplicationClass.StatusTextWhite(this, true)

            DB_StoreItemRedeemConfirm.chDeliveryLocation.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickupLocation.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked = true
                    }
                } else {
                    IsPickUpStore = false
                    DB_StoreItemRedeemConfirm.chPickupLocation.isChecked = false
                    SetUi()
                }
            }
            DB_StoreItemRedeemConfirm.chPickupLocation.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickupLocation.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chPickupLocation.isChecked = true
                    }
                } else {
                    IsPickUpStore = true
                    if (DB_StoreItemRedeemConfirm.chPickUp1.isChecked) {
                        DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp1.text.toString()
                    } else if (DB_StoreItemRedeemConfirm.chPickUp2.isChecked) {
                        DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp2.text.toString()
                    } else if (DB_StoreItemRedeemConfirm.chPickUp3.isChecked) {
                        DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp3.text.toString()
                    }
                    DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked = false
                    SetUi()
                }
            }
            DB_StoreItemRedeemConfirm.chPickUp1.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chPickUp1.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp2.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp3.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chPickUp1.isChecked = true
                    }
                } else {
                    DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp1.text.toString()
                    DB_StoreItemRedeemConfirm.chPickUp2.isChecked = false
                    DB_StoreItemRedeemConfirm.chPickUp3.isChecked = false
                }
            }
            DB_StoreItemRedeemConfirm.chPickUp2.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chPickUp1.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp2.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp3.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chPickUp2.isChecked = true
                    }
                } else {
                    DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp2.text.toString()
                    DB_StoreItemRedeemConfirm.chPickUp1.isChecked = false
                    DB_StoreItemRedeemConfirm.chPickUp3.isChecked = false
                }
            }
            DB_StoreItemRedeemConfirm.chPickUp3.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    if (DB_StoreItemRedeemConfirm.chPickUp1.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp2.isChecked == false &&
                        DB_StoreItemRedeemConfirm.chPickUp3.isChecked == false
                    ) {
                        DB_StoreItemRedeemConfirm.chPickUp3.isChecked = true
                    }
                } else {
                    DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp3.text.toString()
                    DB_StoreItemRedeemConfirm.chPickUp2.isChecked = false
                    DB_StoreItemRedeemConfirm.chPickUp1.isChecked = false
                }
            }
            DB_StoreItemRedeemConfirm.edtPincode.setOnEditorActionListener(object :
                TextView.OnEditorActionListener {
                override fun onEditorAction(v: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
//                        var imm = getSystemService(
//                            Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        ApplicationClass.HiddenKeyBoard(
                            this@StoreItemRedeemConfirm,
                            DB_StoreItemRedeemConfirm.cntTopView
                        )
                        return true
                    }
                    return false
                }

            })
            DB_StoreItemRedeemConfirm.chPickupLocation.isChecked = true
            DB_StoreItemRedeemConfirm.chPickUp1.isChecked = true

            DB_StoreItemRedeemConfirm.cntItemredeemSubmit.setOnClickListener(this)
            DB_StoreItemRedeemConfirm.cntRedeemConfirm.setOnClickListener(this)
            DB_StoreItemRedeemConfirm.cntRedeemCancel.setOnClickListener(this)
            DB_StoreItemRedeemConfirm.imgBack.setOnClickListener(this)
            DB_StoreItemRedeemConfirm.txtAlertok.setOnClickListener(this)
            DB_StoreItemRedeemConfirm.txtUnauthOk.setOnClickListener(this)
            SetUpItemData()
            LocationList = ArrayList()
            GetPickUpLocation()
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

    private fun SetUpItemData() {
        StoreLevelList.selectedStoreItemModel
        Picasso.with(this).load(StoreLevelList.selectedStoreItemModel.image!![0])
            .placeholder(R.drawable.expe_logo).into(
                DB_StoreItemRedeemConfirm.imgRedeemItem
            )
        DB_StoreItemRedeemConfirm.txtRedeemItemName.text =
            StoreLevelList.selectedStoreItemModel.title
        DB_StoreItemRedeemConfirm.txtRedeemItemCredit.text =
            StoreLevelList.selectedStoreItemModel.credit_required
    }

    private fun GetPickUpLocation() {
        try {
            DB_StoreItemRedeemConfirm.cntLoader.visibility = View.VISIBLE
            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.PickUpLocationService>(
                    NetworkServices.PickUpLocationService::class.java
                )
            val call =
                service.GetPickUpLocationData()

            call.enqueue(object : Callback<PickUpLocation_Model> {
                override fun onFailure(call: Call<PickUpLocation_Model>, t: Throwable) {
                    DB_StoreItemRedeemConfirm.cntLoader.visibility = View.GONE
                    Log.e("GetResponsesasXASX", "Hell: ")
                }

                override fun onResponse(
                    call: Call<PickUpLocation_Model>,
                    response: Response<PickUpLocation_Model>
                ) {
                    DB_StoreItemRedeemConfirm.cntLoader.visibility = View.GONE

//                    Log.e("GetResponsesasXASX", "Hell: "+Gson().toJson(response.body()!!))
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        LocationList.addAll(response.body()!!.data!!)
                        SetUpDataUI()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_StoreItemRedeemConfirm.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {
                        DB_StoreItemRedeemConfirm.txtAlertmsg.text = response.body()!!.message
                        DB_StoreItemRedeemConfirm.cntAlert.visibility = View.VISIBLE
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

    private fun ItemRedeemRequest() {
        try {
            DB_StoreItemRedeemConfirm.cntLoader.visibility = View.VISIBLE
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!.toString()

            params[Constants.paramKey_ItemId] = StoreLevelList.selectedStoreItemModel.id!!
            params[Constants.paramKey_ItemCredit] =
                StoreLevelList.selectedStoreItemModel.credit_required!!
            params[Constants.paramKey_LocationType] = GetLocation()
            params[Constants.paramKey_Location] = DeliveryAddress

            DB_StoreItemRedeemConfirm.cntLoader.visibility = View.VISIBLE
            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.ItemRedeemService>(
                    NetworkServices.ItemRedeemService::class.java
                )
            val call =
                service.ItemRedeemData(
                    params,
                    ApplicationClass.userInfoModel.data!!.access_token!!.toString()
                )

            call.enqueue(object : Callback<ItemRedeem_Model> {
                override fun onFailure(call: Call<ItemRedeem_Model>, t: Throwable) {
                    DB_StoreItemRedeemConfirm.cntLoader.visibility = View.GONE
                    Log.e("GetResponsesasXASX", "Hell: " + t)
                }

                override fun onResponse(
                    call: Call<ItemRedeem_Model>,
                    response: Response<ItemRedeem_Model>
                ) {
                    Log.e("GetResponsesasXASX", "Hell: " + Gson().toJson(response.body()!!))
                    DB_StoreItemRedeemConfirm.cntLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        ItemRedeemed = true
                        DB_StoreItemRedeemConfirm.txtAlertmsg.text = response.body()!!.message
                        DB_StoreItemRedeemConfirm.cntAlert.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_StoreItemRedeemConfirm.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {
                        DB_StoreItemRedeemConfirm.txtAlertmsg.text = response.body()!!.message
                        DB_StoreItemRedeemConfirm.cntAlert.visibility = View.VISIBLE
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

    private fun GetLocation(): String {
        if (IsPickUpStore) {
            return "P"
        } else {
            return "D"
        }
    }

    private fun SetUpDataUI() {
        DB_StoreItemRedeemConfirm.chPickUp1.text = LocationList[0].location
        DB_StoreItemRedeemConfirm.chPickUp2.text = LocationList[1].location
        DB_StoreItemRedeemConfirm.chPickUp3.text = LocationList[2].location

        IsPickUpStore = true
        DeliveryAddress = DB_StoreItemRedeemConfirm.chPickUp1.text.toString()

    }

    private fun SetUi() {
        if (DB_StoreItemRedeemConfirm.chDeliveryLocation.isChecked) {
            DB_StoreItemRedeemConfirm.cntDeliveryLocation.visibility = View.VISIBLE
            DB_StoreItemRedeemConfirm.cntPickUpLocation.visibility = View.GONE
        } else if (DB_StoreItemRedeemConfirm.chPickupLocation.isChecked) {
            DB_StoreItemRedeemConfirm.cntDeliveryLocation.visibility = View.GONE
            DB_StoreItemRedeemConfirm.cntPickUpLocation.visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View?) {
        if (System.currentTimeMillis() < ApplicationClass.lastClick) return else {
            ApplicationClass.lastClick = System.currentTimeMillis() + ApplicationClass.clickInterval
            if (view == DB_StoreItemRedeemConfirm.cntItemredeemSubmit) {
                if (!IsPickUpStore) {
                    if (!DB_StoreItemRedeemConfirm.edtAddress1.text.toString().trim()
                            .equals("", true)
                    ) {
                        if (!DB_StoreItemRedeemConfirm.edtAddress2.text.toString().trim()
                                .equals("", true)
                        ) {
                            DeliveryAddress =
                                DB_StoreItemRedeemConfirm.edtAddress1.text.toString() +
                                        ", " + DB_StoreItemRedeemConfirm.edtAddress2.text.toString() +
                                        ", " + DB_StoreItemRedeemConfirm.edtPincode.text.toString()
                        } else {
                            DeliveryAddress =
                                DB_StoreItemRedeemConfirm.edtAddress1.text.toString() +
                                        ", " + DB_StoreItemRedeemConfirm.edtPincode.text.toString()
                        }
                    }
                }
                if (!IsPickUpStore && DB_StoreItemRedeemConfirm.edtAddress1.text.toString().trim()
                        .equals("", true) &&
                    DB_StoreItemRedeemConfirm.edtPincode.text.toString().trim()
                        .equals("", true)
                ) {
                    Toast.makeText(this, "Enter address.", Toast.LENGTH_SHORT).show()
                    return
                }
                SetUpConfirmUI()
                DB_StoreItemRedeemConfirm.cntConfirmation.visibility = View.VISIBLE
            } else if (view == DB_StoreItemRedeemConfirm.cntRedeemConfirm) {
                DB_StoreItemRedeemConfirm.cntConfirmation.visibility = View.GONE
                ItemRedeemRequest()
            } else if (view == DB_StoreItemRedeemConfirm.cntRedeemCancel) {
                DB_StoreItemRedeemConfirm.cntConfirmation.visibility = View.GONE
            } else if (view == DB_StoreItemRedeemConfirm.imgBack) {
                onBackPressed()
            } else if (view == DB_StoreItemRedeemConfirm.txtUnauthOk) {
                ApplicationClass.UserLogout(this)
            } else if (view == DB_StoreItemRedeemConfirm.txtAlertok) {
                if (ItemRedeemed) {
//                Store.thisStoreActivity.finish()
                    setResult(Activity.RESULT_OK)
                    StoreLevelList.thisStoreLevelActivity.finish()
                    finish()
                    overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
                } else {
                    DB_StoreItemRedeemConfirm.cntAlert.visibility = View.GONE
                }
            }
        }
    }

    private fun SetUpConfirmUI() {
        if (IsPickUpStore) {
            DB_StoreItemRedeemConfirm.txtRedeemaddressSel.text =
                DB_StoreItemRedeemConfirm.chPickupLocation.text.toString()
        } else {
            DB_StoreItemRedeemConfirm.txtRedeemaddressSel.text =
                DB_StoreItemRedeemConfirm.chDeliveryLocation.text.toString()
        }
        DB_StoreItemRedeemConfirm.txtRedeemaddressLbl.text = DeliveryAddress

        Picasso.with(this).load(StoreLevelList.selectedStoreItemModel.image!![0]).into(
            DB_StoreItemRedeemConfirm.imgRedeemItemConfirm
        )
        DB_StoreItemRedeemConfirm.txtRedeemItemNameConfirm.text =
            StoreLevelList.selectedStoreItemModel.title!!
        DB_StoreItemRedeemConfirm.txtRedeemItemCreditConfirm.text =
            StoreLevelList.selectedStoreItemModel.credit_required


    }
}
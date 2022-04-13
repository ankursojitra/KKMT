package com.rjsquare.kkmt.Activity.Store

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.function
import com.rjsquare.kkmt.Adapter.StoreLevelItemDetailAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.mStoreLevelListModelSelected
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.StoreList_Model
import com.rjsquare.kkmt.databinding.ActivityStoreLevelListBinding

class StoreLevelList : AppCompatActivity(), View.OnClickListener {
    lateinit var mList_StoreItemDetailModel: ArrayList<StoreList_Model.StoreItemData.StoreItem>
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }


    companion object {
        lateinit var thisStoreLevelActivity: Activity
        lateinit var DB_StoreLevelList: ActivityStoreLevelListBinding
        lateinit var selectedStoreItemModel: StoreList_Model.StoreItemData.StoreItem
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_StoreLevelList = DataBindingUtil.setContentView(this, R.layout.activity_store_level_list)

        try {
            thisStoreLevelActivity = this
            ApplicationClass.StatusTextWhite(this, true)

            DB_StoreLevelList.imgBack.setOnClickListener(this)

            DB_StoreLevelList.txtTitle.text = "Level " + mStoreLevelListModelSelected!!.level

            mList_StoreItemDetailModel = mStoreLevelListModelSelected!!.store_item!!

            framesAdapter()

            DB_StoreLevelList.cntRedeemConfirm.setOnClickListener(this)
            DB_StoreLevelList.cntRedeemCancel.setOnClickListener(this)

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

    fun framesAdapter() {
        try {

            if (mList_StoreItemDetailModel != null && mList_StoreItemDetailModel.size > 0) {
                DB_StoreLevelList.txtNoStoredata.visibility = View.GONE
            } else {
                DB_StoreLevelList.txtNoStoredata.visibility = View.VISIBLE
            }

            val loStoreLevelItemDetailAdapter: StoreLevelItemDetailAdapter
            loStoreLevelItemDetailAdapter = StoreLevelItemDetailAdapter(
                this, mList_StoreItemDetailModel
            )

            DB_StoreLevelList.rrStoreList.adapter = loStoreLevelItemDetailAdapter


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
            if (view == DB_StoreLevelList.imgBack) {
                onBackPressed()
            } else if (view == DB_StoreLevelList.cntRedeemCancel) {
                DB_StoreLevelList.cntConfirmation.visibility = View.GONE
            } else if (view == DB_StoreLevelList.cntRedeemConfirm) {
                DB_StoreLevelList.cntConfirmation.visibility = View.GONE
                function.NextScreen(this, Intent(this, StoreItemRedeemConfirm::class.java))
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
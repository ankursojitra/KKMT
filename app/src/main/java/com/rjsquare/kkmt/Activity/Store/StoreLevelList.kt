package com.rjsquare.kkmt.Activity.Store

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Adapter.StoreLevelItemDetailAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.mStoreLevelListModelSelected
import com.rjsquare.kkmt.Model.StoreItemDetailModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityStoreLevelListBinding

class StoreLevelList : AppCompatActivity(), View.OnClickListener {
//    private lateinit var mTxtNoStoredata: TextView
//    private lateinit var mTxtTitle: TextView
//    private lateinit var mImgBack: ImageView
//    private lateinit var mRrStoreList: RecyclerView
lateinit var mList_StoreItemDetailModel: ArrayList<StoreItemDetailModel>


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object{
        lateinit var DB_StoreLevelList:ActivityStoreLevelListBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_StoreLevelList = DataBindingUtil.setContentView(this,R.layout.activity_store_level_list)

        try {
            ApplicationClass.StatusTextWhite(this, true)

//            mTxtTitle = findViewById<TextView>(R.id.txt_title)
//            mImgBack = findViewById<ImageView>(R.id.img_back)
//            mRrStoreList = findViewById<RecyclerView>(R.id.rr_storeList)
//            mTxtNoStoredata = findViewById<TextView>(R.id.txt_no_storedata)
            DB_StoreLevelList.imgBack.setOnClickListener(this)

            DB_StoreLevelList.txtTitle.text = mStoreLevelListModelSelected!!.StoreLeveltag

            mList_StoreItemDetailModel = mStoreLevelListModelSelected!!.List_StoreItemDetailModel

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
//                if (mHomeModelArrayList_old == null) {
            loStoreLevelItemDetailAdapter = StoreLevelItemDetailAdapter(
                this, mList_StoreItemDetailModel
            )

//            val linearLayoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//            DB_StoreLevelList.rrStoreList.setLayoutManager(linearLayoutManager)
//            DB_StoreLevelList.rrStoreList.setLayoutManager(GridLayoutManager(this, 3))
            DB_StoreLevelList.rrStoreList.setAdapter(loStoreLevelItemDetailAdapter)


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
                var StoreItemRedeemintent = Intent(this, StoreItemRedeemConfirm::class.java)
                startActivity(StoreItemRedeemintent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
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
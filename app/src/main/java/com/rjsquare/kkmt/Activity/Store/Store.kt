package com.rjsquare.kkmt.Activity.Store

import android.app.Activity
import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Adapter.StoreListAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.mList_StoreItemDetailModel
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.mList_StoreListModel
import com.rjsquare.kkmt.Model.StoreItemDetailModel
import com.rjsquare.kkmt.Model.StoreListModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityStoreBinding

class Store : AppCompatActivity(), View.OnClickListener {

//    private lateinit var mImgBack: ImageView

    lateinit var mStoreListModel: StoreListModel
    lateinit var mStoreItemDetailModel: StoreItemDetailModel
//    private lateinit var mTxtNoStoredata: TextView
//    private lateinit var mRrStoreList: RecyclerView


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var thisStoreActivity : Activity
        lateinit var DB_Store: ActivityStoreBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Store = DataBindingUtil.setContentView(this, R.layout.activity_store)
//        setContentView(R.layout.activity_store)

        try {
            ApplicationClass.StatusTextWhite(this, true)
            thisStoreActivity = this
//            mImgBack = findViewById<ImageView>(R.id.img_back)
//            mRrStoreList = findViewById<RecyclerView>(R.id.rr_storeList)
//            mTxtNoStoredata = findViewById<TextView>(R.id.txt_no_storedata)


            DB_Store.imgBack.setOnClickListener(this)
            fillPrizes_Data()
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

    private fun fillPrizes_Data() {
        try {
            mList_StoreListModel = ArrayList()

            //1st
            mList_StoreItemDetailModel = ArrayList()
            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Samsung Watch"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop1)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Apple Ipad pro"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop2)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "KTM Maisto"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop3)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "KTM Maisto"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop3)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreListModel = StoreListModel()
            mStoreListModel.StoreLeveltag = "Level 1"
            mStoreListModel.List_StoreItemDetailModel = mList_StoreItemDetailModel
            mList_StoreListModel.add(mStoreListModel)

            //2nd
            mList_StoreItemDetailModel = ArrayList()
            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Mi TV"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop4)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Nike Airfight"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop5)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Generic A1"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop6)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Nike Airfight"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop5)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Generic A1"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop6)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreListModel = StoreListModel()
            mStoreListModel.StoreLeveltag = "Level 2"
            mStoreListModel.List_StoreItemDetailModel = mList_StoreItemDetailModel
            mList_StoreListModel.add(mStoreListModel)

            //3rd
            mList_StoreItemDetailModel = ArrayList()
            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Samsung Watch"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop1)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Apple Ipad pro"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop2)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "KTM Maisto"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop3)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mList_StoreItemDetailModel = ArrayList()
            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Samsung Watch"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop1)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Apple Ipad pro"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop2)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "KTM Maisto"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop3)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreListModel = StoreListModel()
            mStoreListModel.StoreLeveltag = "Level 3"
            mStoreListModel.List_StoreItemDetailModel = mList_StoreItemDetailModel
            mList_StoreListModel.add(mStoreListModel)

            //4th
            mList_StoreItemDetailModel = ArrayList()
            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Mi TV"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop4)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Nike Airfight"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop5)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Generic A1"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop6)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Nike Airfight"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop5)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreItemDetailModel = StoreItemDetailModel()
            mStoreItemDetailModel.ItemName = "Generic A1"
            mStoreItemDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop6)!!
            mList_StoreItemDetailModel.add(mStoreItemDetailModel)

            mStoreListModel = StoreListModel()
            mStoreListModel.StoreLeveltag = "Level 4"
            mStoreListModel.List_StoreItemDetailModel = mList_StoreItemDetailModel
            mList_StoreListModel.add(mStoreListModel)

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

    fun framesAdapter() {
        try {


            if (mList_StoreListModel != null && mList_StoreListModel.size > 0) {
                DB_Store.txtNoStoredata.visibility = View.GONE
            } else {
                DB_Store.txtNoStoredata.visibility = View.VISIBLE
            }

            val loStoreListAdapter: StoreListAdapter
//                if (mHomeModelArrayList_old == null) {
            loStoreListAdapter = StoreListAdapter(
                this, mList_StoreListModel
            )

//            val linearLayoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//            mRrStoreList.setLayoutManager(linearLayoutManager)
//            mRrStoreList.setLayoutManager(GridLayoutManager(this, 1))
            DB_Store.rrStoreList.adapter = loStoreListAdapter
            DB_Store.rrStoreList.setItemViewCacheSize(mList_StoreListModel.size)

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
            if (view == DB_Store.imgBack) {
                onBackPressed()
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
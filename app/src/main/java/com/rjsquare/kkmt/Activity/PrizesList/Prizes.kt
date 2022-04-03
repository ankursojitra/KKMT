package com.rjsquare.kkmt.Activity.PrizesList

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Adapter.PrizesListAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.PrizeDetailModel
import com.rjsquare.kkmt.Model.PrizeListModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityPrizesBinding

class Prizes : AppCompatActivity(), View.OnClickListener {
    lateinit var mPrizeListModel: PrizeListModel
    lateinit var mPrizeDetailModel: PrizeDetailModel
    lateinit var mList_PrizeListModel: ArrayList<PrizeListModel>
    lateinit var mList_PrizeDetailModel: ArrayList<PrizeDetailModel>

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object{
        lateinit var DB_Prizes:ActivityPrizesBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Prizes = DataBindingUtil.setContentView(this,R.layout.activity_prizes)
//        setContentView(R.layout.activity_prizes)
        try {
            ApplicationClass.StatusTextWhite(this, true)

            DB_Prizes.imgBack.setOnClickListener(this)

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
            mList_PrizeListModel = ArrayList()

            //1st
            mList_PrizeDetailModel = ArrayList()
            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Samsung Watch"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop1)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Apple Ipad pro"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop2)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "KTM Maisto"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop3)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Samsung Watch"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop1)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeListModel = PrizeListModel()
            mPrizeListModel.Prizetag = "1st Prize"
            mPrizeListModel.List_PrizeDetailModel = mList_PrizeDetailModel
            mList_PrizeListModel.add(mPrizeListModel)

            //2nd
            mList_PrizeDetailModel = ArrayList()
            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Mi TV"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop4)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Nike Airfight"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop5)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Generic A1"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop6)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Mi TV"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop4)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeListModel = PrizeListModel()
            mPrizeListModel.Prizetag = "2nd Prize"
            mPrizeListModel.List_PrizeDetailModel = mList_PrizeDetailModel
            mList_PrizeListModel.add(mPrizeListModel)

            //3rd
            mList_PrizeDetailModel = ArrayList()
            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Samsung Watch"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop1)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Apple Ipad pro"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop2)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "KTM Maisto"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop3)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Samsung Watch"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop1)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeListModel = PrizeListModel()
            mPrizeListModel.Prizetag = "3rd Prize"
            mPrizeListModel.List_PrizeDetailModel = mList_PrizeDetailModel
            mList_PrizeListModel.add(mPrizeListModel)

            //4th
            mList_PrizeDetailModel = ArrayList()
            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Mi TV"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop4)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Nike Airfight"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop5)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Generic A1"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop6)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeDetailModel = PrizeDetailModel()
            mPrizeDetailModel.PrizeTitle = "Mi TV"
            mPrizeDetailModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.shop4)!!
            mList_PrizeDetailModel.add(mPrizeDetailModel)

            mPrizeListModel = PrizeListModel()
            mPrizeListModel.Prizetag = "4th Prize"
            mPrizeListModel.List_PrizeDetailModel = mList_PrizeDetailModel
            mList_PrizeListModel.add(mPrizeListModel)

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

            if (mList_PrizeListModel != null && mList_PrizeListModel.size > 0) {
                DB_Prizes.txtNoPrizes.visibility = View.GONE
            } else {
                DB_Prizes.txtNoPrizes.visibility = View.VISIBLE
            }
            val loPrizesListAdapter: PrizesListAdapter
//                if (mHomeModelArrayList_old == null) {
            loPrizesListAdapter = PrizesListAdapter(
                this, mList_PrizeListModel
            )

//            val linearLayoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//            mRrPrizeList.setLayoutManager(linearLayoutManager)
//            mRrPrizeList.setLayoutManager(GridLayoutManager(this, 1))
            DB_Prizes.rrPrizeList.setAdapter(loPrizesListAdapter)
            DB_Prizes.rrPrizeList.setItemViewCacheSize(mList_PrizeListModel.size)

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
            if (view == DB_Prizes.imgBack) {
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
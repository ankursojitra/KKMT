package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Model.PrizeListModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.RawPrizelistFrameBinding
import java.util.*

class PrizesListAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<PrizeListModel>
) : RecyclerView.Adapter<PrizesListAdapter.View_holder>() {
    var mPrizeListModel: PrizeListModel? = null
    companion object{
        var Width = 0
        var Height = 0
    }
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawPrizelistFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_prizelist_frame, parent, false)
//        val height = parent.measuredHeight
//        val width = parent.measuredWidth
//        Width = width
        val height = parent.measuredHeight / 10
        val width = parent.measuredWidth
        Width = width
        Height = width
//        view.layoutParams = RecyclerView.LayoutParams(width, height)
        return View_holder(binding)

//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.raw_prizelist_frame, parent, false)
////        val height = parent.measuredHeight / 10
////        val width = parent.measuredWidth
////        Width = width
////        view.layoutParams = RecyclerView.LayoutParams(width, height)
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mPrizeListModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mPrizeListModel_old: PrizeListModel
            holder.lPrizeListModelSelected = mPrizeListModel
            holder.DB_RawPrizelistFrameBinding.txtPrizetag.text = mPrizeListModel.Prizetag

            ChildframesAdapter(mPrizeListModel, holder.DB_RawPrizelistFrameBinding.rrPrize)
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


    fun ChildframesAdapter(
        mPrizeListModel: PrizeListModel,
        mRrPrize: RecyclerView
    ) {
        try {


//            if (mPrizeListModel.List_PrizeDetailModel != null && mPrizeListModel.List_PrizeDetailModel.size > 0) {
//                mTxtNoPrizes.visibility = View.GONE
//            } else {
//                mTxtNoPrizes.visibility = View.VISIBLE
//            }

            val loPrizesDetailAdapter: PrizesDetailAdapter
//                if (mHomeModelArrayList_old == null) {
            loPrizesDetailAdapter = PrizesDetailAdapter(
                moContext, mPrizeListModel.List_PrizeDetailModel
            )

            val linearLayoutManager =
                LinearLayoutManager(moContext, LinearLayoutManager.HORIZONTAL, false)
//            mRrPrize.setLayoutManager(linearLayoutManager)
//            mRrPrize.setLayoutManager(GridLayoutManager(moContext, 3))
            mRrPrize.setAdapter(loPrizesDetailAdapter)
//            mRrPrize.setItemViewCacheSize(mPrizeListModel.List_PrizeDetailModel.size)
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


    override fun getItemCount(): Int {
        return moArrayList.size
    }

    inner class View_holder(itemBinding: RawPrizelistFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        var lPrizeListModelSelected: PrizeListModel? = null
//        lateinit var mTxtPrizetag: TextView
//        lateinit var mRrPrize: RecyclerView

        lateinit var DB_RawPrizelistFrameBinding: RawPrizelistFrameBinding

        init {
            try {
                DB_RawPrizelistFrameBinding = itemBinding
//                itemBinding.root.layoutParams =  RecyclerView.LayoutParams(Width)
//                AppClass.SetLayoutWidth(DB_RawPrizelistFrameBinding.idFrameconstraint,Width)
//                view.layoutParams = RecyclerView.LayoutParams(width, height)

//                mTxtPrizetag = itemView.findViewById<TextView>(R.id.txt_prizetag)
//                mRrPrize = itemView.findViewById<RecyclerView>(R.id.rr_prize)

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

        }
    }

}
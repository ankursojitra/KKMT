package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Activity.Store.Store
import com.rjsquare.kkmt.Activity.Store.StoreLevelList
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.StoreItemDetailModel
import com.rjsquare.kkmt.Model.StoreListModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.RawStorelistFrameBinding
import java.util.*

class StoreListAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<StoreListModel>
) : RecyclerView.Adapter<StoreListAdapter.View_holder>() {
    var mStoreListModel: StoreListModel? = null
    var Width = 0
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawStorelistFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_storelist_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)

//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.raw_storelist_frame, parent, false)
////        val height = parent.measuredHeight / 10
////        val width = parent.measuredWidth
////        Width = width
////        view.layoutParams = RecyclerView.LayoutParams(width, height)
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mStoreListModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mStoreListModel_old: StoreListModel
            holder.lStoreListModelSelected = mStoreListModel
            holder.DB_RawStorelistFrameBinding.txtStorelevel.text = mStoreListModel.StoreLeveltag

            var List_StoreItem3 = ArrayList<StoreItemDetailModel>()
            List_StoreItem3.add(mStoreListModel.List_StoreItemDetailModel[0])
            List_StoreItem3.add(mStoreListModel.List_StoreItemDetailModel[1])
            List_StoreItem3.add(mStoreListModel.List_StoreItemDetailModel[2])

            ChildframesAdapter(List_StoreItem3, holder.DB_RawStorelistFrameBinding.rrStore)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun ChildframesAdapter(
        mStoreListModel: ArrayList<StoreItemDetailModel>,
        mRrPrize: RecyclerView
    ) {
        try {

            val loStoreItemDetailAdapter: StoreItemDetailAdapter
//                if (mHomeModelArrayList_old == null) {
            loStoreItemDetailAdapter = StoreItemDetailAdapter(
                moContext, mStoreListModel
            )

            val linearLayoutManager =
                LinearLayoutManager(moContext, LinearLayoutManager.HORIZONTAL, false)
            mRrPrize.setLayoutManager(linearLayoutManager)

            mRrPrize.setAdapter(loStoreItemDetailAdapter)
            mRrPrize.setItemViewCacheSize(mStoreListModel.size)
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

    inner class View_holder(itemBinding: RawStorelistFrameBinding) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        var lStoreListModelSelected: StoreListModel? = null

        lateinit var DB_RawStorelistFrameBinding: RawStorelistFrameBinding
        init {
            try {
                DB_RawStorelistFrameBinding=itemBinding

                DB_RawStorelistFrameBinding.txtMore.setOnClickListener(this)
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

        override fun onClick(view: View?) {try{
            if (view == DB_RawStorelistFrameBinding.txtMore) {
                ApplicationClass.mStoreLevelListModelSelected = lStoreListModelSelected
                var StoreLevelIntent = Intent(moContext, StoreLevelList::class.java)
                moContext.startActivity(StoreLevelIntent)
                (moContext as Store).overridePendingTransition(
                    R.anim.activity_in,
                    R.anim.activity_out
                )
            } } catch (NE: NullPointerException) {
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

}
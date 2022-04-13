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
import com.rjsquare.kkmt.Activity.function
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.StoreListModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.StoreList_Model
import com.rjsquare.kkmt.databinding.RawStorelistFrameBinding

class StoreListAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<StoreList_Model.StoreItemData>
) : RecyclerView.Adapter<StoreListAdapter.View_holder>() {
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
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mStoreListModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mStoreListModel_old: StoreListModel
            holder.lStoreListModelSelected = mStoreListModel
            holder.DB_RawStorelistFrameBinding.txtStorelevel.text = "Level " + mStoreListModel.level

            var List_StoreItem3 = ArrayList<StoreList_Model.StoreItemData.StoreItem>()
            List_StoreItem3.addAll(mStoreListModel.store_item!!)

            ChildframesAdapter(List_StoreItem3, holder.DB_RawStorelistFrameBinding.rrStore)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun ChildframesAdapter(
        mStoreListModel: ArrayList<StoreList_Model.StoreItemData.StoreItem>,
        mRrPrize: RecyclerView
    ) {
        try {

            val loStoreItemDetailAdapter: StoreItemDetailAdapter
            loStoreItemDetailAdapter = StoreItemDetailAdapter(
                moContext, mStoreListModel
            )

            val linearLayoutManager =
                LinearLayoutManager(moContext, LinearLayoutManager.HORIZONTAL, false)
            mRrPrize.layoutManager = linearLayoutManager

            mRrPrize.adapter = loStoreItemDetailAdapter
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

    inner class View_holder(itemBinding: RawStorelistFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        var lStoreListModelSelected: StoreList_Model.StoreItemData? = null

        lateinit var DB_RawStorelistFrameBinding: RawStorelistFrameBinding

        init {
            try {
                DB_RawStorelistFrameBinding = itemBinding

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

        override fun onClick(view: View?) {
            try {
                if (view == DB_RawStorelistFrameBinding.txtMore) {
                    ApplicationClass.mStoreLevelListModelSelected = lStoreListModelSelected
                    function.NextScreen(
                        moContext as Store,
                        Intent(moContext, StoreLevelList::class.java)
                    )
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

}
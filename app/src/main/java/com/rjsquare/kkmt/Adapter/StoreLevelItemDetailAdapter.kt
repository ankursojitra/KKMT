package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Activity.Store.StoreLevelList
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.StoreList_Model
import com.rjsquare.kkmt.databinding.RawStoreFrameBinding
import com.squareup.picasso.Picasso

class StoreLevelItemDetailAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<StoreList_Model.StoreItemData.StoreItem>
) : RecyclerView.Adapter<StoreLevelItemDetailAdapter.View_holder>() {
    var Width = 0
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawStoreFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_store_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)


    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mStoreItemDetailModel = moArrayList[position]
            holder.lStoreItemDetailModelSelected = mStoreItemDetailModel
            holder.DB_RawStoreFrameBinding.txtItemName.text = mStoreItemDetailModel.title
            holder.DB_RawStoreFrameBinding.txtItemcredit.text =
                mStoreItemDetailModel.credit_required
            Picasso.with(moContext).load(mStoreItemDetailModel.image!![0])
                .placeholder(R.drawable.expe_logo)
                .into(holder.DB_RawStoreFrameBinding.imgStoreitem)
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

    inner class View_holder(itemBinding: RawStoreFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        var lStoreItemDetailModelSelected: StoreList_Model.StoreItemData.StoreItem? = null

        lateinit var DB_RawStoreFrameBinding: RawStoreFrameBinding

        init {
            try {
                DB_RawStoreFrameBinding = itemBinding
//                GlobalUsage.SetLayoutWidthHeight(
//                    DB_RawStoreFrameBinding.cntStoreItem,
//                    ((Width / 12) * 4),
//                    ((Width / 12) * 5)
//                )
                DB_RawStoreFrameBinding.cntStoreItem.setOnClickListener(this)
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
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_RawStoreFrameBinding.cntStoreItem) {
                    StoreLevelList.selectedStoreItemModel = lStoreItemDetailModelSelected!!
                    Picasso.with(moContext).load(lStoreItemDetailModelSelected!!.image!![0])
                        .placeholder(R.drawable.expe_logo)
                        .into(StoreLevelList.DB_StoreLevelList.imgRedeemItem)
                    StoreLevelList.DB_StoreLevelList.txtRedeemItemName.text =
                        lStoreItemDetailModelSelected!!.title
                    StoreLevelList.DB_StoreLevelList.txtRedeemItemCredit.text =
                        lStoreItemDetailModelSelected!!.credit_required
                    StoreLevelList.DB_StoreLevelList.cntConfirmation.visibility = View.VISIBLE
                }
            }
        }
    }

}
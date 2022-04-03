package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.StoreItemDetailModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.RawStoreFrameBinding

class StoreItemDetailAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<StoreItemDetailModel>
) : RecyclerView.Adapter<StoreItemDetailAdapter.View_holder>() {
    var mStoreItemDetailModel: StoreItemDetailModel? = null
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
            var Per_Value_old = 0.0
            val mStoreItemDetailModel_old: StoreItemDetailModel
            holder.lStoreItemDetailModelSelected = mStoreItemDetailModel
            holder.DB_RawStoreFrameBinding.txtItemName.text = mStoreItemDetailModel.ItemName
            holder.DB_RawStoreFrameBinding.imgStoreitem.setImageDrawable(mStoreItemDetailModel.ImgLink)

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

        var lStoreItemDetailModelSelected: StoreItemDetailModel? = null

        lateinit var DB_RawStoreFrameBinding: RawStoreFrameBinding

        init {
            try {
                DB_RawStoreFrameBinding = itemBinding

                ApplicationClass.SetLayoutWidthHeight(
                    DB_RawStoreFrameBinding.cntStoreItem,
                    ((Width / 12) * 4),
                    ((Width / 12) * 5)
                )

//                DB_RawStoreFrameBinding.cntStoreItem.setOnClickListener(this)
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
//            if (view == DB_RawStoreFrameBinding.cntStoreItem) {
//                Store.DB_Store.cntConfirmation.visibility = View.VISIBLE
//            }
        }
    }

}
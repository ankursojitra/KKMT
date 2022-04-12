package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.PickUpLocation.PrizeList_Model
import com.rjsquare.kkmt.databinding.RawPrizeFrameBinding
import com.squareup.picasso.Picasso

class PrizesDetailAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<PrizeList_Model.PrizeData.Prize>
) : RecyclerView.Adapter<PrizesDetailAdapter.View_holder>() {
    var Width = 0
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawPrizeFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_prize_frame, parent, false)
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)

    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mPrizeDetailModel = moArrayList[position]

            holder.lStoreDetailModelSelected = mPrizeDetailModel
            holder.DB_RawPrizeFrameBinding.txtPrizeName.text = mPrizeDetailModel.title
            Picasso.with(moContext).load(mPrizeDetailModel.image!![0])
                .into(holder.DB_RawPrizeFrameBinding.imgPrize)


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

    inner class View_holder(itemBinding: RawPrizeFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        var lStoreDetailModelSelected: PrizeList_Model.PrizeData.Prize? = null

        lateinit var DB_RawPrizeFrameBinding: RawPrizeFrameBinding

        init {
            try {
                DB_RawPrizeFrameBinding = itemBinding
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
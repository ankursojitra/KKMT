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
import com.rjsquare.kkmt.databinding.RawPrizelistFrameBinding

class PrizesListAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<PrizeList_Model.PrizeData>
) : RecyclerView.Adapter<PrizesListAdapter.View_holder>() {
    companion object {
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
        val width = parent.measuredWidth
        Width = width
        Height = width
        return View_holder(binding)

    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mPrizeListModel = moArrayList[position]
            holder.lStoreListModelSelected = mPrizeListModel
            holder.DB_RawPrizelistFrameBinding.txtPrizetitle.text = mPrizeListModel.name

            ChildframesAdapter(
                mPrizeListModel.prize_list!!,
                holder.DB_RawPrizelistFrameBinding.rrPrize
            )
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
        mStoreList: ArrayList<PrizeList_Model.PrizeData.Prize>,
        mRrPrize: RecyclerView
    ) {
        try {
            val loPrizesDetailAdapter: PrizesDetailAdapter
            loPrizesDetailAdapter = PrizesDetailAdapter(
                moContext, mStoreList
            )

            mRrPrize.adapter = loPrizesDetailAdapter
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

        var lStoreListModelSelected: PrizeList_Model.PrizeData? = null
        lateinit var DB_RawPrizelistFrameBinding: RawPrizelistFrameBinding

        init {
            try {
                DB_RawPrizelistFrameBinding = itemBinding
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
package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Model.PrizeDetailModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.RawPrizeFrameBinding
import java.util.*

class PrizesDetailAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<PrizeDetailModel>
) : RecyclerView.Adapter<PrizesDetailAdapter.View_holder>() {
    var mPrizeDetailModel: PrizeDetailModel? = null
    var Width = 0
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawPrizeFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_prize_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)

//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.raw_prize_frame, parent, false)
//        val height = parent.measuredHeight / 10
//        val width = parent.measuredWidth
//        Width = width
////        view.layoutParams = RecyclerView.LayoutParams(width, height)
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mPrizeDetailModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mPrizeDetailModel_old: PrizeDetailModel
            holder.lPrizeDetailModelSelected = mPrizeDetailModel
            holder.DB_RawPrizeFrameBinding.txtPrizeName.text = mPrizeDetailModel.PrizeTitle
            holder.DB_RawPrizeFrameBinding.imgPrize.setImageDrawable(mPrizeDetailModel.ImgLink)

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
//        return moArrayList.size
        return 3
    }

    inner class View_holder(itemBinding: RawPrizeFrameBinding) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
//        lateinit var mImgPrize: ImageView
//        lateinit var mTxtPrizeName: TextView
//        private lateinit var mIdFrameconstraint: ConstraintLayout
//        lateinit var mIdFrameconstraintX: ConstraintLayout


        var lPrizeDetailModelSelected: PrizeDetailModel? = null

        lateinit var DB_RawPrizeFrameBinding: RawPrizeFrameBinding
        init {
            try {
                DB_RawPrizeFrameBinding=itemBinding
//                mImgPrize = itemView.findViewById<ImageView>(R.id.img_prize)
//                mTxtPrizeName = itemView.findViewById<TextView>(R.id.txt_prizeName)
//                mIdFrameconstraint =
//                    itemView.findViewById<ConstraintLayout>(R.id.id_frameconstraint)
//
//                mIdFrameconstraintX = itemView.findViewById<ConstraintLayout>(R.id.id_frameconstraintX)


//                AppClass.SetLayoutWidth(
//                    DB_RawPrizeFrameBinding.idFrameconstraint,
//                    (((Width / 12) * 3.9).toInt())
//                )
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
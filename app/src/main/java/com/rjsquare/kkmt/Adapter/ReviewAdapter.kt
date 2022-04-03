package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Activity.Review.ReviewDisplay
import com.rjsquare.kkmt.Activity.Review.ReviewList
import com.rjsquare.kkmt.Model.ReviewModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.RawReviewFrameBinding

class ReviewAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<ReviewModel>
) : RecyclerView.Adapter<ReviewAdapter.View_holder>() {
    var mReviewModel: ReviewModel? = null
    var Width = 0
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawReviewFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_review_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)


//        val view: View =
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.raw_review_frame, parent, false)
//        val height = parent.measuredHeight
//        val width = parent.measuredWidth
//        Width = width
////        view.layoutParams = RecyclerView.LayoutParams(width, height)
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mReviewModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mReviewModel_old: ReviewModel
            holder.lReviewModelSelected = mReviewModel


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

    inner class View_holder(itemBinding: RawReviewFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {


//        private lateinit var mIdFrameconstraint: ConstraintLayout

        var lReviewModelSelected: ReviewModel? = null
        lateinit var DB_RawReviewFrameBinding: RawReviewFrameBinding

        init {
            try {
                DB_RawReviewFrameBinding = itemBinding
//                mIdFrameconstraint =
//                    itemView.findViewById<ConstraintLayout>(R.id.id_frameconstraint)
                DB_RawReviewFrameBinding.idFrameconstraint.setOnClickListener(this)


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
                if (view == DB_RawReviewFrameBinding.idFrameconstraint) {
                    var HistoryReviewIntent = Intent(moContext, ReviewDisplay::class.java)
                    moContext.startActivity(HistoryReviewIntent)
                    (moContext as ReviewList).overridePendingTransition(
                        R.anim.activity_in,
                        R.anim.activity_out
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
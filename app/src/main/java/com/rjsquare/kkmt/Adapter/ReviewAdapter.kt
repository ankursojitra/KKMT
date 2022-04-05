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
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.ReviewModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.CustomerHistoryModel
import com.rjsquare.kkmt.databinding.RawReviewFrameBinding
import com.squareup.picasso.Picasso

class ReviewAdapter(
    var moContext: Context,
    var moArrayItemInfo: ArrayList<CustomerHistoryModel.reviewData.reviewItemInfo>
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
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mReviewModel = moArrayItemInfo[position]
            holder.lReviewModelSelected = mReviewModel

            holder.DB_RawReviewFrameBinding.txtEmpName.text = mReviewModel.username
            holder.DB_RawReviewFrameBinding.txtEmprating.text = mReviewModel.ratings
            holder.DB_RawReviewFrameBinding.txtEmpamount.text = ("$${mReviewModel.spend_amount}")
            Picasso.with(moContext).load(mReviewModel.userimage).into(holder.DB_RawReviewFrameBinding.imgEmpProfile)

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
        return moArrayItemInfo.size
    }

    inner class View_holder(itemBinding: RawReviewFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        var lReviewModelSelected: CustomerHistoryModel.reviewData.reviewItemInfo? = null
        lateinit var DB_RawReviewFrameBinding: RawReviewFrameBinding

        init {
            try {
                DB_RawReviewFrameBinding = itemBinding
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
                    ApplicationClass.empReviewModelSelected = lReviewModelSelected
                    ApplicationClass.IsNewReview = false
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
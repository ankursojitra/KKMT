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
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.ReviewInfodata
import com.rjsquare.kkmt.databinding.RawReviewFrameBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class ReviewAdapter(
    var moContext: Context,
    var moArrayItemInfo: ArrayList<ReviewInfodata>
) : RecyclerView.Adapter<ReviewAdapter.View_holder>() {
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
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val df =
                SimpleDateFormat("dd-MM-yyyy") // pass the format pattern that you like and done.

            var dateCreate = simpleDateFormat.parse(mReviewModel.created_at)

            var fDate = df.format(dateCreate)

            holder.DB_RawReviewFrameBinding.txtEmpName.text = mReviewModel.employee_name
            holder.DB_RawReviewFrameBinding.txtEmprating.text = mReviewModel.review
            holder.DB_RawReviewFrameBinding.txtEmpamount.text = ("TT$ ${GlobalUsage.formatNumber(mReviewModel.receipt_amount!!.toInt())}")
            holder.DB_RawReviewFrameBinding.txtBusname.text = ("${mReviewModel.bussinessname}")
            Picasso.with(moContext).load(mReviewModel.employeimage)
                .placeholder(R.drawable.expe_logo)
                .into(holder.DB_RawReviewFrameBinding.imgEmpProfile)

            holder.DB_RawReviewFrameBinding.txtReviewDate.text = fDate
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
        var lReviewModelSelected: ReviewInfodata? = null
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
                if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                    GlobalUsage.lastClick =
                        System.currentTimeMillis() + GlobalUsage.clickInterval
                    if (view == DB_RawReviewFrameBinding.idFrameconstraint) {
                        GlobalUsage.isReviewChange = false
                        GlobalUsage.empReviewModelSelected = lReviewModelSelected
                        GlobalUsage.isNewReview = false
                        GlobalUsage.NextScreen(
                            moContext as ReviewList,
                            Intent(moContext, ReviewDisplay::class.java)
                        )
//                    var HistoryReviewIntent = Intent(moContext, ReviewDisplay::class.java)
//                    moContext.startActivity(HistoryReviewIntent)
//                    (moContext as ReviewList).overridePendingTransition(
//                        R.anim.activity_in,
//                        R.anim.activity_out
//                    )
                    }
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
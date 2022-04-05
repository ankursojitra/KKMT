package com.rjsquare.kkmt.Activity.Review.Fragment

import android.content.ActivityNotFoundException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.Review.ReviewList
import com.rjsquare.kkmt.Adapter.ReviewAdapter
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.CustomerHistoryModel
import com.rjsquare.kkmt.databinding.FragmentPendingReviewBinding

class PendingReviewFragment : Fragment() {
    lateinit var arrayCompleteReviewModel: ArrayList<CustomerHistoryModel.reviewData.reviewItemInfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DB_PendingReviewFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pending_review, container, false)
        var rootView = DB_PendingReviewFragment.root

        arrayCompleteReviewModel = ReviewList.pendingReviewItemInfo
        framesAdapter()
        return rootView
    }

    fun framesAdapter() {
        try {

            if (arrayCompleteReviewModel.size > 0) {
                DB_PendingReviewFragment.txtNoReviews.visibility = View.GONE
            } else {
                DB_PendingReviewFragment.txtNoReviews.visibility = View.VISIBLE
            }

            val reviewAdapter = ReviewAdapter(
                requireActivity(), arrayCompleteReviewModel
            )

            DB_PendingReviewFragment.rrReviewPendinglist.adapter = reviewAdapter

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
    companion object {
        lateinit var DB_PendingReviewFragment:FragmentPendingReviewBinding
        @JvmStatic
        fun newInstance() =
            PendingReviewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
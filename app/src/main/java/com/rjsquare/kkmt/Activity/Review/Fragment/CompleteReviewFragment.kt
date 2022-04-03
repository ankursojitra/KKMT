package com.rjsquare.kkmt.Activity.Review.Fragment

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rjsquare.kkmt.Adapter.ReviewAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.ReviewModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.FragmentCompleteReviewBinding

class CompleteReviewFragment : Fragment() {

    lateinit var arrayCompleteReviewModel: ArrayList<ReviewModel>

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
        DB_CompleteReviewFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_complete_review, container, false)
        var rootView = DB_CompleteReviewFragment.root

        arrayCompleteReviewModel = ApplicationClass.mArray_ReviewModel
        framesAdapter()
        return rootView
//        return inflater.inflate(R.layout.fragment_complete_review, container, false)
    }

    fun framesAdapter() {
        try {

            if (arrayCompleteReviewModel.size > 0) {
                DB_CompleteReviewFragment.txtNoReviews.visibility = View.GONE
            } else {
                DB_CompleteReviewFragment.txtNoReviews.visibility = View.VISIBLE
            }

            val reviewAdapter = ReviewAdapter(
                requireActivity(), arrayCompleteReviewModel
            )

            DB_CompleteReviewFragment.rrReviewCompletelist.adapter = reviewAdapter

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
        lateinit var DB_CompleteReviewFragment: FragmentCompleteReviewBinding

        @JvmStatic
        fun newInstance() =
            CompleteReviewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
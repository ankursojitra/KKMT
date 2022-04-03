package com.rjsquare.kkmt.Fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Activity.Review.ReviewList
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.mArray_ReviewModel
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.mReviewModel
import com.rjsquare.kkmt.Model.ReviewModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.FragmentHistoryBinding


class History : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

//    private lateinit var mCircularProgress2: CircularProgressIndicator
//    private lateinit var mCircularProgress3: CircularProgressIndicator
//    private lateinit var mCircularProgress4: CircularProgressIndicator
//    private lateinit var mCardViewHistory1: CardView
//    private lateinit var mCardViewHistory2: CardView
//    private lateinit var mCardViewHistory3: CardView
//    private lateinit var mTxtNoReviews: TextView
//    private lateinit var mCardViewReview: CardView

    lateinit var DB_FHistory: FragmentHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DB_FHistory = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
//        var rootview = inflater.inflate(R.layout.fragment_history, container, false)
        try {
//        var circularProgress =
//            rootview.findViewById<CircularProgressIndicator>(R.id.circular_progress1);
//
//        mCircularProgress2 =
//            rootview.findViewById<CircularProgressIndicator>(R.id.circular_progress2)
//        mCircularProgress3 =
//            rootview.findViewById<CircularProgressIndicator>(R.id.circular_progress3)
//        mCircularProgress4 =
//            rootview.findViewById<CircularProgressIndicator>(R.id.circular_progress4)
//
//        mCardViewHistory1 = rootview.findViewById<CardView>(R.id.cardView_history1)
//        mCardViewHistory2 = rootview.findViewById<CardView>(R.id.cardView_history2)
//        mCardViewHistory3 = rootview.findViewById<CardView>(R.id.cardView_history3)
//        mTxtNoReviews = rootview.findViewById<TextView>(R.id.txt_no_reviews)
//        mCardViewReview = rootview.findViewById<CardView>(R.id.cardView_review)


            DB_FHistory.circularProgress1.maxProgress = 100.0
            DB_FHistory.circularProgress1.setCurrentProgress(100.0)
            DB_FHistory.circularProgress1.setProgress(100.0, 100.0)
            DB_FHistory.circularProgress1.progress // returns 70
            DB_FHistory.circularProgress1.maxProgress // returns 100

            DB_FHistory.circularProgress2.maxProgress = 100.0
            DB_FHistory.circularProgress2.setCurrentProgress(50.0)

            DB_FHistory.circularProgress3.maxProgress = 100.0
            DB_FHistory.circularProgress3.setCurrentProgress(30.0)

            DB_FHistory.circularProgress4.maxProgress = 100.0
            DB_FHistory.circularProgress4.setCurrentProgress(10.0)

            DB_FHistory.cardViewHistory1.setOnClickListener(this)
            DB_FHistory.cardViewHistory2.setOnClickListener(this)
            DB_FHistory.cardViewHistory3.setOnClickListener(this)

            FillData()

            HomeActivity.mCntLoader.visibility = View.GONE
            HistoryView = true
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
        return DB_FHistory.root
    }

    companion object {
        var HistoryView = false

        @JvmStatic
        fun newInstance(

        ) =
            History().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun FillData() {
        try {
            mArray_ReviewModel = ArrayList()
            mReviewModel = ReviewModel()
            mReviewModel.Notify_Title = ""
            mArray_ReviewModel.add(mReviewModel)
            mArray_ReviewModel.add(mReviewModel)
            mArray_ReviewModel.add(mReviewModel)

            var ReviewCount = mArray_ReviewModel.size
            if (ReviewCount >= 3) {
               DB_FHistory.cardViewHistory1.visibility = View.VISIBLE
               DB_FHistory.cardViewHistory2.visibility = View.VISIBLE
               DB_FHistory.cardViewHistory3.visibility = View.VISIBLE
               DB_FHistory.cardViewReview.visibility = View.VISIBLE
               DB_FHistory.txtNoReviews.visibility = View.GONE
            } else if (ReviewCount == 2) {
                DB_FHistory.cardViewHistory1.visibility = View.VISIBLE
                DB_FHistory.cardViewHistory2.visibility = View.VISIBLE
                DB_FHistory.cardViewHistory3.visibility = View.GONE
                DB_FHistory.cardViewReview.visibility = View.VISIBLE
                DB_FHistory.txtNoReviews.visibility = View.GONE
            } else if (ReviewCount == 1) {
                DB_FHistory.cardViewHistory1.visibility = View.VISIBLE
                DB_FHistory.cardViewHistory2.visibility = View.GONE
                DB_FHistory.cardViewHistory3.visibility = View.GONE
                DB_FHistory.cardViewReview.visibility = View.VISIBLE
                DB_FHistory.txtNoReviews.visibility = View.GONE
            } else if (ReviewCount == 0) {
                DB_FHistory.cardViewHistory1.visibility = View.GONE
                DB_FHistory.cardViewHistory2.visibility = View.GONE
                DB_FHistory.cardViewHistory3.visibility = View.GONE
                DB_FHistory.cardViewReview.visibility = View.GONE
                DB_FHistory.txtNoReviews.visibility = View.VISIBLE
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

    override fun onClick(view: View?) {
        try {
            if (view == DB_FHistory.cardViewHistory1 || view == DB_FHistory.cardViewHistory2 || view == DB_FHistory.cardViewHistory3) {
                var HistoryReviewIntent = Intent(requireActivity(), ReviewList::class.java)
                requireActivity().startActivity(HistoryReviewIntent)
                requireActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
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
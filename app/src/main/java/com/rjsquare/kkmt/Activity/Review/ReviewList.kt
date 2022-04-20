package com.rjsquare.kkmt.Activity.Review

import android.app.Activity
import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.CustomerHistoryModel
import com.rjsquare.kkmt.databinding.ActivityReviewListBinding

class ReviewList : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object{
        lateinit var reviewListActivity : Activity
        lateinit var DB_ReviewList: ActivityReviewListBinding
        lateinit var pendingReviewItemInfo: ArrayList<CustomerHistoryModel.reviewData.reviewItemInfo>
        lateinit var completeReviewItemInfo: ArrayList<CustomerHistoryModel.reviewData.reviewItemInfo>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ReviewList = DataBindingUtil.setContentView(this,R.layout.activity_review_list)

        try {
            reviewListActivity = this
            ApplicationClass.StatusTextWhite(this, true)
            val sectionsPagerReviewAdapter =
                SectionsPagerReviewAdapter(this, supportFragmentManager)
            pendingReviewItemInfo = ArrayList()
            completeReviewItemInfo = ArrayList()
//            for (model in ApplicationClass.mPendingReviewList){
//                if (model.review_status!!.equals(Constants.Pending,true)){
//                }else if (model.review_status!!.equals(Constants.Approve,true)){
//                }
//            }
            pendingReviewItemInfo.addAll(ApplicationClass.mPendingReviewList)
            completeReviewItemInfo.addAll(ApplicationClass.mApproveReviewList)

            DB_ReviewList.viewPager.adapter = sectionsPagerReviewAdapter
            DB_ReviewList.viewPager.addOnPageChangeListener(object :
                ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    // Check if this is the page you want.

                    if (position == 0) {
                        DB_ReviewList.txtPendingReview.background =
                            ContextCompat.getDrawable(
                                this@ReviewList,
                                R.drawable.tab_selection
                            )
                        DB_ReviewList.txtCompleteReview.background = null
                        DB_ReviewList.txtPendingReview.setTextColor(
                            ContextCompat.getColor(
                                this@ReviewList,
                                R.color.black
                            )
                        )
                        DB_ReviewList.txtCompleteReview.setTextColor(
                            ContextCompat.getColor(
                                this@ReviewList,
                                R.color.white
                            )
                        )
                    } else if (position == 1) {
                        DB_ReviewList.txtCompleteReview.background =
                            ContextCompat.getDrawable(
                                this@ReviewList,
                                R.drawable.tab_selection
                            )
                        DB_ReviewList.txtPendingReview.background = null
                        DB_ReviewList.txtCompleteReview.setTextColor(
                            ContextCompat.getColor(
                                this@ReviewList,
                                R.color.black
                            )
                        )
                        DB_ReviewList.txtPendingReview.setTextColor(
                            ContextCompat.getColor(
                                this@ReviewList,
                                R.color.white
                            )
                        )
                    }
                }
            })

            DB_ReviewList.txtPendingReview.setOnClickListener(this)
            DB_ReviewList.txtCompleteReview.setOnClickListener(this)
            DB_ReviewList.imgBack.setOnClickListener(this)

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


//    fun framesAdapter() {
//        try {
//
//            if (mArray_ReviewModel != null && mArray_ReviewModel.size > 0) {
//                DB_ReviewList.txtNoReviews.visibility = View.GONE
//            } else {
//                DB_ReviewList.txtNoReviews.visibility = View.VISIBLE
//            }
//
//            val loReviewAdapter: ReviewAdapter
////                if (mHomeModelArrayList_old == null) {
//            loReviewAdapter = ReviewAdapter(
//                this, mArray_ReviewModel
//            )
//
//            val linearLayoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//            mRrReviewlist.setLayoutManager(linearLayoutManager)
//            mRrReviewlist.setLayoutManager(GridLayoutManager(this, 1))
//            DB_ReviewList.rrReviewlist.setAdapter(loReviewAdapter)
//
//
//        } catch (NE: NullPointerException) {
//            NE.printStackTrace()
//        } catch (IE: IndexOutOfBoundsException) {
//            IE.printStackTrace()
//        } catch (AE: ActivityNotFoundException) {
//            AE.printStackTrace()
//        } catch (E: IllegalArgumentException) {
//            E.printStackTrace()
//        } catch (RE: RuntimeException) {
//            RE.printStackTrace()
//        } catch (E: Exception) {
//            E.printStackTrace()
//        }
//    }

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis()< ApplicationClass.lastClick) return else {
                ApplicationClass.lastClick = System.currentTimeMillis() + ApplicationClass.clickInterval
            if (view == DB_ReviewList.imgBack) {
                onBackPressed()
            }else if (view == DB_ReviewList.txtPendingReview) {
                Log.e("TAG","CHECKItemclick")
                DB_ReviewList.viewPager.currentItem = 0
                DB_ReviewList.txtPendingReview.background =
                    ContextCompat.getDrawable(this, R.drawable.tab_selection)
                DB_ReviewList.txtCompleteReview.background = null
                DB_ReviewList.txtPendingReview.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.black
                    )
                )
                DB_ReviewList.txtCompleteReview.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }else if (view == DB_ReviewList.txtCompleteReview) {
                DB_ReviewList.viewPager.currentItem = 1
                DB_ReviewList.txtCompleteReview.background =
                    ContextCompat.getDrawable(this, R.drawable.tab_selection)
                DB_ReviewList.txtPendingReview.background = null
                DB_ReviewList.txtCompleteReview.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.black
                    )
                )
                DB_ReviewList.txtPendingReview.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
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
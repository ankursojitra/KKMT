package com.rjsquare.kkmt.Activity.Review

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityReviewScreenBinding
import com.squareup.picasso.Picasso

class ReviewEdit : AppCompatActivity(), View.OnClickListener {
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_ReviewEdit: ActivityReviewScreenBinding
        lateinit var thisReviewEdit: Activity
        var isVoiceNote = false
        var isWritenNote = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ReviewEdit = DataBindingUtil.setContentView(this, R.layout.activity_review_screen)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            thisReviewEdit = this

            DB_ReviewEdit.txtReviewName.text = ApplicationClass.Selected_ReviewEmp_Model.EmpName

            Picasso.with(this).load(ApplicationClass.Selected_ReviewEmp_Model.EmpImage)
                .placeholder(R.drawable.ic_expe_logo).into(DB_ReviewEdit.imgProfile)

            DB_ReviewEdit.txtSubmit.setOnClickListener(this)
            DB_ReviewEdit.imgBack.setOnClickListener(this)
            DB_ReviewEdit.cnt1star.setOnClickListener(this)
            DB_ReviewEdit.cntBad.setOnClickListener(this)
            DB_ReviewEdit.cntGood.setOnClickListener(this)
            DB_ReviewEdit.cnt5star.setOnClickListener(this)


            SetUpReviewData()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            mTxtAmt.text=(Html.fromHtml("Enter Amount  <font color='#8A4EF2'>&amp; Receipt Number</font>", Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            mTxtAmt .text=(Html.fromHtml("Enter Amount  <font color='#8A4EF2'>&amp; Receipt Number</font>"));
//        }
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

    private fun UncheckedReview() {
        DB_ReviewEdit.cnt1star.setBackgroundColor(ContextCompat.getColor(this,R.color.transparent))
        DB_ReviewEdit.cntBad.setBackgroundColor(ContextCompat.getColor(this,R.color.transparent))
        DB_ReviewEdit.cntGood.setBackgroundColor(ContextCompat.getColor(this,R.color.transparent))
        DB_ReviewEdit.cnt5star.setBackgroundColor(ContextCompat.getColor(this,R.color.transparent))
    }

    private fun SetUpReviewData() {
        if (ApplicationClass.isReviewNew) {
            //Setup New review data
            UncheckedReview()

        } else {
            //Setup edit review data
        }
    }

    override fun onClick(view: View?) {
        try {
            if (view == DB_ReviewEdit.txtSubmit) {
                var HelperIntent = Intent(this, ReviewDisplay::class.java)
                startActivity(HelperIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_ReviewEdit.imgBack) {
                onBackPressed()
            } else if (view == DB_ReviewEdit.cnt1star) {
                UncheckedReview()
                DB_ReviewEdit.cnt1star.setBackgroundResource(R.drawable.review_selection)
            } else if (view == DB_ReviewEdit.cntBad) {
                UncheckedReview()
                DB_ReviewEdit.cntBad.setBackgroundResource(R.drawable.review_selection)
            } else if (view == DB_ReviewEdit.cntGood) {
                UncheckedReview()
                DB_ReviewEdit.cntGood.setBackgroundResource(R.drawable.review_selection)
            } else if (view == DB_ReviewEdit.cnt5star) {
                UncheckedReview()
                DB_ReviewEdit.cnt5star.setBackgroundResource(R.drawable.review_selection)
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
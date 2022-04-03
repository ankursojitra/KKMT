package com.rjsquare.kkmt.Activity.Review

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.Bussiness.BussinessCheckIn
import com.rjsquare.kkmt.Activity.Bussiness.Bussiness_Location
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityReviewShowBinding
import com.squareup.picasso.Picasso

class ReviewDisplay : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_ReviewDisplay: ActivityReviewShowBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ReviewDisplay = DataBindingUtil.setContentView(this, R.layout.activity_review_show)
//        setContentView(R.layout.activity_review_show)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            DB_ReviewDisplay.txtReviewName.text = ApplicationClass.Selected_ReviewEmp_Model.EmpName

            Picasso.with(this).load(ApplicationClass.Selected_ReviewEmp_Model.EmpImage)
                .into(DB_ReviewDisplay.imgProfile)

            DB_ReviewDisplay.imgBack.setOnClickListener(this)
            DB_ReviewDisplay.cntBacktohome.setOnClickListener(this)
            DB_ReviewDisplay.cntEditdetails.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        try {
            if (v == DB_ReviewDisplay.imgBack) {
                onBackPressed()
            } else if (v == DB_ReviewDisplay.cntBacktohome) {
                Bussiness_Location.thisBussiness_Activity.finish()
                BussinessCheckIn.thisBusinessCheckIn.finish()
                SearchEmployee.thisSearchEmployee.finish()
                ReviewEdit.thisReviewEdit.finish()
                finish()
                overridePendingTransition(R.anim.activity_back_in,R.anim.activity_back_out)
            } else if (v == DB_ReviewDisplay.cntEditdetails) {
                onBackPressed()
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
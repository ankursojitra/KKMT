package com.rjsquare.kkmt.Activity.Review

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityReviewScreenBinding
import com.squareup.picasso.Picasso

class ReviewEdit : AppCompatActivity(), View.OnClickListener {
//    private lateinit var mTxtSubmit: TextView
//    private lateinit var mImgBack: ImageView
//    private lateinit var mTxtAmt: TextView
//    private lateinit var mTxtReviewName: TextView
//    private lateinit var mImgProfile: ImageView

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }
    companion object{
        lateinit var DB_ReviewEdit:ActivityReviewScreenBinding
        lateinit var thisReviewEdit: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ReviewEdit = DataBindingUtil.setContentView(this,R.layout.activity_review_screen)
//        setContentView(R.layout.activity_review_screen)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            thisReviewEdit =this
//            mTxtSubmit = findViewById<TextView>(R.id.txt_submit)
//            mImgBack = findViewById<ImageView>(R.id.img_back)
//            mTxtAmt = findViewById<TextView>(R.id.txt_amt)
//            mTxtReviewName = findViewById<TextView>(R.id.txt_review_name)
//            mImgProfile = findViewById<ImageView>(R.id.img_profile)


            DB_ReviewEdit.txtReviewName.text = ApplicationClass.Selected_ReviewEmp_Model.EmpName

            Picasso.with(this).load(ApplicationClass.Selected_ReviewEmp_Model.EmpImage).into(DB_ReviewEdit.imgProfile)

            DB_ReviewEdit.txtSubmit.setOnClickListener(this)
            DB_ReviewEdit.imgBack.setOnClickListener(this)


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

    override fun onClick(view: View?) {
        try {
            if (view == DB_ReviewEdit.txtSubmit) {
                var HelperIntent = Intent(this, ReviewDisplay::class.java)
                startActivity(HelperIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == DB_ReviewEdit.imgBack) {
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
package com.rjsquare.kkmt.Activity.Bussiness

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.Review.SearchEmployee
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityBussinessCheckInBinding

class BussinessCheckIn : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var thisBusinessCheckIn : Activity
        lateinit var DB_BussinessCheckIn: ActivityBussinessCheckInBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_BussinessCheckIn =
            DataBindingUtil.setContentView(this, R.layout.activity_bussiness_check_in)
//        setContentView(R.layout.activity_bussiness_check_in)
        ApplicationClass.StatusTextWhite(this, false)
        thisBusinessCheckIn = this
        DB_BussinessCheckIn.imgBack.setOnClickListener(this)
        DB_BussinessCheckIn.txtContinue.setOnClickListener(this)
        DB_BussinessCheckIn.cntCheckout.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v == DB_BussinessCheckIn.imgBack) {
            onBackPressed()
        } else if (v == DB_BussinessCheckIn.txtContinue) {
            var ReviewIntent = Intent(this, SearchEmployee::class.java)
            startActivity(ReviewIntent)
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        } else if (v == DB_BussinessCheckIn.cntCheckout) {
            finish()
//            Bussiness_Location.Bussiness_Activity.finish()
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
        }
    }
}
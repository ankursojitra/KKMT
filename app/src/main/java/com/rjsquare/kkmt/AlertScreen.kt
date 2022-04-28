package com.rjsquare.kkmt

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.databinding.ActivityAlertScreenBinding

class AlertScreen : AppCompatActivity(), View.OnClickListener {

    lateinit var DB_ActivityAlertScreen: ActivityAlertScreenBinding

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_ActivityAlertScreen =
            DataBindingUtil.setContentView(this, R.layout.activity_alert_screen)
        DB_ActivityAlertScreen.txtAlertok.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
            GlobalUsage.lastClick =
                System.currentTimeMillis() + GlobalUsage.clickInterval
            if (view == DB_ActivityAlertScreen.txtAlertok) {
                onBackPressed()
            }
        }

    }
}
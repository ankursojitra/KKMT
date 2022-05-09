package com.rjsquare.kkmt.Activity.Setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicy : AppCompatActivity() {
    lateinit var DB_PrivacyPolicy: ActivityPrivacyPolicyBinding
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_PrivacyPolicy = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy)
        GlobalUsage.StatusTextWhite(this, true)

    }
}
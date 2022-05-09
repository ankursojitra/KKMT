package com.rjsquare.kkmt.Activity.Setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityAdsBinding

class Ads : AppCompatActivity() {
    lateinit var DB_Ads: ActivityAdsBinding

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Ads = DataBindingUtil.setContentView(this, R.layout.activity_ads)
        GlobalUsage.StatusTextWhite(this, true)
        DB_Ads.txtAdsText.text = getString(R.string.ads)
    }
}
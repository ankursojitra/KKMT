package com.rjsquare.kkmt.Activity.Setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivitySecurityBinding

class Security : AppCompatActivity() {

    lateinit var DB_Security: ActivitySecurityBinding

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Security = DataBindingUtil.setContentView(this, R.layout.activity_security)

        GlobalUsage.StatusTextWhite(this, true)

        DB_Security.txtSecurityText.text = getString(R.string.security)
    }
}
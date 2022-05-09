package com.rjsquare.kkmt.Activity.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityHelpBinding

class Help : AppCompatActivity() {
    lateinit var DB_Help :ActivityHelpBinding

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in,R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DB_Help = DataBindingUtil.setContentView(this,R.layout.activity_help)
        GlobalUsage.StatusTextWhite(this, true)
        DB_Help.txtHelpText.text = getString(R.string.help)
    }
}
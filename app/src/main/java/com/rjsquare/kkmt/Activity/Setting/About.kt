package com.rjsquare.kkmt.Activity.Setting

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityAboutBinding


class About : AppCompatActivity() {
    lateinit var DB_About: ActivityAboutBinding
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_About = DataBindingUtil.setContentView(this, R.layout.activity_about)
        GlobalUsage.StatusTextWhite(this, true)
        DB_About.txtAboutText.text = getString(R.string.about)
        val pm = applicationContext.packageManager
        val pkgName = applicationContext.packageName
        var pkgInfo: PackageInfo? = null
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val ver = pkgInfo!!.versionName

        DB_About.txtAboutVersion.text = "Version : $ver"
    }
}
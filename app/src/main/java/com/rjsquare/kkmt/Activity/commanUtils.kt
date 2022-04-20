package com.rjsquare.kkmt.Activity

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.util.Patterns
import com.rjsquare.kkmt.R
import java.text.DecimalFormat


object commanUtils {
    fun NextScreen(activity: Activity, NextScreenIntent: Intent) {
        activity.startActivity(NextScreenIntent)
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

//    fun NextScreenc(currentScreen: Activity, nextScreen: Activity) {
//        var new = Intent(currentScreen, nextScreen::class.java)
//        currentScreen.startActivity(new)
//        currentScreen.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
//    }

    fun formatNumber(number: Int): String {
        val df = DecimalFormat("#,###,###")
        return df.format(number)
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}
package com.rjsquare.kkmt.Activity

import android.app.Activity
import android.content.Intent
import com.rjsquare.kkmt.R
import java.text.DecimalFormat

object commanUtils {
    fun NextScreen(activity: Activity, NextScreenIntent: Intent) {
        activity.startActivity(NextScreenIntent)
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

    fun formatNumber(number:Int):String{
        val df = DecimalFormat("#,###,###")
        return df.format(number)
    }

}
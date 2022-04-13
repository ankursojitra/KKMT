package com.rjsquare.kkmt.Activity

import android.app.Activity
import android.content.Intent
import com.rjsquare.kkmt.R

object function {
    fun NextScreen(activity: Activity, NextScreenIntent: Intent) {
        activity.startActivity(NextScreenIntent)
        activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }
}
package com.rjsquare.kkmt

import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log

class IMMResult : ResultReceiver(null) {
    var results = -1
    public override fun onReceiveResult(r: Int, data: Bundle) {
        results = r
    }

    // poll result value for up to 500 milliseconds
    fun getResult(): Int {
        try {
            var sleep = 0
            while (results == -1 && sleep < 500) {
                Thread.sleep(100)
                sleep += 100
            }
        } catch (e: InterruptedException) {
            Log.e("IMMResult", e.message!!)
        }
        return results
    }
}
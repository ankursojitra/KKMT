package com.rjsquare.kkmt.Activity.Splash

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Activity.Login.Login
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.Helpers.Preferences
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivitySplashBinding


class Splash : AppCompatActivity() {
    companion object {
        lateinit var DB_Splash: ActivitySplashBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Splash = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            ApplicationClass.userLogedIn = Preferences.ReadBoolean(Constants.Pref_UserLogedIn, false)

            val handler = Handler()
            val runnable = Runnable {
                var AppStart = Intent()
                if (ApplicationClass.userLogedIn) {
                    AppStart = Intent(this@Splash, HomeActivity::class.java)
                } else {
                    AppStart = Intent(this@Splash, Login::class.java)
                }
                startActivity(AppStart)
                finish()
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            }
            handler.postDelayed(runnable, 1500)
        } catch (NE: NullPointerException) {
            NE.printStackTrace()
        } catch (IE: IndexOutOfBoundsException) {
            IE.printStackTrace()
        } catch (AE: ActivityNotFoundException) {
            AE.printStackTrace()
        } catch (E: IllegalArgumentException) {
            E.printStackTrace()
        } catch (RE: RuntimeException) {
            RE.printStackTrace()
        } catch (E: Exception) {
            E.printStackTrace()
        }
    }
}
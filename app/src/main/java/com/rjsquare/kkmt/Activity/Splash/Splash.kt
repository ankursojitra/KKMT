package com.rjsquare.kkmt.Activity.Splash

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.HomeActivity

import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
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
            GlobalUsage.StatusTextWhite(this, true)
            GlobalUsage.userLogedIn =
                Preferences.ReadBoolean(Constants.Pref_UserLogedIn, false)
//            DB_Splash.cntStartapp.transitionToStart()
            DB_Splash.cntStartapp.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {

                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {

                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    GlobalUsage.NextScreen(
                        this@Splash,
                        Intent(this@Splash, HomeActivity::class.java)
                    )
                    finish()
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {

                }
            })
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
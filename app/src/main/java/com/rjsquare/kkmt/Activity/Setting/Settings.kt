package com.rjsquare.kkmt.Activity.Setting

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.Notifications.NotificationList
import com.rjsquare.kkmt.Activity.Profile.Profile

import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_Settings: ActivitySettingsBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Settings = DataBindingUtil.setContentView(this, R.layout.activity_settings)
//        setContentView(R.layout.activity_settings)
        try {
            GlobalUsage.StatusTextWhite(this, true)

            DB_Settings.imgBack.setOnClickListener(this)
            DB_Settings.cntNotificationMain.setOnClickListener(this)
            DB_Settings.cntPrivacyMain.setOnClickListener(this)
            DB_Settings.cntSecurityMain.setOnClickListener(this)
            DB_Settings.cntAdsMain.setOnClickListener(this)
            DB_Settings.cntAccountMain.setOnClickListener(this)
            DB_Settings.cntHelpMain.setOnClickListener(this)
            DB_Settings.cntAboutMain.setOnClickListener(this)
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

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_Settings.imgBack) {
                    onBackPressed()
                } else if (view == DB_Settings.cntNotificationMain) {
                    GlobalUsage.NextScreen(this,Intent(this, NotificationList::class.java))
                } else if (view == DB_Settings.cntPrivacyMain) {
                    Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
                } else if (view == DB_Settings.cntSecurityMain) {
                    GlobalUsage.NextScreen(this, Intent(this, Security::class.java))
                } else if (view == DB_Settings.cntAdsMain) {
                    GlobalUsage.NextScreen(this, Intent(this, Ads::class.java))
                } else if (view == DB_Settings.cntAccountMain) {
                    GlobalUsage.NextScreen(this,Intent(this, Profile::class.java))
                } else if (view == DB_Settings.cntHelpMain) {
                    GlobalUsage.NextScreen(this,Intent(this, Help::class.java))
                } else if (view == DB_Settings.cntAboutMain) {
                    GlobalUsage.NextScreen(this,Intent(this, About::class.java))
                }
            }
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
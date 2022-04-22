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
import com.rjsquare.kkmt.AppConstant.ApplicationClass
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
            ApplicationClass.StatusTextWhite(this, true)

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
            if (System.currentTimeMillis() < ApplicationClass.lastClick) return else {
                ApplicationClass.lastClick =
                    System.currentTimeMillis() + ApplicationClass.clickInterval
                if (view == DB_Settings.imgBack) {
                    onBackPressed()
                } else if (view == DB_Settings.cntNotificationMain) {
                    var NotifyIntent = Intent(this, NotificationList::class.java)
                    startActivity(NotifyIntent)
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                } else if (view == DB_Settings.cntPrivacyMain) {
                    Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
                } else if (view == DB_Settings.cntSecurityMain) {
                    Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
                } else if (view == DB_Settings.cntAdsMain) {
                    Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
                } else if (view == DB_Settings.cntAccountMain) {
                    var ProfileIntent = Intent(this, Profile::class.java)
                    startActivity(ProfileIntent)
                    overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
                } else if (view == DB_Settings.cntHelpMain) {
                    Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
                } else if (view == DB_Settings.cntAboutMain) {
                    Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
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
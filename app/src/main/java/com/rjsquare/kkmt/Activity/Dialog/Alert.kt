package com.rjsquare.kkmt.Activity.Dialog

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.Store.StoreLevelList
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.AlertDialogBinding

object Alert {
    fun showDialog(activity: Activity, Message: String) {
        val DB_AlertDialog: AlertDialogBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.alert_dialog,
                null,
                false
            )

        val customDialog = AlertDialog.Builder(activity, R.style.alertdialogTheme).create()
        DB_AlertDialog?.txtAlertmsg!!.text = Message
        customDialog.apply {
            setView(DB_AlertDialog.root)
            setCancelable(false)
        }.show()

        DB_AlertDialog.txtAlertok.setOnClickListener {
            if (GlobalUsage.ItemRedeemed) {
                GlobalUsage.ItemRedeemed = false
                activity.setResult(Activity.RESULT_OK)
                StoreLevelList.thisStoreLevelActivity.finish()
                activity.finish()
                activity.overridePendingTransition(
                    R.anim.activity_back_in,
                    R.anim.activity_back_out
                )
            }
            customDialog.dismiss()
        }
    }
}
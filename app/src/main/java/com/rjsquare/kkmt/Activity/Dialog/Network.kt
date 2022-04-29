package com.rjsquare.kkmt.Activity.Dialog

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.Store.StoreLevelList
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.AlertDialogBinding

object Network {
    fun showDialog(activity: Activity) {
        if (GlobalUsage.networkDialogVisible) return
        val DB_AlertDialog: AlertDialogBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.alert_dialog,
                null,
                false
            )

        val customDialog = AlertDialog.Builder(activity, R.style.alertdialogTheme).create()
        DB_AlertDialog?.txtAlertmsg!!.text = activity.getString(R.string.networkmeaasge)
        DB_AlertDialog.txtAlert.text = activity.getString(R.string.networktitle)
        customDialog.apply {
            setView(DB_AlertDialog.root)
            setCancelable(false)
        }.show()
        GlobalUsage.networkDialogVisible = true
        DB_AlertDialog.txtAlertok.setOnClickListener {
            GlobalUsage.networkDialogVisible = false
            customDialog.dismiss()
        }
    }
}
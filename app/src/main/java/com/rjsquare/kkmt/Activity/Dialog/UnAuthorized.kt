package com.rjsquare.kkmt.Activity.Dialog

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.UnauthorizedDialogBinding

object UnAuthorized {
    fun showDialog(activity: Activity) {
        if (GlobalUsage.unauthorizedDialogVisible) return
        val DB_UnauthorizedDialog: UnauthorizedDialogBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.unauthorized_dialog,
                null,
                false
            )

        val customDialog = AlertDialog.Builder(activity, R.style.alertdialogTheme).create()
        DB_UnauthorizedDialog?.txtUnaithorizedmsg!!.text = activity.getString(R.string.unauthorizeduser)
        customDialog.apply {
            setView(DB_UnauthorizedDialog.root)
            setCancelable(false)
        }.show()
        GlobalUsage.unauthorizedDialogVisible = true
        DB_UnauthorizedDialog.txtUnaithorizedOk.setOnClickListener {
            GlobalUsage.UserLogout(activity)
            GlobalUsage.unauthorizedDialogVisible = false
            customDialog.dismiss()
        }
    }
}
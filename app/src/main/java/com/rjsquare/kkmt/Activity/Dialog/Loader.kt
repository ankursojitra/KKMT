package com.rjsquare.kkmt.Activity.Dialog

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.Store.StoreLevelList
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.AlertDialogBinding
import com.rjsquare.kkmt.databinding.LoaderDialogBinding

object Loader {
    lateinit var customDialog:AlertDialog
    fun showLoader(activity: Activity) {
        if (GlobalUsage.loaderDialogVisible) return
        val DB_LoaderDialog: LoaderDialogBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.loader_dialog,
                null,
                false
            )

        customDialog = AlertDialog.Builder(activity, R.style.dialogTheme).create()
//        DB_LoaderDialog?.txtAlertmsg!!.text = Message
//        customDialog.setView(DB_LoaderDialog?.root!!);
//        customDialog.show()
//        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        customDialog.apply {
            setView(DB_LoaderDialog?.root)
            setCancelable(false)
        }.show()
        GlobalUsage.loaderDialogVisible = true

//        DB_LoaderDialog.txtAlertok.setOnClickListener {
//            if (GlobalUsage.ItemRedeemed) {
//                GlobalUsage.ItemRedeemed = false
//                activity.setResult(Activity.RESULT_OK)
//                StoreLevelList.thisStoreLevelActivity.finish()
//                activity.finish()
//                activity.overridePendingTransition(
//                    R.anim.activity_back_in,
//                    R.anim.activity_back_out
//                )
//            }
//            customDialog.dismiss()
//        }
    }

    fun hideLoader() {
        GlobalUsage.loaderDialogVisible = false
        customDialog.dismiss()
    }
}
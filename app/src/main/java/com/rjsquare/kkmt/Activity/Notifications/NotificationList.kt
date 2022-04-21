package com.rjsquare.kkmt.Activity.Notifications

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Adapter.NotifiyAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.NotificationModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityNotificationListBinding

class NotificationList : AppCompatActivity(), View.OnClickListener {
    lateinit var mNotificationModel: NotificationModel
    lateinit var mArray_NotificationModel: ArrayList<NotificationModel>

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_NotificationList: ActivityNotificationListBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_NotificationList =
            DataBindingUtil.setContentView(this, R.layout.activity_notification_list)
//        setContentView(R.layout.activity_notification_list)
        try {
            ApplicationClass.StatusTextWhite(this, true)

            DB_NotificationList.imgBack.setOnClickListener(this)
            mArray_NotificationModel = ArrayList()

            FillData()
            framesAdapter()
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

    private fun FillData() {
        try {
            mArray_NotificationModel = ArrayList()
            mNotificationModel = NotificationModel()
            mArray_NotificationModel.add(mNotificationModel)
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


    fun framesAdapter() {
        try {

            if (mArray_NotificationModel != null && mArray_NotificationModel.size > 0) {
                DB_NotificationList.txtNoNotifications.visibility = View.GONE
            } else {
                DB_NotificationList.txtNoNotifications.visibility = View.VISIBLE
            }

            val loNotifiyAdapter: NotifiyAdapter
//                if (mHomeModelArrayList_old == null) {
            loNotifiyAdapter = NotifiyAdapter(
                this, mArray_NotificationModel
            )

            DB_NotificationList.rrNotification.adapter = loNotifiyAdapter


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
                if (view == DB_NotificationList.imgBack) {
                    onBackPressed()
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
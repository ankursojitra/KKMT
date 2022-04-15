package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.NotificationModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.RawLeaderboardFrameBinding
import com.rjsquare.kkmt.databinding.RawNotificationFrameBinding
import java.util.*

class NotifiyAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<NotificationModel>
) : RecyclerView.Adapter<NotifiyAdapter.View_holder>() {
    var mNotificationModel: NotificationModel? = null
    var Width = 0
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawNotificationFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_notification_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)

//        val view: View =
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.raw_notification_frame, parent, false)
//        val height = parent.measuredHeight
//        val width = parent.measuredWidth
//        Width = width
////        view.layoutParams = RecyclerView.LayoutParams(width, height)
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mNotificationModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mNotificationModel_old: NotificationModel
            holder.lNotificationModelSelected = mNotificationModel


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

    override fun getItemCount(): Int {
        return moArrayList.size
    }

    inner class View_holder(itemBinding: RawNotificationFrameBinding) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

//        private lateinit var mImgNotify: ImageView
//        private lateinit var mTxtNotifyTitle: TextView
//        private lateinit var mTxtNotifyAmt: TextView
//        private lateinit var mCntAmt: ConstraintLayout
//        private lateinit var mIdFrameconstraint: ConstraintLayout

        var lNotificationModelSelected: NotificationModel? = null

        lateinit var DB_RawNotificationFrameBinding: RawNotificationFrameBinding
        init {
            try {
                DB_RawNotificationFrameBinding =itemBinding
//                mImgNotify = itemView.findViewById<ImageView>(R.id.img_notify)
//                mTxtNotifyTitle = itemView.findViewById<TextView>(R.id.txt_notify_title)
//                mTxtNotifyAmt = itemView.findViewById<TextView>(R.id.txt_notify_amt)
//                mCntAmt = itemView.findViewById<ConstraintLayout>(R.id.cnt_amt)
//                mIdFrameconstraint =
//                    itemView.findViewById<ConstraintLayout>(R.id.id_frameconstraint)
                DB_RawNotificationFrameBinding.idFrameconstraint.setOnClickListener(this)


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

        override fun onClick(view: View?) {try{
            if (System.currentTimeMillis()< ApplicationClass.lastClick) return else {
                ApplicationClass.lastClick = System.currentTimeMillis() + ApplicationClass.clickInterval
            if (view == DB_RawNotificationFrameBinding.idFrameconstraint) {
                Toast.makeText(moContext, "Comming soon...", Toast.LENGTH_SHORT).show()
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

}
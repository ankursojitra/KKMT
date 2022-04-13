package com.rjsquare.kkmt.Adapter

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.kkmt.Activity.Events.Events
import com.rjsquare.kkmt.Activity.Events.EventsFullView
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.ApplicationClass.Companion.mEventsModelSelected
import com.rjsquare.kkmt.Model.EventsModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.Events_Model
import com.rjsquare.kkmt.databinding.RawEventsFrameBinding
import com.squareup.picasso.Picasso

class EventsAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<Events_Model.EventsData>
) : RecyclerView.Adapter<EventsAdapter.View_holder>() {

    var Width = 0
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View_holder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RawEventsFrameBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.raw_events_frame, parent, false)
        val height = parent.measuredHeight
        val width = parent.measuredWidth
        Width = width
        return View_holder(binding)

//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.raw_events_frame, parent, false)
//        val height = parent.measuredHeight
//        val width = parent.measuredWidth
//        Width = width
////        view.layoutParams = RecyclerView.LayoutParams(width, height)
//        return View_holder(view)
    }

    override fun onBindViewHolder(holder: View_holder, position: Int) {
        try {
            var mEventsModel = moArrayList[position]
            var Per_Value_old = 0.0
            val mEventsModel_old: EventsModel
            holder.lEventsModelSelected = mEventsModel
//            holder.DB_RawEventsFrameBinding.imgEvent.setImageDrawable(mEventsModel.ImgLink)
//            holder.DB_RawEventsFrameBinding.txtEventTitle.text = mEventsModel.EventTitle
//            holder.DB_RawEventsFrameBinding.txtEventTime.text = mEventsModel.EventTime
//            holder.DB_RawEventsFrameBinding.txtEventDate.text = mEventsModel.EventDate
            holder.DB_RawEventsFrameBinding.txtEventTitle.text = mEventsModel.title
            holder.DB_RawEventsFrameBinding.txtEventTime.text = mEventsModel.time
            holder.DB_RawEventsFrameBinding.txtEventDate.text = mEventsModel.date
            Picasso.with(moContext).load(mEventsModel.image!![0])
                .placeholder(R.drawable.ic_expe_logo).into(holder.DB_RawEventsFrameBinding.imgEvent)
//            Glide
//                .with(moContext)
//                .load(mEventsModel.image!![0])
//                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
//                .into(holder.DB_RawEventsFrameBinding.imgEvent);
//            Log.e("TAG", "IMAGELINK : " + mEventsModel.image!![0])
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

    inner class View_holder(itemBinding: RawEventsFrameBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        lateinit var mImgEvent: ImageView
//        lateinit var mTxtEventTitle: TextView
//        lateinit var mTxtEventTime: TextView
//        private lateinit var mIdFrameconstraint: ConstraintLayout
//        private lateinit var mIdMainCnt: ConstraintLayout
//        lateinit var mTxtEventDate: TextView

        var lEventsModelSelected: Events_Model.EventsData? = null

        lateinit var DB_RawEventsFrameBinding: RawEventsFrameBinding

        init {
            try {

                DB_RawEventsFrameBinding = itemBinding
                mImgEvent = DB_RawEventsFrameBinding.imgEvent
//                mImgEvent = itemView.findViewById<ImageView>(R.id.img_event)
//                mTxtEventTitle = itemView.findViewById<TextView>(R.id.txt_event_title)
//                mTxtEventTime = itemView.findViewById<TextView>(R.id.txt_event_time)
//                mIdMainCnt = itemView.findViewById<ConstraintLayout>(R.id.id_mainCnt)
//                mTxtEventDate = itemView.findViewById<TextView>(R.id.txt_event_date)
//
//
//                mIdFrameconstraint =
//                    itemView.findViewById<ConstraintLayout>(R.id.id_frameconstraint)
                DB_RawEventsFrameBinding.idFrameconstraint.setOnClickListener(this)

                ApplicationClass.SetLayoutWidthHeight(
                    DB_RawEventsFrameBinding.imgEvent,
                    ((Width / 11) * 6),
                    ((Width / 10) * 3)
                )
//                AppClass.SetLayoutWidth(mImgEvent, ((Width / 10) * 5))


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
                if (view == DB_RawEventsFrameBinding.idFrameconstraint) {
                    mEventsModelSelected = lEventsModelSelected
                    var FullEventIntent = Intent(moContext, EventsFullView::class.java)
                    moContext.startActivity(FullEventIntent)
                    (moContext as Events).overridePendingTransition(
                        R.anim.activity_in,
                        R.anim.activity_out
                    )
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
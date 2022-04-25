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
import com.rjsquare.kkmt.Activity.Events.EventsHome
import com.rjsquare.kkmt.Activity.Events.EventsDetailsScreen
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.EventsByMonth_Model
import com.rjsquare.kkmt.databinding.RawEventsFrameBinding
import com.squareup.picasso.Picasso

class EventsByMonthAdapter(
    var moContext: Context,
    var moArrayList: ArrayList<EventsByMonth_Model.EventsData.DateWiseEvents>
) : RecyclerView.Adapter<EventsByMonthAdapter.View_holder>() {

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
            holder.lEventsModelSelected = mEventsModel
            holder.DB_RawEventsFrameBinding.txtEventTitle.text = mEventsModel.title
            holder.DB_RawEventsFrameBinding.txtEventTime.text = mEventsModel.time
            holder.DB_RawEventsFrameBinding.txtEventDate.text = mEventsModel.date
            Picasso.with(moContext).load(mEventsModel.image!![0])
                .placeholder(R.drawable.expe_logo).into(holder.DB_RawEventsFrameBinding.imgEvent)
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
        var lEventsModelSelected: EventsByMonth_Model.EventsData.DateWiseEvents? = null

        lateinit var DB_RawEventsFrameBinding: RawEventsFrameBinding

        init {
            try {

                DB_RawEventsFrameBinding = itemBinding
                mImgEvent = DB_RawEventsFrameBinding.imgEvent
                DB_RawEventsFrameBinding.idFrameconstraint.setOnClickListener(this)

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
                    if (view == DB_RawEventsFrameBinding.idFrameconstraint) {
                        GlobalUsage.mEventsByMonthModelSelected = lEventsModelSelected
                        var FullEventIntent = Intent(moContext, EventsDetailsScreen::class.java)
                        moContext.startActivity(FullEventIntent)
                        (moContext as EventsHome).overridePendingTransition(
                            R.anim.activity_in,
                            R.anim.activity_out
                        )
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
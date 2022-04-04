package com.rjsquare.kkmt.Activity.Events

import android.content.ActivityNotFoundException
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afdhal_fa.imageslider.`interface`.ItemClickListener
import com.afdhal_fa.imageslider.model.SlideUIModel
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityEventsFullViewBinding

class EventsFullView : AppCompatActivity(), View.OnClickListener {

//    private lateinit var mImgBack: ImageView

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_EventsView: ActivityEventsFullViewBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_EventsView = DataBindingUtil.setContentView(this, R.layout.activity_events_full_view)
//        setContentView(R.layout.activity_events_full_view)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            DB_EventsView.imgBack.setOnClickListener(this)


            val imageList = ArrayList<SlideUIModel>()
            for (link in ApplicationClass.mEventsModelSelected!!.image!!){
                imageList.add(SlideUIModel(link, " "))
            }
            DB_EventsView.imageSlide.setItemClickListener(object : ItemClickListener {
                override fun onItemClick(model: SlideUIModel, position: Int) {

                }
            })
            DB_EventsView.imageSlide.setImageList(imageList)
           DB_EventsView.imageSlide.startSliding(1000) // with new period
           DB_EventsView.imageSlide.startSliding()
//           DB_EventsView.imageSlide.stopSliding()
            DB_EventsView.txtEventTitle.text =  ApplicationClass.mEventsModelSelected!!.title!!
            DB_EventsView.txtEventLocation.text =  ApplicationClass.mEventsModelSelected!!.location
            DB_EventsView.txtEventTime.text =  ApplicationClass.mEventsModelSelected!!.time
            DB_EventsView.txtEventDesc.text =  ApplicationClass.mEventsModelSelected!!.description
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
            if (view == DB_EventsView.imgBack) {
                onBackPressed()
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
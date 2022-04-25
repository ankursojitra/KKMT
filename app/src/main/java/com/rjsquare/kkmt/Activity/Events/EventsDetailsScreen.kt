package com.rjsquare.kkmt.Activity.Events

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afdhal_fa.imageslider.`interface`.ItemClickListener
import com.afdhal_fa.imageslider.model.SlideUIModel
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityEventsDetailsScreenBinding

class EventsDetailsScreen : AppCompatActivity(), View.OnClickListener {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var DB_EventsDetailsScreen: ActivityEventsDetailsScreenBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_EventsDetailsScreen =
            DataBindingUtil.setContentView(this, R.layout.activity_events_details_screen)
        try {
            GlobalUsage.StatusTextWhite(this, true)
            initListners()
            setUpUI()

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

    private fun setUpUI() {
        DB_EventsDetailsScreen.txtEventTitle.text = GlobalUsage.mEventsModelSelected!!.title!!
        DB_EventsDetailsScreen.txtEventLocation.text = GlobalUsage.mEventsModelSelected!!.location
        DB_EventsDetailsScreen.txtEventTime.text = GlobalUsage.mEventsModelSelected!!.time
        DB_EventsDetailsScreen.txtEventDesc.text = GlobalUsage.mEventsModelSelected!!.description
    }

    private fun initListners() {
        DB_EventsDetailsScreen.imgBack.setOnClickListener(this)

        val imageList = ArrayList<SlideUIModel>()

        for (link in GlobalUsage.mEventsModelSelected!!.image!!) {
            imageList.add(SlideUIModel(link, " "))
        }
        DB_EventsDetailsScreen.imageSlide.setItemClickListener(object : ItemClickListener {
            override fun onItemClick(model: SlideUIModel, position: Int) {

            }
        })
        DB_EventsDetailsScreen.imageSlide.setImageList(imageList)
        DB_EventsDetailsScreen.imageSlide.startSliding(1000) // with new period
        DB_EventsDetailsScreen.imageSlide.startSliding()

    }

    override fun onClick(view: View?) {
        try {
            if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
                GlobalUsage.lastClick =
                    System.currentTimeMillis() + GlobalUsage.clickInterval
                if (view == DB_EventsDetailsScreen.imgBack) {
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
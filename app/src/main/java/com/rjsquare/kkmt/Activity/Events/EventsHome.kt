package com.rjsquare.kkmt.Activity.Events

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.utils.DateUtils
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityEventsHomeBinding
import java.util.*


class EventsHome : AppCompatActivity(), View.OnClickListener, OnDayClickListener {
    var IsEventCallavailable = true

    companion object {

        lateinit var DB_EventsHome: ActivityEventsHomeBinding
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_EventsHome = DataBindingUtil.setContentView(this, R.layout.activity_events_home)
        try {
            GlobalUsage.StatusTextWhite(this, true)

            Handler().postDelayed({
                initListners()
                setUpPagerAdapter()
                IsEventCallavailable = true
            }, 50)

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

    private fun setUpPagerAdapter() {
        val sectionsPagerReviewAdapter =
            SectionsPagereventsReviewAdapter(this, supportFragmentManager)
        DB_EventsHome.viewPager.adapter = sectionsPagerReviewAdapter
        DB_EventsHome.viewPager.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // Check if this is the page you want.

//                    if (position == 0) {
//                        ReviewList.DB_ReviewList.txtPendingReview.background =
//                            ContextCompat.getDrawable(
//                                this@ReviewList,
//                                R.drawable.tab_selection
//                            )
//                        ReviewList.DB_ReviewList.txtCompleteReview.background = null
//                        ReviewList.DB_ReviewList.txtPendingReview.setTextColor(
//                            ContextCompat.getColor(
//                                this@ReviewList,
//                                R.color.black
//                            )
//                        )
//                        ReviewList.DB_ReviewList.txtCompleteReview.setTextColor(
//                            ContextCompat.getColor(
//                                this@ReviewList,
//                                R.color.white
//                            )
//                        )
//                    } else if (position == 1) {
//                        ReviewList.DB_ReviewList.txtCompleteReview.background =
//                            ContextCompat.getDrawable(
//                                this@ReviewList,
//                                R.drawable.tab_selection
//                            )
//                        ReviewList.DB_ReviewList.txtPendingReview.background = null
//                        ReviewList.DB_ReviewList.txtCompleteReview.setTextColor(
//                            ContextCompat.getColor(
//                                this@ReviewList,
//                                R.color.black
//                            )
//                        )
//                        ReviewList.DB_ReviewList.txtPendingReview.setTextColor(
//                            ContextCompat.getColor(
//                                this@ReviewList,
//                                R.color.white
//                            )
//                        )
//                    }
            }
        })
    }

    private fun initListners() {
        DB_EventsHome.imgBack.setOnClickListener(this)

        DB_EventsHome.txtUnauthOk.setOnClickListener(this)
    }

    private fun getDisabledDays(): List<Calendar>? {
        val firstDisabled =
            DateUtils.getCalendar()
        firstDisabled.add(Calendar.DAY_OF_MONTH, 2)
        val secondDisabled =
            DateUtils.getCalendar()
        secondDisabled.add(Calendar.DAY_OF_MONTH, 1)
        val thirdDisabled =
            DateUtils.getCalendar()
        thirdDisabled.add(Calendar.DAY_OF_MONTH, 18)
        val calendars: MutableList<Calendar> =
            java.util.ArrayList()
        calendars.add(firstDisabled)
        calendars.add(secondDisabled)
        calendars.add(thirdDisabled)
        return calendars
    }

    private fun getRandomCalendar(): Calendar? {
        val random = Random()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, random.nextInt(99))
        return calendar
    }

//    private fun filldata() {
//        try {
//            mEventsModel = EventsModel()
//            mEventsModel.EventTitle = "My Biggest Lesson"
//            mEventsModel.EventTime = "Posted 1h ago"
//            mEventsModel.EventDate = "Saturday 1st January 2020"
//            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event1)!!
//            mArray_EventsModel.add(mEventsModel)
//
//            mEventsModel = EventsModel()
//            mEventsModel.EventTitle = "More Than Money"
//            mEventsModel.EventTime = "Posted 3 days ago"
//            mEventsModel.EventDate = "Saturday 8th January 2020"
//            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event2)!!
//            mArray_EventsModel.add(mEventsModel)
//
//            mEventsModel = EventsModel()
//            mEventsModel.EventTitle = "So Fascinating"
//            mEventsModel.EventTime = "Posted 3 days ago"
//            mEventsModel.EventDate = "Saturday 1st January 2020"
//            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event3)!!
//            mArray_EventsModel.add(mEventsModel)
//
//            mEventsModel = EventsModel()
//            mEventsModel.EventTitle = "Shake It Up"
//            mEventsModel.EventTime = "Posted 1h ago"
//            mEventsModel.EventDate = "Sunday 9th January 2020"
//            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event4)!!
//            mArray_EventsModel.add(mEventsModel)
//
//            mEventsModel = EventsModel()
//            mEventsModel.EventTitle = "Old is gold"
//            mEventsModel.EventTime = "Posted 1h ago"
//            mEventsModel.EventDate = "Saturday 1st January 2020"
//            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event5)!!
//            mArray_EventsModel.add(mEventsModel)
//
//            mEventsModel = EventsModel()
//            mEventsModel.EventTitle = "Task Masters"
//            mEventsModel.EventTime = "Posted 3 days ago"
//            mEventsModel.EventDate = "Saturday 15th January 2020"
//            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event6)!!
//            mArray_EventsModel.add(mEventsModel)
//
//            framesAdapter()
//        } catch (NE: NullPointerException) {
//            NE.printStackTrace()
//        } catch (IE: IndexOutOfBoundsException) {
//            IE.printStackTrace()
//        } catch (AE: ActivityNotFoundException) {
//            AE.printStackTrace()
//        } catch (E: IllegalArgumentException) {
//            E.printStackTrace()
//        } catch (RE: RuntimeException) {
//            RE.printStackTrace()
//        } catch (E: Exception) {
//            E.printStackTrace()
//        }
//    }


    override fun onClick(view: View?) {
        if (System.currentTimeMillis() < GlobalUsage.lastClick) return else {
            GlobalUsage.lastClick = System.currentTimeMillis() + GlobalUsage.clickInterval
            if (view == DB_EventsHome.imgBack) {
                onBackPressed()
            } else if (view == DB_EventsHome.txtUnauthOk) {
                DB_EventsHome.cntUnAuthorized.visibility = View.GONE
                GlobalUsage.UserLogout(this)
            }
        }
//        else if (view == DB_EventsHome.cntLoadmore) {
////            IsEventCallavailable = true
//            if (IsEventCallavailable) {
//                DB_EventsHome.cntLoader.visibility = View.VISIBLE
//                GetLatestEvents((++PageNo).toString(), PagePerlimit.toString())
//            }
//        }
    }

    override fun onDayClick(eventDay: EventDay?) {

    }


}
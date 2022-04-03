package com.rjsquare.kkmt.Activity.Events

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.utils.DateUtils
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Video.Video
import com.rjsquare.kkmt.Adapter.EventsAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.EventsModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.EventsData
import com.rjsquare.kkmt.RetrofitInstance.Events.EventsService
import com.rjsquare.kkmt.RetrofitInstance.Events.VideosService
import com.rjsquare.kkmt.RetrofitInstance.Events.Events_Model
import com.rjsquare.kkmt.databinding.ActivityEventsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class Events : AppCompatActivity(), View.OnClickListener, OnDayClickListener {
//    lateinit var mEventsModel: EventsData
    lateinit var mEventsModel: EventsModel
    lateinit var mArray_EventsModel: ArrayList<EventsModel>
//    lateinit var mArray_EventsModel: ArrayList<EventsData>
    lateinit var DB_Events: ActivityEventsBinding
    var PageNo = 0
    var dataSize = 0
    var IsEventCallavailable = false

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_Events = DataBindingUtil.setContentView(this, R.layout.activity_events)
//        setContentView(R.layout.activity_events)
        try {
            ApplicationClass.StatusTextWhite(this, true)
            mArray_EventsModel = ArrayList()
            DB_Events.imgBack.setOnClickListener(this)


//            val events: MutableList<EventDay> = java.util.ArrayList()
//
//            val calendar = Calendar.getInstance()
//            events.add(EventDay(calendar, R.drawable.dot))
//
//            val calendar1 = Calendar.getInstance()
//            calendar1.set(Calendar.DAY_OF_MONTH, 10)
//            calendar1.set(Calendar.MONTH, Calendar.NOVEMBER)
//            calendar1.set(Calendar.YEAR, 2020)
//            events.add(EventDay(calendar1, R.drawable.dot))
//
//            val calendar2 = Calendar.getInstance()
//            calendar2.set(Calendar.DAY_OF_MONTH, 11)
//            events.add(
//                EventDay(
//                    calendar2,
//                    R.drawable.dot,
//                    Color.parseColor("#228B22")
//                )
//            )
//
//            val calendar3 = Calendar.getInstance()
//            calendar3.set(Calendar.DAY_OF_MONTH, 7)
//            events.add(EventDay(calendar3, R.drawable.dot))
//
//            val calendar4 = Calendar.getInstance()
//            calendar4.set(Calendar.DAY_OF_MONTH, 13)
//            events.add(EventDay(calendar4, R.drawable.dot))
//
//            val calendarView =
//                findViewById<View>(R.id.calendarView) as CalendarView
//
//            val min = Calendar.getInstance()
//            min.add(Calendar.MONTH, -12)
//
//            val max = Calendar.getInstance()
//            max.add(Calendar.MONTH, 12)
//
//            calendarView.setMinimumDate(min)
//            calendarView.setMaximumDate(max)
//
//            calendarView.setEvents(events)
//
////        calendarView.setDisabledDays(getDisabledDays())
//
//            calendarView.setOnDayClickListener { eventDay: EventDay ->
//                Toast.makeText(
//                    applicationContext,
//                    eventDay.calendar.time.toString() + " "
//                            + eventDay.isEnabled,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }

            DB_Events.txtUnauthOk.setOnClickListener(this)
            IsEventCallavailable = false
//            DB_Events.cntLoader.visibility = View.VISIBLE
            filldata()
//            framesAdapter()
//            GetLatestEvents((++PageNo).toString())

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

    private fun GetLatestEvents(pageNo: String) {
//        try {
//
//            //Here the json data is add to a hash map with key data
//            val params: MutableMap<String, String> =
//                HashMap()
//            params[ApplicationClass.paramKey_PageNo] = pageNo
////                ApplicationClass.userInfoModel.data!!.userid.toString()
////            params[ApplicationClass.paramKey_Selfie] = fileString
//
//            val service =
//                ApiCallingInstance.retrofitInstance.create<EventsService>(
//                    EventsService::class.java
//                )
//            val call =
//                service.GetEventsData(
//                    params
//                )
//
//            call.enqueue(object : Callback<Events_Model> {
//                override fun onFailure(call: Call<Events_Model>, t: Throwable) {
//                    DB_Events.cntLoader.visibility = View.GONE
//                    Log.e("GetResponsesasXASX", "Hell: ")
//                }
//
//                override fun onResponse(
//                    call: Call<Events_Model>,
//                    response: Response<Events_Model>
//                ) {
//                    DB_Events.cntLoader.visibility = View.GONE
//                    if (response.body()!!.status.equals(ApplicationClass.ResponseSucess)) {
//                        dataSize = response.body()!!.data!!.size
//                        mArray_EventsModel.addAll(response.body()!!.data!!)
//                        DB_Events.rrEvents.adapter!!.notifyDataSetChanged()
//                        IsEventCallavailable = true
//                        Log.e("TAG","Sizeofdata : "+mArray_EventsModel.size)
//                        if (mArray_EventsModel.size > 0) {
//                            DB_Events.txtNoEvents.visibility = View.GONE
//                        } else {
//                            DB_Events.txtNoEvents.visibility = View.VISIBLE
//                        }
//                    } else if (response.body()!!.status.equals(ApplicationClass.ResponseUnauthorized)) {
//                        DB_Events.cntUnAuthorized.visibility = View.VISIBLE
//                    } else {
//
//                    }
//                }
//            })
//        } catch (E: Exception) {
//            print(E)
//        } catch (NE: NullPointerException) {
//            print(NE)
//        } catch (IE: IndexOutOfBoundsException) {
//            print(IE)
//        } catch (IE: IllegalStateException) {
//            print(IE)
//        } catch (AE: ActivityNotFoundException) {
//            print(AE)
//        } catch (KNE: KotlinNullPointerException) {
//            print(KNE)
//        } catch (CE: ClassNotFoundException) {
//            print(CE)
//        }
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

    private fun filldata() {
        try {
            mEventsModel = EventsModel()
            mEventsModel.EventTitle = "My Biggest Lesson"
            mEventsModel.EventTime = "Posted 1h ago"
            mEventsModel.EventDate = "Saturday 1st January 2020"
            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event1)!!
            mArray_EventsModel.add(mEventsModel)

            mEventsModel = EventsModel()
            mEventsModel.EventTitle = "More Than Money"
            mEventsModel.EventTime = "Posted 3 days ago"
            mEventsModel.EventDate = "Saturday 8th January 2020"
            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event2)!!
            mArray_EventsModel.add(mEventsModel)

            mEventsModel = EventsModel()
            mEventsModel.EventTitle = "So Fascinating"
            mEventsModel.EventTime = "Posted 3 days ago"
            mEventsModel.EventDate = "Saturday 1st January 2020"
            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event3)!!
            mArray_EventsModel.add(mEventsModel)

            mEventsModel = EventsModel()
            mEventsModel.EventTitle = "Shake It Up"
            mEventsModel.EventTime = "Posted 1h ago"
            mEventsModel.EventDate = "Sunday 9th January 2020"
            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event4)!!
            mArray_EventsModel.add(mEventsModel)

            mEventsModel = EventsModel()
            mEventsModel.EventTitle = "Old is gold"
            mEventsModel.EventTime = "Posted 1h ago"
            mEventsModel.EventDate = "Saturday 1st January 2020"
            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event5)!!
            mArray_EventsModel.add(mEventsModel)

            mEventsModel = EventsModel()
            mEventsModel.EventTitle = "Task Masters"
            mEventsModel.EventTime = "Posted 3 days ago"
            mEventsModel.EventDate = "Saturday 15th January 2020"
            mEventsModel.ImgLink = ContextCompat.getDrawable(this, R.drawable.event6)!!
            mArray_EventsModel.add(mEventsModel)

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


    fun framesAdapter() {
        try {
//            mArray_EventsModel = ArrayList()
            Log.e("TAG","Size of list : "+mArray_EventsModel.size)
            if (mArray_EventsModel != null && mArray_EventsModel.size > 0) {
                DB_Events.txtNoEvents.visibility = View.GONE
            } else {
                DB_Events.txtNoEvents.visibility = View.VISIBLE
            }
            val loEventsAdapter: EventsAdapter
            loEventsAdapter = EventsAdapter(
                this, mArray_EventsModel
            )

//            val linearLayoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            DB_Events.rrEvents.layoutManager = linearLayoutManager
//            mRrEvents.setLayoutManager(GridLayoutManager(this, 2))
            DB_Events.rrEvents.adapter = loEventsAdapter

//            DB_Events.rrEvents.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    val layoutManager =
//                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
//                    val totalItemCount = layoutManager.itemCount
//                    val lastVisible = layoutManager.findLastVisibleItemPosition()
//                    val endHasBeenReached = lastVisible + 5 >= totalItemCount
//                    Log.e("TAG","POSITION : "+totalItemCount)
//                    Log.e("TAG","LastPOSITION : "+lastVisible)
//                    if (totalItemCount > 0 && endHasBeenReached) {
//                        //you have reached to the bottom of your recycler view
//                        Log.e("TAG", "RECYCLERVIEWLASTITEM")
//                    }
//                    if ((totalItemCount-1) == lastVisible && IsEventCallavailable && dataSize == 10){
//                        IsEventCallavailable = false
//                        GetLatestEvents((++PageNo).toString())
//                    }
//                }
//            })
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
        if (view == DB_Events.imgBack) {
            onBackPressed()
        }else if (view == DB_Events.txtUnauthOk) {
            DB_Events.cntUnAuthorized.visibility = View.GONE
            ApplicationClass.UserLogout(this)
        }
    }

    override fun onDayClick(eventDay: EventDay?) {

    }


}
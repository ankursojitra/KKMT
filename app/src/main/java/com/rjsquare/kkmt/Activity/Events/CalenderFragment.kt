package com.rjsquare.kkmt.Activity.Events

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.Network
import com.rjsquare.kkmt.Activity.Dialog.UnAuthorized
import com.rjsquare.kkmt.Adapter.EventsByMonthAdapter
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.AppConstant.GlobalUsage
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.EventsByMonth_Model
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.databinding.FragmentCalenderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CalenderFragment : Fragment() {

    lateinit var mArray_EventsByMonthModel: ArrayList<EventsByMonth_Model.EventsData>
    lateinit var mArray_EventsByDateModel: ArrayList<EventsByMonth_Model.EventsData.DateWiseEvents>
    lateinit var mEventsByMonth_Hash: HashMap<String, ArrayList<EventsByMonth_Model.EventsData>>
    var Month = 0
    lateinit var calendarView: CalendarView
    lateinit var events: MutableList<EventDay>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DB_CalenderFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_calender, container, false)
        var rootView = DB_CalenderFragment.root
        // Inflate the layout for this fragment

        initListners(rootView)

        framesAdapter()
        if (!GlobalUsage.IsNetworkAvailable(requireActivity())) {
            Network.showDialog(requireActivity())
        } else GetLatestEvents((Month).toString())

        return rootView
    }

    private fun initListners(rootView: View) {

        mArray_EventsByMonthModel = ArrayList()
        events = ArrayList()

        mArray_EventsByDateModel = ArrayList()
        mEventsByMonth_Hash = HashMap()
        calendarView = rootView.findViewById<View>(R.id.calendarView) as CalendarView

        //Setup minimum date in calender
        val min = Calendar.getInstance()
        min.add(Calendar.MONTH, 0)
        min.add(Calendar.DAY_OF_MONTH, -1)
        calendarView.setMinimumDate(min)

        //Setup maximum date in calender
        val max = Calendar.getInstance()
        max.add(Calendar.MONTH, 12)
//        calendarView.setMaximumDate(max)

        calendarView.setOnForwardPageChangeListener {
            val calender = calendarView.currentPageDate.time
            GetSelectedMonthsEvents((++Month).toString(), calender)
        }
        calendarView.setOnPreviousPageChangeListener {
            val calender = calendarView.currentPageDate.time
            GetSelectedMonthsEvents((--Month).toString(), calender)
        }
        calendarView.setOnDayClickListener { eventDay: EventDay ->
            val cal = eventDay.calendar
            val dayFormat = SimpleDateFormat("dd")
            val calenderDate = (dayFormat.format(cal.time))
            mArray_EventsByDateModel = ArrayList()

            for (DateEvent in mArray_EventsByMonthModel) {

                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                var EventDate = dateFormat.parse(DateEvent.date.toString())
                val dayDate = dayFormat.format(EventDate)
                if (dayDate.equals(calenderDate, true)) {
                    mArray_EventsByDateModel.addAll(DateEvent.events!!)
                }
            }
            framesAdapter()
        }

        val dateFormat: DateFormat = SimpleDateFormat("MM")
        val date = Date()
        Month = dateFormat.format(date).toInt()
    }

    private fun GetSelectedMonthsEvents(month: String, calender: Date) {
        Handler().postDelayed({

            val frmt = SimpleDateFormat("yyyy-MM")
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            var EventDate = frmt.format(calender)
//            var EventDay = frmt.format(EventDate)
            Log.e("TAG", "GetSelected Dates : " + EventDate)
            if (mEventsByMonth_Hash.containsKey(EventDate)) {
                mArray_EventsByMonthModel = ArrayList()
                mArray_EventsByMonthModel.addAll(mEventsByMonth_Hash.get(EventDate)!!)
                SetEvents()

            } else {
                GetLatestEvents(month)
            }
        }, Constants.delayStart)
    }

    private fun SetEvents() {
        events = ArrayList()
        val dateMonthFormat: DateFormat = SimpleDateFormat("MM")
        val dateDayFormat: DateFormat = SimpleDateFormat("dd")
        val dateYearFormat: DateFormat = SimpleDateFormat("yyyy")
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        for (event in mArray_EventsByMonthModel) {
            var EventDate = dateFormat.parse(event.date.toString())
            var EventDay = dateDayFormat.format(EventDate)
            var EventMonth = dateMonthFormat.format(EventDate)
            var EventYear = dateYearFormat.format(EventDate)

//            event.date
            val calender = Calendar.getInstance()
            calender.set(Calendar.DAY_OF_MONTH, EventDay.toInt())
            calender.set(Calendar.MONTH, GetMonth(EventMonth.toInt()))
            calender.set(Calendar.YEAR, EventYear.toInt())
            events.add(EventDay(calender, R.drawable.dot))
        }
        calendarView.setEvents(events)

    }

    private fun GetMonth(month: Int): Int {
        when (month) {
            1 -> return Calendar.JANUARY
            2 -> return Calendar.FEBRUARY
            3 -> return Calendar.MARCH
            4 -> return Calendar.APRIL
            5 -> return Calendar.MAY
            6 -> return Calendar.JUNE
            7 -> return Calendar.JULY
            8 -> return Calendar.AUGUST
            9 -> return Calendar.SEPTEMBER
            10 -> return Calendar.OCTOBER
            11 -> return Calendar.NOVEMBER
            12 -> return Calendar.DECEMBER
            else -> return Calendar.JANUARY
        }
    }

    private fun GetLatestEvents(month: String) {
        try {
            DB_CalenderFragment.gifLoader.visibility = View.VISIBLE
            mArray_EventsByMonthModel = ArrayList()
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            val dateFormat: DateFormat = SimpleDateFormat("yyyy")
            val date = Date()
            Log.d("Month", dateFormat.format(date))
            params[Constants.paramKey_UserId] =
                GlobalUsage.userInfoModel.data!!.userid.toString()
            params[Constants.paramKey_Month] = month
            params[Constants.paramKey_Year] = dateFormat.format(date).toString()

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.EventsByMonthService>(
                    NetworkServices.EventsByMonthService::class.java
                )
            val call =
                service.GetEventsByMonthData(
                    params
                )

            call.enqueue(object : Callback<EventsByMonth_Model> {
                override fun onFailure(call: Call<EventsByMonth_Model>, t: Throwable) {
                    DB_CalenderFragment.gifLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<EventsByMonth_Model>,
                    response: Response<EventsByMonth_Model>
                ) {
                    DB_CalenderFragment.gifLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        mArray_EventsByMonthModel.addAll(response.body()!!.data!!)
                        if (!mArray_EventsByMonthModel.isNullOrEmpty()) {
                            val frmt = SimpleDateFormat("yyyy-MM")
                            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                            var EventDate =
                                dateFormat.parse(response.body()!!.data!![0].date.toString())
                            var EventDay = frmt.format(EventDate)
                            mEventsByMonth_Hash.put(EventDay, mArray_EventsByMonthModel)
                        }
                        SetEvents()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        UnAuthorized.showDialog(requireActivity())
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {

                    }
                }
            })
        } catch (E: Exception) {
            print(E)
        } catch (NE: NullPointerException) {
            print(NE)
        } catch (IE: IndexOutOfBoundsException) {
            print(IE)
        } catch (IE: IllegalStateException) {
            print(IE)
        } catch (AE: ActivityNotFoundException) {
            print(AE)
        } catch (KNE: KotlinNullPointerException) {
            print(KNE)
        } catch (CE: ClassNotFoundException) {
            print(CE)
        }
    }

    fun framesAdapter() {
        try {
            if (mArray_EventsByDateModel != null && mArray_EventsByDateModel.size > 0) {
                DB_CalenderFragment.txtNoEvents.visibility = View.GONE
            } else {
                DB_CalenderFragment.txtNoEvents.visibility = View.VISIBLE
            }
            val loEventsAdapter: EventsByMonthAdapter
            loEventsAdapter = EventsByMonthAdapter(
                requireActivity(), mArray_EventsByDateModel
            )

            val linearLayoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            DB_CalenderFragment.rrEvents.layoutManager = linearLayoutManager

            DB_CalenderFragment.rrEvents.adapter = loEventsAdapter
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

    companion object {
        lateinit var DB_CalenderFragment: FragmentCalenderBinding


        @JvmStatic
        fun newInstance() =
            CalenderFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
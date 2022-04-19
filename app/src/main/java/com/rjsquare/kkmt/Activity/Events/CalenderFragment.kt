package com.rjsquare.kkmt.Activity.Events

import android.content.ActivityNotFoundException
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Adapter.EventsAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.Events_Model
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.databinding.FragmentCalenderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CalenderFragment : Fragment() {

    lateinit var mArray_EventsModel: ArrayList<Events_Model.EventsData>
    var Month = 0

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
        mArray_EventsModel = ArrayList()
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_calender, container, false)
        val dateFormat: DateFormat = SimpleDateFormat("MM")
        val date = Date()
        Month = dateFormat.format(date).toInt()

        val events: MutableList<EventDay> = java.util.ArrayList()

        val calendar = Calendar.getInstance()
        events.add(EventDay(calendar, R.drawable.dot))

        val calendar1 = Calendar.getInstance()
        calendar1.set(Calendar.DAY_OF_MONTH, 10)
        calendar1.set(Calendar.MONTH, Calendar.NOVEMBER)
        calendar1.set(Calendar.YEAR, 2022)
        events.add(EventDay(calendar1, R.drawable.dot))

        val calendar2 = Calendar.getInstance()
        calendar2.set(Calendar.DAY_OF_MONTH, 11)
        events.add(
            EventDay(
                calendar2,
                R.drawable.dot,
                Color.parseColor("#228B22")
            )
        )

        val calendar3 = Calendar.getInstance()
        calendar3.set(Calendar.DAY_OF_MONTH, 7)
        events.add(EventDay(calendar3, R.drawable.dot))

        val calendar4 = Calendar.getInstance()
        calendar4.set(Calendar.DAY_OF_MONTH, 13)
        events.add(EventDay(calendar4, R.drawable.dot))

        val calendarView =
            rootView.findViewById<View>(R.id.calendarView) as CalendarView

        val min = Calendar.getInstance()
        min.add(Calendar.MONTH, 0)
        min.add(Calendar.DAY_OF_MONTH, -1)

        val max = Calendar.getInstance()
        max.add(Calendar.MONTH, 12)

        calendarView.setMinimumDate(min)
        calendarView.setMaximumDate(max)

        calendarView.setEvents(events)

//        calendarView.setDisabledDays(getDisabledDays())
        calendarView.setOnForwardPageChangeListener {
            GetLatestEvents((++Month).toString())
            Toast.makeText(requireActivity(), "Next", Toast.LENGTH_LONG).show()
        }
        calendarView.setOnPreviousPageChangeListener {
            GetLatestEvents((--Month).toString())
            Toast.makeText(requireActivity(), "Previous", Toast.LENGTH_LONG).show()
        }
        calendarView.setOnDayClickListener { eventDay: EventDay ->
            Toast.makeText(
                requireContext(),
                eventDay.calendar.time.toString() + " "
                        + eventDay.isEnabled,
                Toast.LENGTH_SHORT
            ).show()
        }

        framesAdapter()
        GetLatestEvents((Month).toString())
        return rootView
    }

    private fun GetLatestEvents(month: String) {
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            val dateFormat: DateFormat = SimpleDateFormat("yyyy")
            val date = Date()
            Log.d("Month", dateFormat.format(date))
            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid.toString()
            params[Constants.paramKey_Month] = month
            params[Constants.paramKey_Year] = dateFormat.format(date).toString()
//                ApplicationClass.userInfoModel.data!!.userid.toString()
//            params[ApplicationClass.paramKey_Selfie] = fileString

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.EventsByMonthService>(
                    NetworkServices.EventsByMonthService::class.java
                )
            val call =
                service.GetEventsByMonthData(
                    params
                )

            call.enqueue(object : Callback<Events_Model> {
                override fun onFailure(call: Call<Events_Model>, t: Throwable) {
//                    DB_CalenderFragment.gifLoader.visibility = View.GONE
                    Log.e("GetResponsesasXASX", "Hell: ")
                }

                override fun onResponse(
                    call: Call<Events_Model>,
                    response: Response<Events_Model>
                ) {

                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        mArray_EventsModel = ArrayList()
                        mArray_EventsModel.addAll(response.body()!!.data!!)
                        DB_CalenderFragment.rrEvents.adapter!!.notifyDataSetChanged()

                        if (mArray_EventsModel.size > 0) {
                            DB_CalenderFragment.txtNoEvents.visibility = View.GONE
                        } else {
                            DB_CalenderFragment.txtNoEvents.visibility = View.VISIBLE
                        }
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        Events.DB_Events.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {
//                        DB_Events.cntLoadmore.visibility = View.GONE
//                        IsEventCallavailable = false
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
//            mArray_EventsModel = ArrayList()
            Log.e("TAG", "Size of list : " + mArray_EventsModel.size)
            if (mArray_EventsModel != null && mArray_EventsModel.size > 0) {
                DB_CalenderFragment.txtNoEvents.visibility = View.GONE
            } else {
                DB_CalenderFragment.txtNoEvents.visibility = View.VISIBLE
            }
            val loEventsAdapter: EventsAdapter
            loEventsAdapter = EventsAdapter(
                requireActivity(), mArray_EventsModel
            )

//            val linearLayoutManager =
//                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            DB_Events.rrEvents.layoutManager = linearLayoutManager
//            mRrEvents.setLayoutManager(GridLayoutManager(this, 2))
            DB_CalenderFragment.rrEvents.adapter = loEventsAdapter

//            EventListFragment.DB_EventListFragment.rrEvents.addOnScrollListener(object :
//                RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    val layoutManager =
//                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
//                    val totalItemCount = layoutManager.itemCount
//                    val lastVisible = layoutManager.findLastVisibleItemPosition()
//                    val endHasBeenReached = lastVisible + 5 >= totalItemCount
//                    Log.e("TAG", "POSITION : " + totalItemCount)
//                    Log.e("TAG", "LastPOSITION : " + lastVisible)
//                    if (totalItemCount > 0 && endHasBeenReached) {
//                        //you have reached to the bottom of your recycler view
//                        Log.e("TAG", "RECYCLERVIEWLASTITEM")
//                    }
//                    if ((totalItemCount - 1) == lastVisible && IsEventCallavailable && dataSize == PagePerlimit) {
//                        IsEventCallavailable = true
//                        GetLatestEvents((++PageNo).toString(), PagePerlimit.toString())
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
package com.rjsquare.kkmt.Activity.Events

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Dialog.UnAuthorized
import com.rjsquare.kkmt.Adapter.EventsAdapter
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.Events_Model
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.databinding.FragmentEventListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventListFragment : Fragment() {

    var PageNo = 0
    var PagePerlimit = 10
    var dataSize = 0
    var IsEventCallavailable = true
    lateinit var mArray_EventsModel: ArrayList<Events_Model.EventsData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DB_EventListFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_event_list, container, false)
        var rootView = DB_EventListFragment.root

        mArray_EventsModel = ArrayList()

        framesAdapter()
        GetLatestEvents((++PageNo).toString(), PagePerlimit.toString())
        return rootView
    }

    fun framesAdapter() {
        try {

            if (mArray_EventsModel != null && mArray_EventsModel.size > 0) {
                DB_EventListFragment.txtNoEvents.visibility = View.GONE
            } else {
                DB_EventListFragment.txtNoEvents.visibility = View.VISIBLE
            }
            val loEventsAdapter: EventsAdapter
            loEventsAdapter = EventsAdapter(
                requireActivity(), mArray_EventsModel
            )
            DB_EventListFragment.rrEvents.adapter = loEventsAdapter

            DB_EventListFragment.rrEvents.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 5 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached) {
                        //you have reached to the bottom of your recycler view
                    }
                    if ((totalItemCount - 1) == lastVisible && IsEventCallavailable && dataSize == PagePerlimit) {
                        IsEventCallavailable = true
                        GetLatestEvents((++PageNo).toString(), PagePerlimit.toString())
                    }
                }
            })
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


    private fun GetLatestEvents(pageNo: String, PagePerlimit: String) {
        try {
            DB_EventListFragment.gifLoader.visibility = View.VISIBLE
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()
            params[Constants.paramKey_PageNo] = pageNo
            params[Constants.paramKey_limit] = PagePerlimit

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.EventsService>(
                    NetworkServices.EventsService::class.java
                )
            val call =
                service.GetEventsData(
                    params
                )

            call.enqueue(object : Callback<Events_Model> {
                override fun onFailure(call: Call<Events_Model>, t: Throwable) {
                    DB_EventListFragment.gifLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<Events_Model>,
                    response: Response<Events_Model>
                ) {
                    DB_EventListFragment.gifLoader.visibility = View.GONE
                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        dataSize = response.body()!!.data!!.size
                        mArray_EventsModel.addAll(response.body()!!.data!!)
                        DB_EventListFragment.rrEvents.adapter!!.notifyDataSetChanged()

                        IsEventCallavailable = response.body()!!.data!!.size >= this@EventListFragment.PagePerlimit

                        if (mArray_EventsModel.size > 0) {
                            DB_EventListFragment.txtNoEvents.visibility = View.GONE
                        } else {
                            DB_EventListFragment.txtNoEvents.visibility = View.VISIBLE
                        }
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        UnAuthorized.showDialog(requireActivity())
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {
                        IsEventCallavailable = false
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


    companion object {
        lateinit var DB_EventListFragment: FragmentEventListBinding


        @JvmStatic
        fun newInstance() =
            EventListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
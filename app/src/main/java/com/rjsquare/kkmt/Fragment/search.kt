package com.rjsquare.kkmt.Fragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.rishabhharit.roundedimageview.RoundedImageView
import com.rjsquare.kkmt.Activity.HomeActivity
import com.rjsquare.kkmt.Adapter.SearchAdapter
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.SearchModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.view_bottom_sheet.view.*


class search : Fragment(), DialogInterface.OnCancelListener, View.OnClickListener,
    OnMapReadyCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    //    private lateinit var mRrSearchdata: RecyclerView
    lateinit var mSearchModel: SearchModel
    lateinit var mArray_SearchModel: ArrayList<SearchModel>
//    private lateinit var mCntBottom: ConstraintLayout
//    private lateinit var mCntOutside: ConstraintLayout


    //    var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DB_FSearch = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
//        var rootView = inflater.inflate(R.layout.fragment_search, container, false)
        try {
            activitySearch = requireActivity()

            DB_FSearch.cntOutside.setOnClickListener(this)
//            DB_FSearch.cntOutside.isClickable = false
            DB_FSearch.edtSearchbar.onFocusChangeListener = object : View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {

                    if (hasFocus) {
//                        val y = HomeActivity.YposKeyOff - HomeActivity.EstimatedListHeight\
                        BusinessListUpside(true)

                    } else {
                        BusinessListUpside(false)

//                        DB_FSearch.cntOutside.isClickable = true
                        //                    var otpView = Intent(requireActivity(), Register_User::class.java)
                        //                    startActivity(otpView)
                        //                    requireActivity().overridePendingTransition(
                        //                        R.anim.activity_in,
                        //                        R.anim.activity_out
                        //                    )
                    }
                }
            }

            HomeActivity.ismEdtSearchbarInit = true
            mSearchModel = SearchModel()
            mArray_SearchModel = ArrayList()

//            mMapView = rootView.findViewById<View>(R.id.mapView) as MapView
            DB_FSearch.mapView.onCreate(savedInstanceState)

            BusinessListUpside(false)

            DB_FSearch.mapView.onResume()
            try {
                MapsInitializer.initialize(requireActivity().applicationContext)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            DB_FSearch.mapView.getMapAsync { mMap ->
                googleMap = mMap

                // For showing a move to my location button
//            googleMap.setMyLocationEnabled(true)

                // For dropping a marker at a point on the Map
                val sydney = LatLng((-34).toDouble(), 151.toDouble())
                googleMap!!.addMarker(
                    MarkerOptions().position(sydney).title("Marker Title")
                        .snippet("Marker Description")
                )


                googleMap!!.uiSettings.isCompassEnabled = false
                googleMap!!.uiSettings.isMapToolbarEnabled = false

                try {
                    val success = googleMap!!.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            activitySearch, R.raw.style_json
                        )
                    )
                    if (!success) {
                        Log.e(
                            "TAG",
                            "Style parsing failed."
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                // For zooming automatically to the location of the marker
                val cameraPosition: CameraPosition =
                    CameraPosition.Builder().target(sydney).zoom(12f).build()
                googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }


            val DB_BottomSheet = DB_FSearch.cntBottomView
            mCntBottomView = DB_BottomSheet
            mCntBottomback = DB_BottomSheet.cnt_bottomback
            mImgSearch =DB_BottomSheet.img_search
            mTxtTitle =DB_BottomSheet.txt_title
            mTxtLocationname =DB_BottomSheet.txt_locationname

            DB_BottomSheet.cnt_bottomback.setOnClickListener(this)


            filldata()

            HomeActivity.mCntLoader.visibility = View.GONE
            searchView = true
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
        return DB_FSearch.root
    }

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager: InputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
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

    private fun filldata() {
        try {
            mSearchModel = SearchModel()
            mSearchModel.BackColor =
                ContextCompat.getDrawable(requireActivity(), R.drawable.search_back_blue)!!
            mSearchModel.TxtTitle = "Conrad Place Chicago"
            mSearchModel.TxtAddress = "963 Madison drive Suite 679"
            mSearchModel.ImgLink = ContextCompat.getDrawable(requireActivity(), R.drawable.bus)!!
            mArray_SearchModel.add(mSearchModel)

            mSearchModel = SearchModel()
            mSearchModel.BackColor =
                ContextCompat.getDrawable(requireActivity(), R.drawable.search_back_red)!!
            mSearchModel.TxtTitle = "Hyatt Place, Chicago"
            mSearchModel.TxtAddress = "135, Kamer Station, Jason Street, 9210"
            mSearchModel.ImgLink = ContextCompat.getDrawable(requireActivity(), R.drawable.imgtwo)!!
            mArray_SearchModel.add(mSearchModel)

            mSearchModel = SearchModel()
            mSearchModel.BackColor =
                ContextCompat.getDrawable(requireActivity(), R.drawable.search_back_yellow)!!
            mSearchModel.TxtTitle = "Hyatt Place, Chicago"
            mSearchModel.TxtAddress = "135, Kamer Station, Jason Street, 9210"
            mSearchModel.ImgLink =
                ContextCompat.getDrawable(requireActivity(), R.drawable.imgthree)!!
            mArray_SearchModel.add(mSearchModel)
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
            val loSearchAdapter: SearchAdapter
//                if (mHomeModelArrayList_old == null) {
            loSearchAdapter = SearchAdapter(
                requireActivity(), mArray_SearchModel
            )

            val linearLayoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            DB_FSearch.rrSearchdata.layoutManager = linearLayoutManager
            //            rr_home.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            DB_FSearch.rrSearchdata.adapter = loSearchAdapter
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
        var searchView = false

        lateinit var DB_FSearch: FragmentSearchBinding

        //        lateinit var mCntRr: ConstraintLayout
        lateinit var activitySearch: Activity
//        lateinit var mEdtSearchbar: EditText

        //        lateinit var fragment_bottom_sheet: View
        var isBottom = true

        lateinit var mCntBottomView: ConstraintLayout
        lateinit var mCntBottomback: ConstraintLayout
        lateinit var mImgSearch: RoundedImageView
        lateinit var mTxtTitle: TextView
        lateinit var mTxtLocationname: TextView

        private lateinit var mMap: GoogleMap
        fun BusinessListUpside(b: Boolean) {
            if (b){
                DB_FSearch.cntRr
                    .animate()
                    .x(0f)
                    .y(HomeActivity.keyBoardOn)
                    .duration = HomeActivity.durationTime
            }else{
                DB_FSearch.cntRr
                    .animate()
                    .x(0f)
                    .y(HomeActivity.keyBoardOff)
                    .duration = HomeActivity.durationTime
            }
        }

        fun PrepareBottomSheetView() {
            try {
                mImgSearch.setImageDrawable(ApplicationClass.mSearchModelSelected!!.ImgLink)
                mTxtTitle.text = ApplicationClass.mSearchModelSelected!!.TxtTitle
                mTxtLocationname.text = ApplicationClass.mSearchModelSelected!!.TxtAddress

                mCntBottomView.visibility = View.VISIBLE

            } catch (NE: NullPointerException) {
                NE.printStackTrace()
            } catch (AE: ArrayIndexOutOfBoundsException) {
                AE.printStackTrace()
            } catch (IE: IndexOutOfBoundsException) {
                IE.printStackTrace()
            } catch (AE: ActivityNotFoundException) {
                AE.printStackTrace()
            } catch (IE: IllegalStateException) {
                IE.printStackTrace()
            } catch (RE: RuntimeException) {
                RE.printStackTrace()
            } catch (E: java.lang.Exception) {
                E.printStackTrace()
            }
        }

        fun SlideToDown() {
            var slide: Animation? = null
            slide = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 5.2f
            )
            slide.setDuration(400)
            slide.setFillAfter(true)
            slide.setFillEnabled(true)
            mCntBottomView.startAnimation(slide)
            slide.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    mCntBottomView.clearAnimation()
                    val lp: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
                        mCntBottomView.width, mCntBottomView.height
                    )
                    lp.setMargins(0, mCntBottomView.width, 0, 0)
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                    mCntBottomView.layoutParams = lp
                }
            })
        }

        fun SlideToAbove() {
            var slide: Animation? = null
            slide = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -5.0f
            )
            slide.setDuration(400)
            slide.setFillAfter(true)
            slide.setFillEnabled(true)
            mCntBottomView.startAnimation(slide)
            slide.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    mCntBottomView.clearAnimation()
                    val lp: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
                        mCntBottomView.width, mCntBottomView.height
                    )
                    // lp.setMargins(0, 0, 0, 0);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_TOP)
                    mCntBottomView.layoutParams = lp
                }
            })
        }


        @JvmStatic
        fun newInstance() =
            search().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        val handler = Handler()
//        val runnable = Runnable {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (activity != null) {
//                val mapFragment = requireActivity().supportFragmentManager
//                    .findFragmentById(R.id.map) as SupportMapFragment?
//                mapFragment?.getMapAsync(this)
        }
//        }
//        handler.postDelayed(runnable, 1000)

    }

    override fun onCancel(dialog: DialogInterface?) {

    }

    override fun onClick(view: View?) {
        try {
            if (view == mCntBottomback) {
                mCntBottomView.visibility = View.GONE
            } else if (view == DB_FSearch.cntOutside) {
                hideSoftKeyboard(requireActivity())
                DB_FSearch.edtSearchbar.clearFocus()
//                DB_FSearch.cntOutside.isClickable = false

                BusinessListUpside(false)
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



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        Log.e("TAG", "GETMAP")
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isMapToolbarEnabled = false

        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    activitySearch, R.raw.style_json
                )
            )
            if (!success) {
                Log.e(
                    "TAG",
                    "Style parsing failed."
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
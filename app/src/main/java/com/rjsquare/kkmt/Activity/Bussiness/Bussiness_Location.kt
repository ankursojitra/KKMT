package com.rjsquare.kkmt.Activity.Bussiness

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivityLocationBinding
import java.util.*


class Bussiness_Location : AppCompatActivity(), View.OnClickListener, OnMapReadyCallback {

    //    lateinit var ReportView: View
//    lateinit var ConfirmView: View
//    lateinit var ReportThankYouView: View
    private lateinit var mCh1: CheckBox
    private lateinit var mCh2: CheckBox
    private lateinit var mCh3: CheckBox
    private lateinit var mTxtSubmit: TextView
    private lateinit var mTxtBacktohome: TextView
    private lateinit var mMap: GoogleMap
    private lateinit var mCntBussinessname: ConstraintLayout
    private lateinit var mTxtLeave: TextView

    private lateinit var mImgLogo: ImageView
    private lateinit var mCntConfirm: ConstraintLayout
    private lateinit var mCntNotfind: ConstraintLayout
    private lateinit var mImgClose: ImageView
    private lateinit var mImgBusRepClose: ImageView

    lateinit var DB_BusinessLocation: ActivityLocationBinding

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_BusinessLocation = DataBindingUtil.setContentView(this, R.layout.activity_location)
//        setContentView(R.layout.activity_location)
        try {
            ApplicationClass.StatusTextWhite(this, false)
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(this)

            DB_BusinessLocation.rippleback.startRippleAnimation()
            thisBussiness_Activity = this

//            ReportView = DB_BusinessLocation.layoutBussinessReport as View
//            ConfirmView = DB_BusinessLocation.layoutBussinessConfirm as View
//            ReportThankYouView = DB_BusinessLocation.layoutBussinessThankyou as View

            mCntBussinessname = DB_BusinessLocation.layoutBussinessReport.cntBussinessname
            mImgLogo = DB_BusinessLocation.layoutBussinessConfirm.imgLogo
            mCntConfirm = DB_BusinessLocation.layoutBussinessConfirm.cntConfirm
            mCntNotfind = DB_BusinessLocation.layoutBussinessConfirm.cntNotfind
            mImgClose = DB_BusinessLocation.layoutBussinessConfirm.imgClose
            mImgBusRepClose = DB_BusinessLocation.layoutBussinessReport.imgClose

            mCh1 = DB_BusinessLocation.layoutBussinessReport.ch1
            mCh2 = DB_BusinessLocation.layoutBussinessReport.ch2
            mCh3 = DB_BusinessLocation.layoutBussinessReport.ch3
            mTxtSubmit = DB_BusinessLocation.layoutBussinessReport.txtSubmit
            mTxtBacktohome = DB_BusinessLocation.layoutBussinessThankyou.txtBacktohome
            mTxtLeave = DB_BusinessLocation.layoutBussinessReport.txtLeave

            mCh1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    mCh1.isChecked = true
                    mCh2.isChecked = false
                    mCh3.isChecked = false
                    mCntBussinessname.visibility = View.VISIBLE
                }
            }

            mCh2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    mCh1.isChecked = false
                    mCh2.isChecked = true
                    mCh3.isChecked = false
                    mCntBussinessname.visibility = View.GONE
                }
            }

            mCh3.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    mCh1.isChecked = false
                    mCh2.isChecked = false
                    mCh3.isChecked = true
                    mCntBussinessname.visibility = View.GONE
                }
            }


            mTxtBacktohome.setOnClickListener(this)
            mTxtSubmit.setOnClickListener(this)
            DB_BusinessLocation.cntCompany.setOnClickListener(this)
            DB_BusinessLocation.cntNotfound.setOnClickListener(this)
            mTxtLeave.setOnClickListener(this)
            mCntConfirm.setOnClickListener(this)
            mCntNotfind.setOnClickListener(this)
            mImgClose.setOnClickListener(this)
            mImgBusRepClose.setOnClickListener(this)

            DB_BusinessLocation.cntCompany.visibility = View.GONE

            val handler = Handler()
            val runnable = Runnable {
                foundDevice()
            }
            handler.postDelayed(runnable, 3000)
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

    fun foundDevice() {
        try {
            val R = Random()

            val dx: Float = R.nextFloat()
            val dy: Float = R.nextFloat()

//            var xPosition = (10..200).random().toFloat()
//            var yPosition = (10..500).random().toFloat()
//
//            Log.e("TAG", "XPOSITION : " + xPosition)
//            Log.e("TAG", "YPOSITION : " + yPosition)
//
//
//            val textView = TextView(this)
//            textView.text = "your text Random"
//
//
//            val insertPoint: ViewGroup = DynamicViewChild as ViewGroup
//            insertPoint.addView(
//                mDynamicViewChild,
//                0,
//                ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//                )
//            )

//            val parentLayout = DynamicViewChild as ConstraintLayout
//            val set = ConstraintSet()
//            val textView = TextView(this)
//            textView.text = "your text Random"
//            val childView = ImageView(this)
//            // set view id, else getId() returns -1
//            // set view id, else getId() returns -1
//            textView.id = View.generateViewId()
//
//            mCntCompany.x = 500f
//            mCntCompany.y = 500f
//
//            parentLayout.addView(mCntCompany, 0)
//
//            mCntCompany.x = 300f
//            mCntCompany.y = 400f
//
//            parentLayout.addView(mCntCompany, 1)
//
//
////            parentLayout.addView(textView, 1)
////            parentLayout.addView(textView, 2)
//
//            set.clone(parentLayout)
//            // connect start and end point of views, in this case top of child to top of parent.
//            // connect start and end point of views, in this case top of child to top of parent.
//            set.connect(
//                mCntCompany.id,
//                ConstraintSet.TOP,
//                parentLayout.id,
//                ConstraintSet.TOP
//            )
//
//
//            set.applyTo(parentLayout)
//
//
//            mCntCompany.setVisibility(View.VISIBLE)
//            val timer = Timer()
//            mCntCompany.animate()
//                .x(dx)
//                .y(dy)
//                .setDuration(0)
//                .start()
//            val animatorSet = AnimatorSet()
//            animatorSet.duration = 400
//            animatorSet.interpolator = AccelerateDecelerateInterpolator()
//            val animatorList = ArrayList<Animator>()
//            val scaleXAnimator = ObjectAnimator.ofFloat(mCntCompany, "ScaleX", 0f, 1.2f, 1f)
//            animatorList.add(scaleXAnimator)
//            val scaleYAnimator = ObjectAnimator.ofFloat(mCntCompany, "ScaleY", 0f, 1.2f, 1f)
//            animatorList.add(scaleYAnimator)
//            animatorSet.playTogether(animatorList)
//            animatorSet.start()

            DB_BusinessLocation.cntCompany.visibility = View.VISIBLE
            val timer = Timer()
            DB_BusinessLocation.cntCompany.animate()
                .x(dx)
                .y(dy)
                .setDuration(0)
                .start()
            val animatorSet = AnimatorSet()
            animatorSet.duration = 400
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            val animatorList = ArrayList<Animator>()
            val scaleXAnimator =
                ObjectAnimator.ofFloat(DB_BusinessLocation.cntCompany, "ScaleX", 0f, 1.2f, 1f)
            animatorList.add(scaleXAnimator)
            val scaleYAnimator =
                ObjectAnimator.ofFloat(DB_BusinessLocation.cntCompany, "ScaleY", 0f, 1.2f, 1f)
            animatorList.add(scaleYAnimator)
            animatorSet.playTogether(animatorList)

            animatorSet.start()

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
            if (view == DB_BusinessLocation.cntNotfound) {
                CloseViews()
                DB_BusinessLocation.layoutBussinessReport.BusinessReport.visibility = View.VISIBLE
            } else if (view == mTxtSubmit) {
                if (mCh1.isChecked || mCh2.isChecked || mCh3.isChecked) {
                    CloseViews()
                    DB_BusinessLocation.layoutBussinessThankyou.BusinessThankyou.visibility =
                        View.VISIBLE
                } else {
                    Toast.makeText(
                        this,
                        "Please select atleast one option from above.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (view == mTxtBacktohome) {
                finish()
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
            } else if (view == DB_BusinessLocation.cntCompany) {
                CloseViews()
                DB_BusinessLocation.layoutBussinessConfirm.BusinessConfirm.visibility = View.VISIBLE
            } else if (view == mImgClose) {
                CloseViews()
                DB_BusinessLocation.cntBusMainView.visibility = View.VISIBLE
            } else if (view == mImgBusRepClose) {
                CloseViews()
                DB_BusinessLocation.cntBusMainView.visibility = View.VISIBLE
            } else if (view == mCntConfirm) {
                CloseViews()
                DB_BusinessLocation.cntBusMainView.visibility = View.VISIBLE
                var HelperIntent = Intent(this, BussinessCheckIn::class.java)
                startActivity(HelperIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
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

    private fun CloseViews() {
        DB_BusinessLocation.cntBusMainView.visibility = View.GONE
        DB_BusinessLocation.layoutBussinessConfirm.BusinessConfirm.visibility = View.GONE
        DB_BusinessLocation.layoutBussinessReport.BusinessReport.visibility = View.GONE
        DB_BusinessLocation.layoutBussinessThankyou.BusinessThankyou.visibility = View.GONE
    }

    companion object {
        lateinit var thisBussiness_Activity: Activity
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            mMap = googleMap

            // Add a marker in Sydney and move the camera
            val Trinidad = LatLng(10.3761803, -61.2449877)
//        mMap.addMarker(MarkerOptions().position(Trinidad).title("Trinidad & Tobago"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Trinidad))
//        mMap.uiSettings.isRotateGesturesEnabled = false
//        mMap.uiSettings.isScrollGesturesEnabled = false
//        mMap.uiSettings.isZoomGesturesEnabled = false
            mMap.uiSettings.isCompassEnabled = false
            mMap.uiSettings.isMapToolbarEnabled = false
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Trinidad, 10.0f))

            try {
                val success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        this@Bussiness_Location, R.raw.style_json
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
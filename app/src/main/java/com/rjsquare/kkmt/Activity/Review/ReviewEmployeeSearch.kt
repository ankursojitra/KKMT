package com.rjsquare.kkmt.Activity.Review

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
import com.camerakit.CameraKitView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.rjsquare.kkmt.Activity.Bussiness.BussinessCheckIn
import com.rjsquare.kkmt.Activity.Bussiness.Bussiness_Location
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.ReviewEmp_Model
import com.rjsquare.kkmt.R
import com.skyfishjy.library.RippleBackground
import java.util.*

class ReviewEmployeeSearch : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener,
    CameraKitView.ImageCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var mTxtCompanyname: TextView
    private lateinit var mImgFound: ImageView
    private lateinit var mTxtName: TextView
    private lateinit var mCntHelper: ConstraintLayout
    private lateinit var mCntHelper2: ConstraintLayout
    private lateinit var mrippleBackground: RippleBackground
    private lateinit var mCntNotfound: ConstraintLayout
    lateinit var ReportView: View
    lateinit var ReportThankYouView: View
    private lateinit var mTxtSubmit: TextView
    private lateinit var mTxtBacktohome: TextView
    private lateinit var mCntTopHeader: ConstraintLayout
    private lateinit var mCh1: CheckBox
    private lateinit var mCntCsrpic: ConstraintLayout
    private lateinit var mCh2: CheckBox
    private lateinit var mCh3: CheckBox
    private lateinit var mTxtLeave: TextView

    //    private lateinit var mCameraKitView: CameraKitView
    private lateinit var mImgComplogo: ImageView
    private lateinit var mImgCamera: ImageView

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var thisReviewEmployee: Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_review_employee_search)
        try {
//
//                AppClass.StatusTextWhite(this, true)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            thisReviewEmployee = this
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(this)

            mrippleBackground = findViewById<RippleBackground>(R.id.content)
//        mCameraKitView = findViewById<CameraKitView>(R.id.cameraKitView)

            mrippleBackground.startRippleAnimation()

            ReportView = findViewById<View>(R.id.layout_helper_report)
            ReportThankYouView = findViewById<View>(R.id.layout_helper_thankyou)
            mImgComplogo = findViewById<ImageView>(R.id.img_complogo)
            mTxtSubmit = ReportView.findViewById<TextView>(R.id.txt_submit)
            mTxtBacktohome = ReportThankYouView.findViewById<TextView>(R.id.txt_backtohome)
            mTxtCompanyname = findViewById<TextView>(R.id.txt_companyname)
            mCntNotfound = findViewById<ConstraintLayout>(R.id.cnt_notfound)
            mImgFound = findViewById<ImageView>(R.id.img_found)
            mTxtName = findViewById<TextView>(R.id.txt_name)
            mCntHelper = findViewById<ConstraintLayout>(R.id.cnt_helper)
            mCntHelper2 = findViewById<ConstraintLayout>(R.id.cnt_helper2)
            mCntTopHeader = findViewById<ConstraintLayout>(R.id.cnt_top_header)
            mCntCsrpic = findViewById<ConstraintLayout>(R.id.cnt_csrpic)
            mImgCamera = findViewById<ImageView>(R.id.img_camera)
            mTxtLeave = findViewById<TextView>(R.id.txt_leave)


            mCh1 = findViewById<CheckBox>(R.id.ch_1)
            mCh2 = findViewById<CheckBox>(R.id.ch_2)
            mCh3 = findViewById<CheckBox>(R.id.ch_3)


//            mCh1.setOnCheckedChangeListener { buttonView, isChecked ->
//                if (isChecked) {
//                    mCh1.isChecked = true
//                    mCh2.isChecked = false
//                    mCh3.isChecked = false
//                    mCntCsrpic.visibility = View.VISIBLE
//                }
//            }
//
//            mCh2.setOnCheckedChangeListener { buttonView, isChecked ->
//                if (isChecked) {
//                    mCh1.isChecked = false
//                    mCh2.isChecked = true
//                    mCh3.isChecked = false
//                    mCntCsrpic.visibility = View.GONE
//                }
//            }
//
//            mCh3.setOnCheckedChangeListener { buttonView, isChecked ->
//                if (isChecked) {
//                    mCh1.isChecked = false
//                    mCh2.isChecked = false
//                    mCh3.isChecked = true
//                    mCntCsrpic.visibility = View.GONE
//                }
//            }


            mTxtBacktohome.setOnClickListener(this)
            mTxtSubmit.setOnClickListener(this)
            mCntHelper.setOnClickListener(this)
            mCntHelper2.setOnClickListener(this)
            mCntNotfound.setOnClickListener(this)
            mImgComplogo.setOnClickListener(this)
            mImgCamera.setOnClickListener(this)
            mTxtLeave.setOnClickListener(this)

            ApplicationClass.mReviewEmp_Model = ReviewEmp_Model()
            ApplicationClass.mReviewEmp_Model.EmpName = "Alexa Jinaou"
            ApplicationClass.mReviewEmp_Model.EmpImage = R.drawable.rp!!

            ApplicationClass.ArrayList_mReviewEmp_Model.add(ApplicationClass.mReviewEmp_Model)

            ApplicationClass.mReviewEmp_Model = ReviewEmp_Model()
            ApplicationClass.mReviewEmp_Model.EmpName = "Alexa Gonzaleia"
            ApplicationClass.mReviewEmp_Model.EmpImage = R.drawable.review!!

            ApplicationClass.ArrayList_mReviewEmp_Model.add(ApplicationClass.mReviewEmp_Model)

            mCntHelper.visibility = View.GONE
            mCntHelper2.visibility = View.GONE

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


    override fun onStart() {
        super.onStart()
//        mCameraKitView.onStart()
    }

    override fun onResume() {
        super.onResume()
//        mCameraKitView.onResume()
    }

    override fun onPause() {
//        mCameraKitView.onPause()
        super.onPause()
    }

    override fun onStop() {
//        mCameraKitView.onStop()
        super.onStop()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        mCameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun foundDevice() {
        try {
            val R = Random()

            val dx: Float = R.nextFloat()
            val dy: Float = R.nextFloat()

            mCntHelper.setVisibility(View.VISIBLE)
            mCntHelper2.setVisibility(View.VISIBLE)
            val timer = Timer()
            mCntHelper.animate()
                .x(dx)
                .y(dy)
                .setDuration(0)
                .start()
            mCntHelper2.animate()
                .x(dx)
                .y(dy)
                .setDuration(0)
                .start()
            val animatorSet = AnimatorSet()
            animatorSet.duration = 400
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            val animatorList = ArrayList<Animator>()
            val scaleXAnimator = ObjectAnimator.ofFloat(mCntHelper, "ScaleX", 0f, 1.2f, 1f)
            animatorList.add(scaleXAnimator)
            val scaleYAnimator = ObjectAnimator.ofFloat(mCntHelper, "ScaleY", 0f, 1.2f, 1f)
            animatorList.add(scaleYAnimator)
            animatorSet.playTogether(animatorList)

            animatorSet.duration = 400
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            val scaleXAnimator2 = ObjectAnimator.ofFloat(mCntHelper2, "ScaleX", 0f, 1.2f, 1f)
            animatorList.add(scaleXAnimator2)
            val scaleYAnimator2 = ObjectAnimator.ofFloat(mCntHelper2, "ScaleY", 0f, 1.2f, 1f)
            animatorList.add(scaleYAnimator2)
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        try {
            // Add a marker in Sydney and move the camera
            val Trinidad = LatLng(10.3761803, -61.2449877)
            mMap.uiSettings.isCompassEnabled = false
            mMap.uiSettings.isMapToolbarEnabled = false
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Trinidad, 10.0f))
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this@ReviewEmployeeSearch, R.raw.style_json
                )
            )
            if (!success) {
                Log.e("TAG", "Style parsing failed.")
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

    override fun onClick(view: View?) {
        try {
            if (view == mCntNotfound) {
                mCntNotfound.visibility = View.INVISIBLE
                ReportView.visibility = View.VISIBLE
                ReportThankYouView.visibility = View.GONE
                mCntTopHeader.visibility = View.INVISIBLE
            } else if (view == mTxtSubmit) {
                if (mCh1.isChecked || mCh2.isChecked || mCh3.isChecked) {
                    ReportView.visibility = View.GONE
                    ReportThankYouView.visibility = View.VISIBLE
                } else {
                    Toast.makeText(
                        this,
                        "Please select atleast one option from above.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (view == mTxtBacktohome) {
                Bussiness_Location.thisBussiness_Activity.finish()
                BussinessCheckIn.thisBusinessCheckIn.finish()
                finish()

//                mCntNotfound.visibility = View.VISIBLE
//                ReportView.visibility = View.GONE
//                ReportThankYouView.visibility = View.GONE
//                mCntTopHeader.visibility = View.VISIBLE


                //Next changes
//                AppClass.Selected_ReviewEmp_Model = AppClass.ArrayList_mReviewEmp_Model.get(0)
//                var HelperIntent = Intent(this, ReviewEdit::class.java)
//                startActivity(HelperIntent)
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
            } else if (view == mCntHelper) {
                ApplicationClass.Selected_ReviewEmp_Model = ApplicationClass.ArrayList_mReviewEmp_Model.get(0)
                var HelperIntent = Intent(this, ReviewEdit::class.java)
                startActivity(HelperIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == mCntHelper2) {
                ApplicationClass.Selected_ReviewEmp_Model = ApplicationClass.ArrayList_mReviewEmp_Model.get(1)
                var HelperIntent = Intent(this, ReviewEdit::class.java)
                startActivity(HelperIntent)
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == mImgComplogo) {
//            mCameraKitView.captureImage(this)
//            mCameraKitView.captureImage { _, image ->
//                Log.e("Taking picture", "callback called")
//                var image = BitmapFactory.decodeByteArray(image, 0, image!!.size)
//                mImgCamera.setImageBitmap(image)
//            }
//            mCameraKitView.visibility = View.INVISIBLE
            } else if (view == mImgCamera) {
                Toast.makeText(this, "Bicon image capture here", Toast.LENGTH_SHORT).show()
//            var HelperIntent = Intent(this, AndroidCameraApi::class.java)
//            startActivityForResult(HelperIntent, 0)
//            overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
            } else if (view == mTxtLeave) {
                var HelperIntent = Intent(this, ReviewEdit::class.java)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            mImgCamera.setImageBitmap(ApplicationClass.mBiconReviewImageX)
        }
    }

    override fun onImage(p0: CameraKitView?, p1: ByteArray?) {


    }

}
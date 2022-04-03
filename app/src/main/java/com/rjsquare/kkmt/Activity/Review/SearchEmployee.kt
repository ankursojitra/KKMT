package com.rjsquare.kkmt.Activity.Review

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.rjsquare.kkmt.Activity.Bussiness.BussinessCheckIn
import com.rjsquare.kkmt.Activity.Bussiness.Bussiness_Location
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.Model.LatLngModel
import com.rjsquare.kkmt.Model.ReviewEmp_Model
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.databinding.ActivitySearchEmployeeBinding
import com.tristate.radarview.LatLongCs
import com.tristate.radarview.ObjectModel
import com.tristate.radarview.RadarViewC.IRadarCallBack
import java.util.*


class SearchEmployee : AppCompatActivity(), View.OnClickListener {

    private lateinit var mCh1: CheckBox
    private lateinit var mCh2: CheckBox
    private lateinit var mCh3: CheckBox
    private lateinit var mTxtSubmit: TextView
    private lateinit var mTxtView: TextView
    private lateinit var mTxtBacktohome: TextView
    private lateinit var mCntBussinessname: ConstraintLayout
    var posx = 50f
    var latlngList = ArrayList<LatLngModel>()

    //    private lateinit var mTxtLeave: TextView
    private lateinit var mCntConfirm: ConstraintLayout
    private lateinit var mCntNotfind: ConstraintLayout
    private lateinit var mImgClose: ImageView
    private lateinit var mImgCamera: ImageView
    var PosList = ArrayList<Int>()
    val size = 100

    var PositionLat = ArrayList<Double>()
    var SelPositionLat = ArrayList<Double>()
    var SelPositionLng = ArrayList<Double>()
    var SelPositionImg = ArrayList<Int>()
    var PositionLng = ArrayList<Double>()
    val mDataSet: ArrayList<ObjectModel> = ArrayList()
    lateinit var mCenterView: View

    lateinit var latLongCs: LatLongCs

    companion object {
        lateinit var thisSearchEmployee: Activity
        lateinit var DB_SearchEmployee: ActivitySearchEmployeeBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_SearchEmployee = DataBindingUtil.setContentView(this, R.layout.activity_search_employee)
        thisSearchEmployee = this

        ApplicationClass.StatusTextWhite(this, false)
        DB_SearchEmployee.rippleBack.startRippleAnimation()

        mCh1 = DB_SearchEmployee.layoutHelperReport.ch1
        mCh2 = DB_SearchEmployee.layoutHelperReport.ch2
        mCh3 = DB_SearchEmployee.layoutHelperReport.ch3

        mTxtBacktohome = DB_SearchEmployee.layoutHelperThankyou.txtBacktohome
        mTxtSubmit = DB_SearchEmployee.layoutHelperReport.txtSubmit
//        mTxtLeave = DB_SearchEmployee.layoutHelperReport.txtLeave
        mImgCamera = DB_SearchEmployee.layoutHelperReport.imgCamera
        mImgClose = DB_SearchEmployee.layoutHelperReport.imgClose

        //Radar View Setup


//        val tempIView1 = ImageView(this)
//        val tempView1 = ConstraintLayout(this)
//        tempIView1.setImageResource(R.drawable.ic_email)
//        tempView1.addView(tempIView1)
//        val rlp1 = ConstraintLayout.LayoutParams(size, size)
//        tempIView1.layoutParams = rlp1

//        posx += 50f
//
//        tempView.x = 100f
//        tempView.y = 150f
//        DB_SearchEmployee.rippleBack.addView(tempView)


        //------------------------------------

        val inflater =
            baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        mCenterView = inflater.inflate(R.layout.layout_center, null)
        mTxtView = mCenterView.findViewById<TextView>(R.id.mTVText)
        mTxtView.setOnClickListener(this)
        latLongCs = LatLongCs(23.070301, 72.517406)

//        val view1 = DB_SearchEmployee.cntHelper as View
//        val view2 = DB_SearchEmployee.cntHelper2 as View

        val t1 = TextView(this)
        t1.text = "A"

        val t2 = TextView(this)
        t2.text = "B"

        val t3 = TextView(this)
        t3.text = "C"

        PositionLat.add(23.070390)
        PositionLat.add(23.072059)
        PositionLat.add(23.066806)
        PositionLat.add(23.067906)
        PositionLat.add(23.064656)

        PositionLng.add(72.519376)
        PositionLng.add(72.515294)
        PositionLng.add(72.515854)
        PositionLng.add(72.517004)
        PositionLng.add(72.518504)

        var TotalSize = (PositionLat.size * PositionLng.size)
        for (i in 0..100 - 1) {
            val lat = PositionLat.get(Random().nextInt(PositionLat.size))
            val lng = PositionLng.get(Random().nextInt(PositionLng.size))

            var SameValueMatched = false
            for (tempValue in 0..SelPositionLat.size - 1) {
                if (SelPositionLat.get(tempValue) == lat && SelPositionLng.get(tempValue) == lng) {
                    SameValueMatched = true
                    break
                }
            }

            if (!SameValueMatched) {
                SelPositionLat.add(lat)
                SelPositionLng.add(lng)
            }
        }

        val tempIView = ImageView(this)
        val tempView = ConstraintLayout(this)
        tempIView.setImageResource(R.drawable.arrow)
//        tempView.removeAllViews()
        tempView.addView(tempIView)
        val rlp = ConstraintLayout.LayoutParams(size, size)
        tempIView.layoutParams = rlp


        val tempIView1 = ImageView(this)
        val tempView1 = ConstraintLayout(this)
        tempIView1.setImageResource(R.drawable.ic_email)
        tempView1.addView(tempIView1)
        val rlp1 = ConstraintLayout.LayoutParams(size, size)
        tempIView1.layoutParams = rlp1

        val tempIView2 = ImageView(this)
        val tempView2 = ConstraintLayout(this)
        tempIView2.setImageResource(R.drawable.ic_challenge)
//        tempView2.removeAllViews()
        tempView2.addView(tempIView2)
        val rlp2 = ConstraintLayout.LayoutParams(size, size)
        tempIView2.layoutParams = rlp2


        val tempIView3 = ImageView(this)
        val tempView3 = ConstraintLayout(this)
        tempIView3.setImageResource(R.drawable.ic_amount)
        tempView3.addView(tempIView3)
        val rlp3 = ConstraintLayout.LayoutParams(size, size)
        tempIView3.layoutParams = rlp3

        val tempImageView = ImageView(this)
        val tempTextView = TextView(this)
        val mTempView = ConstraintLayout(this)
//        tempImageView.setImageResource(R.drawable.ic_ads)
//        tempTextView.setText("Abcd")
//        var TextParam = ConstraintLayout.LayoutParams(0, 0)
//        TextParam.setMargins(0, 100, 0, 0)
//        tempTextView.layoutParams = TextParam
//        mTempView.addView(tempImageView)
//        mTempView.addView(tempTextView)
////        mTempView.orientation = LinearLayout.VERTICAL
//        val rlp4 = ConstraintLayout.LayoutParams(size, size)
//        mTempView.layoutParams = rlp4

//        DB_SearchEmployee.cntHelper.parent.
//        mTempView.removeAllViews()
//        mTempView.addView(DB_SearchEmployee.cntHelper)

        var view1 = inflater.inflate(R.layout.child_view, null)
        var view2 = inflater.inflate(R.layout.child_view, null)
        var view3 = inflater.inflate(R.layout.child_view, null)
        var view4 = inflater.inflate(R.layout.child_view, null)
        var view5 = inflater.inflate(R.layout.child_view, null)
        var view6 = inflater.inflate(R.layout.child_view, null)
        var view7 = inflater.inflate(R.layout.child_view, null)
        var view8 = inflater.inflate(R.layout.child_view, null)
        var view9 = inflater.inflate(R.layout.child_view, null)
        var view10 = inflater.inflate(R.layout.child_view, null)
        var view11 = inflater.inflate(R.layout.child_view, null)
        var view12 = inflater.inflate(R.layout.child_view, null)
        var view13 = inflater.inflate(R.layout.child_view, null)
        var view14 = inflater.inflate(R.layout.child_view, null)
        var view15 = inflater.inflate(R.layout.child_view, null)
        var view16 = inflater.inflate(R.layout.child_view, null)
        var view17 = inflater.inflate(R.layout.child_view, null)
        var view18 = inflater.inflate(R.layout.child_view, null)
        var view19 = inflater.inflate(R.layout.child_view, null)
        var view20 = inflater.inflate(R.layout.child_view, null)
        var view21 = inflater.inflate(R.layout.child_view, null)
        var view22 = inflater.inflate(R.layout.child_view, null)
        var view23 = inflater.inflate(R.layout.child_view, null)
        var view24 = inflater.inflate(R.layout.child_view, null)
        var view25 = inflater.inflate(R.layout.child_view, null)
        var view26 = inflater.inflate(R.layout.child_view, null)
        var view27 = inflater.inflate(R.layout.child_view, null)
        var view28 = inflater.inflate(R.layout.child_view, null)
//                                          ubhu                aadu
        //Add custom data with a view, you can also add this view by looping


        var latList = ArrayList<Double>()

        var lngList = ArrayList<Double>()

        latList.add(23.068416)
        latList.add(23.068926)
        latList.add(23.069706)
        latList.add(23.070256)
        latList.add(23.070756)
        latList.add(23.071459)
        latList.add(23.072179)
        latList.add(23.071459)
        latList.add(23.070756)
        latList.add(23.070256)
        latList.add(23.069706)
        latList.add(23.068926)
        latList.add(23.069066)
        latList.add(23.069706)
        latList.add(23.070256)
        latList.add(23.070756)
        latList.add(23.071509)
        latList.add(23.070756)
        latList.add(23.070256)
        latList.add(23.069706)
        latList.add(23.070166)
        latList.add(23.070166)
        latList.add(23.070896)
        latList.add(23.072879)
        latList.add(23.072385)
        latList.add(23.071615)
        latList.add(23.072385)
        latList.add(23.071615)

        lngList.add(72.517391)
        lngList.add(72.515901)
        lngList.add(72.515421)
        lngList.add(72.515394)
        lngList.add(72.515404)
        lngList.add(72.515814)
        lngList.add(72.517394)
        lngList.add(72.518891)
        lngList.add(72.519351)
        lngList.add(72.519440)
        lngList.add(72.519351)
        lngList.add(72.518891)
        lngList.add(72.517391)
        lngList.add(72.516204)
        lngList.add(72.516184)
        lngList.add(72.516184)
        lngList.add(72.517394)
        lngList.add(72.518524)
        lngList.add(72.518640)
        lngList.add(72.518524)
        lngList.add(72.517991)
        lngList.add(72.516789)
        lngList.add(72.517391)
        lngList.add(72.517394)
        lngList.add(72.515794)
        lngList.add(72.514994)
        lngList.add(72.519014)
        lngList.add(72.519854)

        for (pos in 0..latList.size - 1) {
            var latlngModel = LatLngModel()
            latlngModel.poslat = latList.get(pos)
            latlngModel.poslng = lngList.get(pos)
            latlngList.add(latlngModel)
        }

        var TotalViews = 5

        GenerateRandomPos(TotalViews)

        for (pos in 0..TotalViews - 1) {
            var view = inflater.inflate(R.layout.child_view, null)
            var tView = view.findViewById<TextView>(R.id.txt_name)
            tView.text = "Name : " + pos
            view.tag = pos

            ApplicationClass.mReviewEmp_Model = ReviewEmp_Model()
            ApplicationClass.mReviewEmp_Model.EmpName = "Name : " + pos
            ApplicationClass.mReviewEmp_Model.EmpImage = R.drawable.rp!!

            ApplicationClass.ArrayList_mReviewEmp_Model.add(ApplicationClass.mReviewEmp_Model)

            view.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    Log.e("TAG", "RANDOMCLCIK : " + pos)
                }
            })
            mDataSet.add(
                ObjectModel(
                    latlngList.get(PosList.get(pos)).poslat,
                    latlngList.get(PosList.get(pos)).poslng,
                    100.0,
                    view
                )
            )
        }
        for (pos in 0..TotalViews - 1) {
            mDataSet.get(pos).getmView().visibility = View.INVISIBLE
        }

        var latecounter = 150L
        val handler = Handler()
        val runnable = Runnable {
            for (pos in 0..TotalViews - 1) {
                val handler = Handler()
                val runnable = Runnable {
                    AnimateDevice(mDataSet.get(pos))
                }
                handler.postDelayed(runnable, latecounter)
                latecounter += 100
            }
        }
        handler.postDelayed(runnable, 2000)


//        mDataSet.add(ObjectModel(latlngList.get(0).poslat, latlngList.get(0).poslng, 100.0, view1))
//        mDataSet.add(ObjectModel(latlngList.get(1).poslat, latlngList.get(1).poslng, 100.0, view2))
//        mDataSet.add(ObjectModel(latlngList.get(2).poslat, latlngList.get(2).poslng, 100.0, view3))
//        mDataSet.add(ObjectModel(latlngList.get(3).poslat, latlngList.get(3).poslng, 100.0, view4))
//        mDataSet.add(ObjectModel(latlngList.get(4).poslat, latlngList.get(4).poslng, 100.0, view5))
//        mDataSet.add(ObjectModel(latlngList.get(5).poslat, latlngList.get(5).poslng, 100.0, view6))
//        mDataSet.add(ObjectModel(latlngList.get(6).poslat, latlngList.get(6).poslng, 100.0, view7))
//        mDataSet.add(ObjectModel(latlngList.get(7).poslat, latlngList.get(7).poslng, 100.0, view8))
//        mDataSet.add(ObjectModel(latlngList.get(8).poslat, latlngList.get(8).poslng, 100.0, view9))
//        mDataSet.add(ObjectModel(latlngList.get(9).poslat, latlngList.get(9).poslng, 100.0, view10))
//        mDataSet.add(ObjectModel(latlngList.get(10).poslat, latlngList.get(10).poslng, 100.0, view11))
//        mDataSet.add(ObjectModel(latlngList.get(11).poslat, latlngList.get(11).poslng, 100.0, view12))
//        mDataSet.add(ObjectModel(latlngList.get(12).poslat, latlngList.get(12).poslng, 100.0, view13))
//        mDataSet.add(ObjectModel(latlngList.get(13).poslat, latlngList.get(13).poslng, 100.0, view14))
//        mDataSet.add(ObjectModel(latlngList.get(14).poslat, latlngList.get(14).poslng, 100.0, view15))
//        mDataSet.add(ObjectModel(latlngList.get(15).poslat, latlngList.get(15).poslng, 100.0, view16))
//        mDataSet.add(ObjectModel(latlngList.get(16).poslat, latlngList.get(16).poslng, 100.0, view17))
//        mDataSet.add(ObjectModel(latlngList.get(17).poslat, latlngList.get(17).poslng, 100.0, view18))
//        mDataSet.add(ObjectModel(latlngList.get(18).poslat, latlngList.get(18).poslng, 100.0, view19))
//        mDataSet.add(ObjectModel(latlngList.get(19).poslat, latlngList.get(19).poslng, 100.0, view20))
//        mDataSet.add(ObjectModel(latlngList.get(20).poslat, latlngList.get(20).poslng, 100.0, view21))
//        mDataSet.add(ObjectModel(latlngList.get(21).poslat, latlngList.get(21).poslng, 100.0, view22))
//        mDataSet.add(ObjectModel(latlngList.get(22).poslat, latlngList.get(22).poslng, 100.0, view23))
//        mDataSet.add(ObjectModel(latlngList.get(23).poslat, latlngList.get(23).poslng, 100.0, view24))
//        mDataSet.add(ObjectModel(latlngList.get(24).poslat, latlngList.get(24).poslng, 100.0, view25))
//        mDataSet.add(ObjectModel(latlngList.get(25).poslat, latlngList.get(25).poslng, 100.0, view26))
//        mDataSet.add(ObjectModel(latlngList.get(26).poslat, latlngList.get(26).poslng, 100.0, view27))
//        mDataSet.add(ObjectModel(latlngList.get(27).poslat, latlngList.get(27).poslng, 100.0, view28))


        //Final Positions

//        mDataSet.add(ObjectModel(23.068416, 72.517391, 100.0, view4))
//        mDataSet.add(ObjectModel(23.070256, 72.515404, 100.0, view4))
//        mDataSet.add(ObjectModel(23.072179, 72.517404, 100.0, view4))
//        mDataSet.add(ObjectModel(23.070256, 72.519440, 100.0, view4))
//        mDataSet.add(ObjectModel(23.069006, 72.518831, 100.0, view4))


//        for (i in 0..SelPositionLng.size-1){
//            val tempIView1 = ImageView(this)
//            val tempView1 = ConstraintLayout(this)
//            tempIView1.setImageResource(R.drawable.ic_email)
//            tempView1.addView(tempIView1)
//            val rlp1 = ConstraintLayout.LayoutParams(size, size)
//            tempIView1.layoutParams = rlp1
//
//            mDataSet.add(ObjectModel(SelPositionLat.get(i), SelPositionLng.get(i), 50.0, tempView1))
//        }

        for (iPos in 0..SelPositionLat.size - 1) {
            Log.e(
                "TAG",
                "PositionValues Lat: " + SelPositionLat.get(iPos) + ", Lng : " + SelPositionLng.get(
                    iPos
                )
            )
        }
        DB_SearchEmployee.radarView.setupData(
            250.0,
            mDataSet,
            latLongCs,
            mCenterView
        ) //Here 250 is the radar radious you can set as per your choice or set

        //You can get callback of your view click
        DB_SearchEmployee.radarView.setUpCallBack(IRadarCallBack { objectModel, view ->
            Log.e("TAG", "Clicks of Radarview. : " + view.transitionName)
            Log.e("TAG", "Clicks of RadarviewZX. : " + view.tag)
            Log.e("TAG", "Clicks of RadarviewXX. : " + objectModel.toString())
            ApplicationClass.Selected_ReviewEmp_Model = ApplicationClass.ArrayList_mReviewEmp_Model.get(view.tag.toString().toInt())
            var HelperIntent = Intent(this, ReviewEdit::class.java)
            startActivity(HelperIntent)
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out)

        })

        DB_SearchEmployee.cntNotfound.setOnClickListener(this)
        mImgCamera.setOnClickListener(this)
        mTxtBacktohome.setOnClickListener(this)
        mTxtSubmit.setOnClickListener(this)
//        mTxtLeave.setOnClickListener(this)
        mImgClose.setOnClickListener(this)
    }

    private fun AnimateDevice(mView: ObjectModel) {
        try {
            val R = Random()

            val dx: Float = mView.getmView().x
            val dy: Float = mView.getmView().y
//
//            val dx: Float = R.nextFloat()
//            val dy: Float = R.nextFloat()

            mView.getmView().setVisibility(View.VISIBLE)
//            mCntHelper2.setVisibility(View.VISIBLE)
            val timer = Timer()
            mView.getmView().animate()
                .x(dx)
                .y(dy)
                .setDuration(500)
                .start()
//            mCntHelper2.animate()
//                .x(dx)
//                .y(dy)
//                .setDuration(0)
//                .start()
            val animatorSet = AnimatorSet()
            animatorSet.duration = 500
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            val animatorList = ArrayList<Animator>()
            val scaleXAnimator = ObjectAnimator.ofFloat(mView.getmView(), "ScaleX", 0f, 1.2f, 1f)
            animatorList.add(scaleXAnimator)
            val scaleYAnimator = ObjectAnimator.ofFloat(mView.getmView(), "ScaleY", 0f, 1.2f, 1f)
            animatorList.add(scaleYAnimator)
            animatorSet.playTogether(animatorList)

//            animatorSet.duration = 400
//            animatorSet.interpolator = AccelerateDecelerateInterpolator()
//            val scaleXAnimator2 = ObjectAnimator.ofFloat(mCntHelper2, "ScaleX", 0f, 1.2f, 1f)
//            animatorList.add(scaleXAnimator2)
//            val scaleYAnimator2 = ObjectAnimator.ofFloat(mCntHelper2, "ScaleY", 0f, 1.2f, 1f)
//            animatorList.add(scaleYAnimator2)
//            animatorSet.playTogether(animatorList)

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

    private fun GenerateRandomPos(TotalViews: Int) {
        var counter = 0
        while (counter <= TotalViews) {
            var RNo = GetNumber()
            Collections.shuffle(PosList)
            if (!PosList.contains(RNo)) {
                PosList.add(RNo)
                counter++
            }
        }
    }

    private fun GetNumber(): Int {
        return Random().nextInt(20)
    }

    override fun onClick(view: View?) {
//        try {
        if (view == DB_SearchEmployee.cntNotfound) {
            CloseViews()
            DB_SearchEmployee.layoutHelperReport.cntReportview.visibility = View.VISIBLE
        } else if (view == mTxtSubmit) {
            if (mCh1.isChecked || mCh2.isChecked || mCh3.isChecked) {
                CloseViews()
                DB_SearchEmployee.layoutHelperThankyou.cntReportThankYouView.visibility =
                    View.VISIBLE
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
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
        } else if (view == mImgCamera) {
            Toast.makeText(this, "Employee image capture Comingsoon...", Toast.LENGTH_SHORT).show()
        } else if (view == mImgClose) {
            CloseViews()
            DB_SearchEmployee.cntEmpmainView.visibility = View.VISIBLE
        } else if (view == mTxtView) {
            Log.e("TAG", "valuePos : ")
//            val tempIViewx = ImageView(this)
//            val tempViewx = ConstraintLayout(this)
//            tempIViewx.setImageResource(R.drawable.arrow)
////        tempView2.removeAllViews()
//            tempViewx.addView(tempIViewx)
//            val rlpx = ConstraintLayout.LayoutParams(size, size)
//            tempIViewx.layoutParams = rlpx
//
//            mDataSet.add(
//                ObjectModel(
//                    SelPositionLat.get(mDataSet.size - 1),
//                    SelPositionLng.get(mDataSet.size - 1),
//                    50.0,
//                    tempViewx
//                )
//            )

//            DB_SearchEmployee.radarView.setupData(
//                250.0,
//                mDataSet,
//                latLongCs,
//                mCenterView
//            ) //Here 250 is the radar radious you can set as per your choice or set

        }
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
    }

    private fun CloseViews() {
        DB_SearchEmployee.cntEmpmainView.visibility = View.GONE
        DB_SearchEmployee.layoutHelperReport.cntReportview.visibility = View.GONE
        DB_SearchEmployee.layoutHelperThankyou.cntReportThankYouView.visibility = View.GONE
    }

}
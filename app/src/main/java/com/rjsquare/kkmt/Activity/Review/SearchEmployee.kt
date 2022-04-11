package com.rjsquare.kkmt.Activity.Review

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.minew.beaconplus.sdk.MTCentralManager
import com.minew.beaconplus.sdk.enums.BluetoothState
import com.minew.beaconplus.sdk.interfaces.OnBluetoothStateChangedListener
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Bussiness.BussinessCheckIn
import com.rjsquare.kkmt.Activity.Bussiness.Bussiness_Location
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.Model.LatLngModel
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.SlaveBeaconService
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.SlaveBeaconModel
import com.rjsquare.kkmt.databinding.ActivitySearchEmployeeBinding
import com.tristate.radarview.LatLongCs
import com.tristate.radarview.ObjectModel
import com.tristate.radarview.RadarViewC.IRadarCallBack
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    private val BLE_DEVICE_INFO_CALL = 10000L
    private val REQUEST_ENABLE_BT = 3

    private var mMtCentralManager: MTCentralManager? = null
    lateinit var BeaconMACList: ArrayList<String>
    var HandlerCallavailable = false

    //    private lateinit var mTxtLeave: TextView
    private lateinit var mCntConfirm: ConstraintLayout
    private lateinit var mCntNotfind: ConstraintLayout
    private lateinit var mImgClose: ImageView
    private lateinit var mImgCamera: ImageView
    private val PERMISSION_COARSE_LOCATION = 2
    lateinit var handler: Handler
    lateinit var runnable: Runnable
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

    lateinit var latList: ArrayList<Double>
    lateinit var lngList: ArrayList<Double>


    companion object {
        lateinit var thisSearchEmployee: Activity
        lateinit var DB_SearchEmployee: ActivitySearchEmployeeBinding
        lateinit var inflater: LayoutInflater
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

        BeaconMACList = ArrayList()
        handler = Handler()
        HandlerCallavailable = true

        if (!ensureBleExists()) finish()

        if (!isBLEEnabled()) {
            showBLEDialog()
        }
        initManager()
        getRequiredPermissions()
        initListener()

        //------------------------------------


//        var TotalViews = ApplicationClass.slaveModellist.size



        FillPos()

        DB_SearchEmployee.cntNotfound.setOnClickListener(this)
        mImgCamera.setOnClickListener(this)
        mTxtBacktohome.setOnClickListener(this)
        mTxtSubmit.setOnClickListener(this)
//        mTxtLeave.setOnClickListener(this)
        mImgClose.setOnClickListener(this)
        DB_SearchEmployee.txtUnauthOk.setOnClickListener(this)
        runnable = Runnable {
//                foundDevice()
            mMtCentralManager!!.stopScan()
            SlaveBleDeviceInfo()
        }

    }

    private fun FillPos() {

        latList = ArrayList()
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

        lngList = ArrayList()
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
        Log.e("ATG", "CHECKLATLNG SIZE  : " + latlngList.size)
        inflater =
            baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        mCenterView = inflater.inflate(R.layout.layout_center, null)
        mTxtView = mCenterView.findViewById<TextView>(R.id.mTVText)
        mTxtView.setOnClickListener(this)
        latLongCs = LatLongCs(23.070301, 72.517406)

        var TotalViews = 12

        GenerateRandomPos(TotalViews)

        for (pos in 0..TotalViews - 1) {
            Log.e("ATG", "CheckViewCreate :")
            var view = inflater.inflate(R.layout.child_view, null)
            var tView = view.findViewById<TextView>(R.id.txt_name)
//            tView.text = ApplicationClass.slaveModellist[pos].username
            tView.text = "Name : " + pos
            view.tag = pos

//            ApplicationClass.mReviewEmp_Model = ReviewEmp_Model()
//            ApplicationClass.mReviewEmp_Model.EmpName = "Name : " + pos
//            ApplicationClass.mReviewEmp_Model.EmpImage = R.drawable.rp!!
//
//            ApplicationClass.ArrayList_mReviewEmp_Model.add(ApplicationClass.mReviewEmp_Model)

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
        val runnablex = Runnable {
            for (pos in 0..TotalViews - 1) {
                Log.e("ATGX", "CheckViewCreatea :")
                val handler = Handler()
                val runnablex = Runnable {
//                    AnimateDevice(mDataSet.get(pos))
                }
                handler.postDelayed(runnablex, latecounter)
                latecounter += 100
            }
        }
        handler.postDelayed(runnablex, 200)

//        latLongCs = LatLongCs(23.070301, 72.517406)
        DB_SearchEmployee.radarView.setupData(
            250.0,
            mDataSet,
            latLongCs,
            mCenterView
        )
        //Here 250 is the radar radious you can set as per your choice or set

        //You can get callback of your view click
        DB_SearchEmployee.radarView.setUpCallBack(IRadarCallBack { objectModel, view ->
            Log.e("TAG", "Clicks of Radarview. : " + view.transitionName)
            Log.e("TAG", "Clicks of RadarviewZX. : " + view.tag)
            Log.e("TAG", "Clicks of RadarviewXX. : " + objectModel.toString())
            ApplicationClass.Selected_ReviewEmp_Model =
                ApplicationClass.ArrayList_mReviewEmp_Model.get(view.tag.toString().toInt())
            var HelperIntent = Intent(this, ReviewEdit::class.java)
            startActivity(HelperIntent)
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        })
    }

    private fun SlaveBleDeviceInfo() {

        var beaconOBJ = JSONObject()
        var arrayJ = JSONArray()
        for (Mac in BeaconMACList) {
            arrayJ.put(Mac)
            Log.e("TAG", "CheckSlaveMac : " + Mac)
        }
        beaconOBJ.put("becon_list", arrayJ)
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            params[Constants.paramKey_BussinessId] =
                ApplicationClass.selectedMasterModel.businessid_db.toString()

            val service =
                ApiCallingInstance.retrofitInstance.create<SlaveBeaconService>(
                    SlaveBeaconService::class.java
                )
            val call =
                service.GetSlaveBeaconData(
                    params, BeaconMACList, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<SlaveBeaconModel> {
                override fun onFailure(call: Call<SlaveBeaconModel>, t: Throwable) {
                    DB_SearchEmployee.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<SlaveBeaconModel>,
                    response: Response<SlaveBeaconModel>
                ) {
                    DB_SearchEmployee.cntLoader.visibility = View.GONE
                    Log.e("TAG", "CHECKRESPONSESlave : " + Gson().toJson(response.body()))

                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        ApplicationClass.slaveModellist = response.body()!!.data!!
                        ShowEmployees()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        DB_SearchEmployee.cntUnAuthorized.visibility = View.VISIBLE
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

    private fun ShowEmployees() {
        inflater =
            baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        mCenterView = inflater.inflate(R.layout.layout_center, null)
        mTxtView = mCenterView.findViewById<TextView>(R.id.mTVText)
        mTxtView.setOnClickListener(this)
        latLongCs = LatLongCs(23.070301, 72.517406)

        var TotalViews = ApplicationClass.slaveModellist.size

        GenerateRandomPos(TotalViews)

        for (pos in 0..TotalViews - 1) {
            Log.e("ATG", "CheckViewCreate :")
            var view = inflater.inflate(R.layout.child_view, null)
            var tView = view.findViewById<TextView>(R.id.txt_name)
            tView.text = ApplicationClass.slaveModellist[pos].username
//            tView.text = "Name : " + pos
            view.tag = pos

//            ApplicationClass.mReviewEmp_Model = ReviewEmp_Model()
//            ApplicationClass.mReviewEmp_Model.EmpName = "Name : " + pos
//            ApplicationClass.mReviewEmp_Model.EmpImage = R.drawable.rp!!
//
//            ApplicationClass.ArrayList_mReviewEmp_Model.add(ApplicationClass.mReviewEmp_Model)

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
        val runnablex = Runnable {
            for (pos in 0..TotalViews - 1) {
                Log.e("ATGX", "CheckViewCreatea :")
                val handler = Handler()
                val runnablex = Runnable {
                    AnimateDevice(mDataSet.get(pos))
                }
                handler.postDelayed(runnablex, latecounter)
                latecounter += 100
            }
        }
        handler.postDelayed(runnablex, 200)

//        latLongCs = LatLongCs(23.070301, 72.517406)
        DB_SearchEmployee.radarView.setupData(
            250.0,
            mDataSet,
            latLongCs,
            mCenterView
        )
        //Here 250 is the radar radious you can set as per your choice or set

        //You can get callback of your view click
        DB_SearchEmployee.radarView.setUpCallBack(IRadarCallBack { objectModel, view ->
            Log.e("TAG", "Clicks of Radarview. : " + view.transitionName)
            Log.e("TAG", "Clicks of RadarviewZX. : " + view.tag)
            Log.e("TAG", "Clicks of RadarviewXX. : " + objectModel.toString())
            ApplicationClass.Selected_ReviewEmp_Model =
                ApplicationClass.ArrayList_mReviewEmp_Model.get(view.tag.toString().toInt())
            var HelperIntent = Intent(this, ReviewEdit::class.java)
            startActivity(HelperIntent)
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        })
    }

    private fun ensureBleExists(): Boolean {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Phone does not support BLE", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun initListener() {
        mMtCentralManager!!.setMTCentralManagerListener { peripherals ->
            for (peripheral in peripherals) {
                val Mac = peripheral.mMTFrameHandler.mac.replace(":", "")
                if (peripheral.mMTFrameHandler.name.contains(Constants.Companion.slaveBeacon)) {
                    if (!BeaconMACList.contains(Mac)) {
                        BeaconMACList.add(Mac)
                    }
                }
            }
            if (HandlerCallavailable) {
                HandlerCallavailable = false
                handler.postDelayed(runnable, BLE_DEVICE_INFO_CALL)
            }
        }
    }

    private fun getRequiredPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSION_COARSE_LOCATION
            )
        } else {
            initData()
        }
    }

    private fun initData() {
        mMtCentralManager!!.startScan()
    }

    private fun initManager() {
        mMtCentralManager = MTCentralManager.getInstance(this)
        //startservice
        mMtCentralManager!!.startService()
        val bluetoothState: BluetoothState = mMtCentralManager!!.getBluetoothState(this)
        when (bluetoothState) {
            BluetoothState.BluetoothStateNotSupported -> Log.e("tag", "BluetoothStateNotSupported")
            BluetoothState.BluetoothStatePowerOff -> Log.e("tag", "BluetoothStatePowerOff")
            BluetoothState.BluetoothStatePowerOn -> Log.e("tag", "BluetoothStatePowerOn")
        }
        mMtCentralManager!!.setBluetoothChangedListener(OnBluetoothStateChangedListener { state ->
            when (state) {
                BluetoothState.BluetoothStateNotSupported -> Log.e(
                    "tag",
                    "BluetoothStateNotSupported"
                )
                BluetoothState.BluetoothStatePowerOff -> Log.e("tag", "BluetoothStatePowerOff")
                BluetoothState.BluetoothStatePowerOn -> Log.e("tag", "BluetoothStatePowerOn")
            }
        })
    }

    private fun showBLEDialog() {
        val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Check Bluetooth is Enable or not

            return
        }
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT)
    }

    protected fun isBLEEnabled(): Boolean {
        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter
        return adapter != null && adapter.isEnabled
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
        PosList = ArrayList()
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

        } else if (view == DB_SearchEmployee.txtUnauthOk) {
            ApplicationClass.UserLogout(this)
        }
    }

    private fun CloseViews() {
        DB_SearchEmployee.cntEmpmainView.visibility = View.GONE
        DB_SearchEmployee.layoutHelperReport.cntReportview.visibility = View.GONE
        DB_SearchEmployee.layoutHelperThankyou.cntReportThankYouView.visibility = View.GONE
    }

}
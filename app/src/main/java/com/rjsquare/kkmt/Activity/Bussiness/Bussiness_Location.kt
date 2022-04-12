package com.rjsquare.kkmt.Activity.Bussiness

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.minew.beaconplus.sdk.MTCentralManager
import com.minew.beaconplus.sdk.enums.BluetoothState
import com.minew.beaconplus.sdk.interfaces.OnBluetoothStateChangedListener
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Review.SearchEmployee
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.BusinessNotFoundModel
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.MasterBeaconModel
import com.rjsquare.kkmt.databinding.ActivityLocationBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_bussiness_report.view.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Bussiness_Location : AppCompatActivity(), View.OnClickListener, OnMapReadyCallback {

    private lateinit var mCh1: CheckBox
    private lateinit var mCh2: CheckBox
    private lateinit var mCh3: CheckBox
    private lateinit var edtBussinessName: EditText
    private lateinit var mTxtSubmit: TextView
    private lateinit var mCntBackToHome: ConstraintLayout
    private lateinit var mMap: GoogleMap
    private lateinit var mCntBussinessname: ConstraintLayout
    private lateinit var mTxtLeave: TextView
    private lateinit var mTxtBusinessName: TextView

    private lateinit var mImgLogo: ImageView
    private lateinit var mCntConfirm: ConstraintLayout
    private lateinit var mCntNotfind: ConstraintLayout
    private lateinit var mImgClose: ImageView
    private lateinit var mImgBusRepClose: ImageView

    lateinit var DB_BusinessLocation: ActivityLocationBinding

    private var notFoundBusinessName = ""
    private var notFoundBusinessReason = ""
    private val CHECK_IN_CODE = 100
    private val BLE_DEVICE_INFO_CALL = 10000L
    private val REQUEST_ENABLE_BT = 3
    private val PERMISSION_COARSE_LOCATION = 2
    private var mMtCentralManager: MTCentralManager? = null
    lateinit var BeaconMACList: ArrayList<String>
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    var HandlerCallavailable = false
    lateinit var masterList: ArrayList<MasterBeaconModel.BusinessBescon>

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_BusinessLocation = DataBindingUtil.setContentView(this, R.layout.activity_location)
        try {
            ApplicationClass.StatusTextWhite(this, false)
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(this)

            DB_BusinessLocation.rippleback.startRippleAnimation()
            thisBussiness_Activity = this
            BeaconMACList = ArrayList()
            handler = Handler()
            HandlerCallavailable = true
            masterList = ArrayList()

            mCntBussinessname = DB_BusinessLocation.layoutBussinessReport.cntBussinessname
            mImgLogo = DB_BusinessLocation.layoutBussinessConfirm.imgBusprofile
            mTxtBusinessName = DB_BusinessLocation.layoutBussinessConfirm.txtBusname
            mCntConfirm = DB_BusinessLocation.layoutBussinessConfirm.cntConfirm
            mCntNotfind = DB_BusinessLocation.layoutBussinessConfirm.cntNotfind
            mImgClose = DB_BusinessLocation.layoutBussinessConfirm.imgClose
            mImgBusRepClose = DB_BusinessLocation.layoutBussinessReport.imgClose

            mCh1 = DB_BusinessLocation.layoutBussinessReport.ch1
            mCh2 = DB_BusinessLocation.layoutBussinessReport.ch2
            mCh3 = DB_BusinessLocation.layoutBussinessReport.ch3
            mTxtSubmit = DB_BusinessLocation.layoutBussinessReport.txtSubmit
            edtBussinessName =
                DB_BusinessLocation.layoutBussinessReport.cntBussinessname.edt_bussinessname
            mCntBackToHome = DB_BusinessLocation.layoutBussinessThankyou.cntBacktohome
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


            mCntBackToHome.setOnClickListener(this)
            mTxtSubmit.setOnClickListener(this)
            DB_BusinessLocation.cntCompany.setOnClickListener(this)
            DB_BusinessLocation.cntNotfound.setOnClickListener(this)
            mTxtLeave.setOnClickListener(this)
            mCntConfirm.setOnClickListener(this)
            mCntNotfind.setOnClickListener(this)
            mImgClose.setOnClickListener(this)
            mImgBusRepClose.setOnClickListener(this)
            DB_BusinessLocation.txtUnauthOk.setOnClickListener(this)
            DB_BusinessLocation.txtAlertok.setOnClickListener(this)
            DB_BusinessLocation.layoutBussinessConfirm.cntNotfind.setOnClickListener(this)
            DB_BusinessLocation.cntCompany.visibility = View.GONE

            runnable = Runnable {
                mMtCentralManager!!.stopScan()
                MasterBleDeviceInfo()
            }
            BleScan()
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

    private fun BleScan() {
        HandlerCallavailable = true
        if (!ensureBleExists()) finish()

        if (!isBLEEnabled()) {
            showBLEDialog()
        }
        initManager()
        getRequiredPermissions()
        initListener()
    }

    private fun MasterBleDeviceInfo() {
        DB_BusinessLocation.cntLoader.visibility = View.VISIBLE
        var beaconOBJ = JSONObject()
        var arrayJ = JSONArray()
        for (Mac in BeaconMACList) {
            arrayJ.put(Mac)
            Log.e("TAG", "CheckMac : " + Mac)
        }
        beaconOBJ.put("becon_list", arrayJ)
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.MasterBeaconService>(
                    NetworkServices.MasterBeaconService::class.java
                )
            val call =
                service.GetBusinessBeaconData(
                    params, BeaconMACList, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<MasterBeaconModel> {
                override fun onFailure(call: Call<MasterBeaconModel>, t: Throwable) {
                    DB_BusinessLocation.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<MasterBeaconModel>,
                    response: Response<MasterBeaconModel>
                ) {
                    DB_BusinessLocation.cntLoader.visibility = View.GONE
                    Log.e("TAG", "CHECKRESPONSE : " + Gson().toJson(response.body()))

                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        masterList = response.body()!!.data!!
                        SetBusinessViews()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        ShowUnauthorization()
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {
                        ShowAlert(response.body()!!.message)
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

    private fun ShowUnauthorization() {
        CloseViews()
        DB_BusinessLocation.cntUnAuthorized.visibility = View.VISIBLE
    }

    private fun BusinessNotFound() {
        DB_BusinessLocation.cntLoader.visibility = View.VISIBLE
        var beaconOBJ = JSONObject()
        var arrayJ = JSONArray()
        for (Mac in BeaconMACList) {
            arrayJ.put(Mac)
            Log.e("TAG", "CheckMac : " + Mac)
        }
        beaconOBJ.put("becon_list", arrayJ)
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()

            params[Constants.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            params[Constants.paramKey_Reason] = notFoundBusinessReason
            params[Constants.paramKey_BusinessName] = notFoundBusinessName

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.BusinessNotFoundService>(
                    NetworkServices.BusinessNotFoundService::class.java
                )
            val call =
                service.GetBusinessNotFoundData(
                    params, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<BusinessNotFoundModel> {
                override fun onFailure(call: Call<BusinessNotFoundModel>, t: Throwable) {
                    DB_BusinessLocation.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<BusinessNotFoundModel>,
                    response: Response<BusinessNotFoundModel>
                ) {
                    DB_BusinessLocation.cntLoader.visibility = View.GONE
                    Log.e("TAG", "CHECKRESPONSE : " + Gson().toJson(response.body()))

                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        ShowThankyouPopup()
                    } else if (response.body()!!.status.equals(Constants.ResponseUnauthorized)) {
                        ShowUnauthorization()
                    } else if (response.body()!!.status.equals(Constants.ResponseEmpltyList)) {

                    } else {
                        ShowAlert(response.body()!!.message)
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

    private fun ShowThankyouPopup() {
        CloseViews()
        DB_BusinessLocation.layoutBussinessThankyou.BusinessThankyou.visibility = View.VISIBLE
    }

    private fun ShowAlert(message: String?) {
        DB_BusinessLocation.txtAlertmsg.text = message
        DB_BusinessLocation.cntAlert.visibility = View.VISIBLE
    }

    private fun getMarkerBitmapFromView(masterBeaconInfo: MasterBeaconModel.BusinessBescon): Bitmap {
        //HERE YOU CAN ADD YOUR CUSTOM VIEW
        val customMarkerView: View =
            (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.map_marker,
                null
            )
        val textView = customMarkerView.findViewById<TextView>(R.id.txt_busname) as TextView
        val imageViewPin = customMarkerView.findViewById<ImageView>(R.id.img_pin) as ImageView
        val imageViewBusiness =
            customMarkerView.findViewById<ImageView>(R.id.img_business) as ImageView
        val CntView =
            customMarkerView.findViewById<ConstraintLayout>(R.id.cnt_view) as ConstraintLayout

        imageViewPin.setImageResource(ApplicationClass.GetPin(this@Bussiness_Location,masterBeaconInfo.mappin!!))
        CntView.setBackgroundResource(ApplicationClass.GetPinView(this@Bussiness_Location,masterBeaconInfo.mappin!!))

        //Business Image showen
        Log.e("TAG", "IMAGEBUSINESS")
        Picasso.with(this).load(masterBeaconInfo.businesstreadetype).into(imageViewBusiness,
            object : com.squareup.picasso.Callback {
                override fun onSuccess() {

                }

                override fun onError() {
                }
            })


        textView.text = masterBeaconInfo.bussiness_name
        textView.setTextColor(
            ContextCompat.getColor(
                this@Bussiness_Location,
                R.color.white
            )
        )

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        customMarkerView.layout(
            0,
            0,
            customMarkerView.measuredWidth,
            customMarkerView.measuredHeight
        )
        customMarkerView.buildDrawingCache()
        val returnedBitmap = Bitmap.createBitmap(
            customMarkerView.measuredWidth,
            customMarkerView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawable = customMarkerView.background
        if (drawable != null)
            drawable.draw(canvas)
        customMarkerView.draw(canvas)
        return returnedBitmap
    }
    private fun SetBusinessViews() {
        Log.e("TAG", "COLORLIST: " + masterList.size)
        for (BusinessPos in 0..masterList.size - 1) {
            Log.e("TAG", "COLORLIST: " + BusinessPos)

            mMap.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(
                            masterList[BusinessPos].latitude!!.toDouble(),
                            masterList[BusinessPos].longitude!!.toDouble()
                        )
                    )
                    .zIndex(BusinessPos.toFloat())
//                    .title(Business.bussiness_name)
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(masterList[BusinessPos])))
//                    .icon(BitmapDescriptorFactory.fromBitmap(GetMarkerPin(masterList[BusinessPos])))
            )
        }
    }

    private fun GetMarkerPin(businessBescon: MasterBeaconModel.BusinessBescon): Bitmap {
//        BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(masterList[BusinessPos]))
        val customMarkerView: View =
            (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.map_marker,
                null
            )

        var mainMarkerView = customMarkerView.findViewById<ConstraintLayout>(R.id.cnt_marker)
        mainMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        mainMarkerView.layout(0, 0, mainMarkerView.measuredWidth, mainMarkerView.measuredHeight)
        val bitmap = mainMarkerView.drawToBitmap(Bitmap.Config.ARGB_8888)
        return bitmap
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

    private fun initListener() {
        mMtCentralManager!!.setMTCentralManagerListener { peripherals ->
            for (peripheral in peripherals) {
                val Mac = peripheral.mMTFrameHandler.mac.replace(":", "")
                if (peripheral.mMTFrameHandler.name.contains(Constants.masterBeacon)) {
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

    private fun ensureBleExists(): Boolean {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Phone does not support BLE", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    override fun onClick(view: View?) {
        try {
            if (view == DB_BusinessLocation.cntNotfound) {
                ShowBusinessReport()
            } else if (view == mTxtSubmit) {
                if (mCh1.isChecked) {
                    notFoundBusinessName = edtBussinessName.text.toString()
                    if (!notFoundBusinessName.equals("".trim(), true)) {
                        CloseViews()
                        notFoundBusinessReason = getString(R.string.businessrason1)
                        BusinessNotFound()
                    } else {
                        // Business Name is mendatory
                        ShowAlert("Business name is require.")
                    }
                } else if (mCh2.isChecked) {
                    CloseViews()
                    notFoundBusinessReason = getString(R.string.businessrason2)
                    BusinessNotFound()
                } else if (mCh3.isChecked) {
                    CloseViews()
                    notFoundBusinessReason = getString(R.string.businessrason3)
                    BusinessNotFound()
                } else {
                    Toast.makeText(
                        this,
                        "Please select atleast one option from above.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (view == mCntBackToHome) {
                onBackPressed()
            } else if (view == DB_BusinessLocation.cntCompany) {
//                ShowBusniessConfirmation()
            } else if (view == mImgClose) {
                ShowBusniessMainView()
            } else if (view == mImgBusRepClose) {
                ShowBusniessMainView()
            } else if (view == mCntConfirm) {
                ShowBusniessMainView()
                BusinessCheckInFlow()
            } else if (view == DB_BusinessLocation.txtUnauthOk) {
                HideUnauthorization()
                ApplicationClass.UserLogout(this)
            } else if (view == DB_BusinessLocation.txtAlertok) {
                HideAlert()
            } else if (view == DB_BusinessLocation.layoutBussinessConfirm.cntNotfind) {
                HideBusinessConfiramation()
                ShowBusinessReport()
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

    private fun ShowBusniessMainView() {
        CloseViews()
        DB_BusinessLocation.cntBusMainView.visibility = View.VISIBLE
    }

    private fun HideBusinessConfiramation() {
        DB_BusinessLocation.layoutBussinessConfirm.BusinessConfirm.visibility = View.GONE
    }

    private fun ShowBusinessReport() {
        CloseViews()
        DB_BusinessLocation.layoutBussinessReport.BusinessReport.visibility = View.VISIBLE
    }

    private fun HideUnauthorization() {
        DB_BusinessLocation.cntUnAuthorized.visibility = View.GONE
    }

    private fun HideAlert() {
        DB_BusinessLocation.cntAlert.visibility = View.GONE
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
            mMap.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
                // which is clicked and displaying it in a toast message.
                val markerName = marker.title
//                Toast.makeText(
//                    this@Bussiness_Location,
//                    "Clicked location is ${marker.zIndex}",
//                    Toast.LENGTH_SHORT
//                ).show()

                ApplicationClass.selectedMasterModel = masterList[marker.zIndex.toInt()]

                Picasso.with(this)
                    .load(ApplicationClass.selectedMasterModel.businessimage)
                    .into(mImgLogo)
                mTxtBusinessName.text = ApplicationClass.selectedMasterModel.bussiness_name
                ShowBusniessConfirmation()

//                BusinessCheckInFlow(marker.zIndex.toInt())

                false
            }
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

    private fun ShowBusniessConfirmation() {
        CloseViews()
        DB_BusinessLocation.layoutBussinessConfirm.BusinessConfirm.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHECK_IN_CODE && resultCode == Activity.RESULT_OK) {
            //Rescan Here
            mMap.clear()
            BleScan()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mMtCentralManager!!.stopScan()
        handler.removeCallbacks(runnable);
    }

    private fun BusinessCheckInFlow() {
        if (ApplicationClass.selectedMasterModel.check_in!!.equals("Yes", true)) {
            var ReviewIntent = Intent(this, SearchEmployee::class.java)
            startActivityForResult(ReviewIntent, CHECK_IN_CODE)
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        } else {
            var BusinessCheckInIntent = Intent(this, BussinessCheckIn::class.java)
            startActivityForResult(BusinessCheckInIntent, CHECK_IN_CODE)
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        }
    }
}
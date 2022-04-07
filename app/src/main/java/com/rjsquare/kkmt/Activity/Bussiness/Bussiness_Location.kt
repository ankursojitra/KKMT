package com.rjsquare.kkmt.Activity.Bussiness

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.minew.beaconplus.sdk.MTCentralManager
import com.minew.beaconplus.sdk.enums.BluetoothState
import com.minew.beaconplus.sdk.interfaces.OnBluetoothStateChangedListener
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.LogInCall.BusinessBeaconService
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.BusinessBeaconModel
import com.rjsquare.kkmt.databinding.ActivityLocationBinding
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    private val BLE_DEVICE_INFO_CALL = 3000L
    private val REQUEST_ENABLE_BT = 3
    private val PERMISSION_COARSE_LOCATION = 2
    private var mMtCentralManager: MTCentralManager? = null
    lateinit var BeaconMACList: ArrayList<String>
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    var HandlerCallavailable = false
    lateinit var BusinessList: ArrayList<BusinessBeaconModel.BusinessBescon>

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

            DB_BusinessLocation.cntLoader.visibility = View.VISIBLE
            DB_BusinessLocation.rippleback.startRippleAnimation()
            thisBussiness_Activity = this
            BeaconMACList = ArrayList()
            handler = Handler()
            HandlerCallavailable = true
            BusinessList = ArrayList()
            if (!ensureBleExists()) finish()

            if (!isBLEEnabled()) {
                showBLEDialog()
            }
            initManager()
            getRequiredPermissions()
            initListener()

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
            DB_BusinessLocation.txtUnauthOk.setOnClickListener(this)
            DB_BusinessLocation.cntCompany.visibility = View.GONE

//            val handler = Handler()
//            val runnable = Runnable {
//                foundDevice()
//            }
//            handler.postDelayed(runnable, 3000)

            runnable = Runnable {
//                foundDevice()
                mMtCentralManager!!.stopScan()
                MasterBleDeviceInfo()
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

    private fun MasterBleDeviceInfo() {
        var beaconOBJ = JSONObject()
        var arrayJ = JSONArray()
        for (Mac in BeaconMACList) {
            arrayJ.put(Mac)
        }
        beaconOBJ.put("becon_list", arrayJ)
        try {
            //Here the json data is add to a hash map with key data
            val params: MutableMap<String, String> =
                HashMap()


            params[ApplicationClass.paramKey_UserId] =
                ApplicationClass.userInfoModel.data!!.userid!!

            val service =
                ApiCallingInstance.retrofitInstance.create<BusinessBeaconService>(
                    BusinessBeaconService::class.java
                )
            val call =
                service.GetBusinessBeaconData(
                    params, BeaconMACList, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<BusinessBeaconModel> {
                override fun onFailure(call: Call<BusinessBeaconModel>, t: Throwable) {
                    DB_BusinessLocation.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<BusinessBeaconModel>,
                    response: Response<BusinessBeaconModel>
                ) {
                    DB_BusinessLocation.cntLoader.visibility = View.GONE
                    Log.e("TAG", "CHECKRESPONSE : " + Gson().toJson(response.body()))

                    if (response.body()!!.status.equals(ApplicationClass.ResponseSucess)) {
                        BusinessList = response.body()!!.data!!
                        SetBusinessViews()
                    } else if (response.body()!!.status.equals(ApplicationClass.ResponseUnauthorized)) {
                        DB_BusinessLocation.cntUnAuthorized.visibility = View.VISIBLE
                    } else if (response.body()!!.status.equals(ApplicationClass.ResponseEmpltyList)) {

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

    fun getARGB(hex: Int): IntArray? {
        val a = hex and -0x1000000 shr 24
        val r = hex and 0xFF0000 shr 16
        val g = hex and 0xFF00 shr 8
        val b = hex and 0xFF
        return intArrayOf(a, r, g, b)
    }

    fun hexToIntColor(hex: String): IntArray {
//        var Alpha = Integer.valueOf(hex.substring(0, 2), 16)
        var Red = Integer.valueOf(hex.substring(0, 2), 16)
        var Green = Integer.valueOf(hex.substring(2, 4), 16)
        var Blue = Integer.valueOf(hex.substring(4, 6), 16)
//        Alpha = Alpha shl 24 and -0x1000000
        Red = Red shl 16 and 0x00FF0000
        Green = Green shl 8 and 0x0000FF00
        Blue = Blue and 0x000000FF
//        return Alpha or Red or Green or Blue
        return intArrayOf(Red, Green, Blue)
    }

    private fun getMarkerBitmapFromView(businessBeaconInfo: BusinessBeaconModel.BusinessBescon): Bitmap {
        //HERE YOU CAN ADD YOUR CUSTOM VIEW

        val customMarkerView: View =
            (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.map_marker,
                null
            )
        val textView = customMarkerView.findViewById<View>(R.id.txt_name) as TextView
        val imageViewPin = customMarkerView.findViewById<ImageView>(R.id.img_pin) as ImageView
//        var Code = hexToIntColor((businessBeaconInfo.mappin!!.replace("#", "")))
//        imageViewPin.setColorFilter(Color.rgb(Code[0], Code[1], Code[2]))

        imageViewPin.setImageResource(GetPin(businessBeaconInfo.mappin!!))

        textView.setText(businessBeaconInfo.bussiness_name);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(
            0,
            0,
            customMarkerView.getMeasuredWidth(),
            customMarkerView.getMeasuredHeight()
        );
        customMarkerView.buildDrawingCache();
        val returnedBitmap = Bitmap.createBitmap(
            customMarkerView.measuredWidth,
            customMarkerView.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(returnedBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        val drawable = customMarkerView.background
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    @SuppressLint("ResourceType")
    private fun GetPin(pinColor: String): Int {
        Log.e("TAG", "COLORXXX :" + pinColor)
        if (getResources().getString(R.color.gray_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_gray
        } else if (getResources().getString(R.color.orange_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_orange
        } else if (getResources().getString(R.color.green_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_green
        } else if (getResources().getString(R.color.pink_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_pink
        } else if (getResources().getString(R.color.blue_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_blue
        } else if (getResources().getString(R.color.red_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_red
        } else if (getResources().getString(R.color.yellow_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_yellow
        } else if (getResources().getString(R.color.purple_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_purple
        } else if (getResources().getString(R.color.black_pin).equals(pinColor, true)) {
            return R.drawable.ic_pin_black
        } else {
            return R.drawable.ic_pin_red
        }
    }

    private fun SetBusinessViews() {

        Log.e("TAG","COLORLIST: "+BusinessList.size)
        for (Business in BusinessList) {

            mMap.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(
                            Business.latitude!!.toDouble(),
                            Business.longitude!!.toDouble()
                        )
                    )
                    .title(Business.bussiness_name)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(Business)))
            )
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

    private fun initListener() {
        mMtCentralManager!!.setMTCentralManagerListener { peripherals ->
            Log.e("demo", "scan size is: " + peripherals.size)
//            Log.d("demo", "scan peripherals is: " + peripherals)
            Log.e("demo", "scan peripherals is: " + peripherals[0].mMTFrameHandler.mac)
            for (peripheral in peripherals) {
                val Mac = peripheral.mMTFrameHandler.mac.replace(":", "")
                if (!BeaconMACList.contains(Mac)) {
                    BeaconMACList.add(Mac)
                }
            }
            if (HandlerCallavailable) {
                HandlerCallavailable = false
                handler.postDelayed(runnable, BLE_DEVICE_INFO_CALL)
            }
//            mAdapter.setData(peripherals)
        }
//        mAdapter.setOnItemClickListener(object : OnItemClickListener() {
//            fun onItemClick(view: View?, position: Int) {
//                val mtPeripheral: MTPeripheral = mAdapter.getData(position)
//                mMtCentralManager!!.connect(mtPeripheral, connectionStatueListener)
//            }
//            fun onItemLongClick(view: View?, position: Int) {}
//        })
    }

    private fun initData() {
        //三星手机系统可能会限制息屏下扫描，导致息屏后无法获取到广播数据
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
            } else if (view == DB_BusinessLocation.txtUnauthOk) {
                DB_BusinessLocation.cntUnAuthorized.visibility = View.GONE
                ApplicationClass.UserLogout(this)
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
            mMap.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
                // which is clicked and displaying it in a toast message.
                val markerName = marker.title
                Toast.makeText(
                    this@Bussiness_Location,
                    "Clicked location is $markerName",
                    Toast.LENGTH_SHORT
                ).show()
                marker.id
                BusinessCheckInFlow()

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

    private fun BusinessCheckInFlow() {

    }
}
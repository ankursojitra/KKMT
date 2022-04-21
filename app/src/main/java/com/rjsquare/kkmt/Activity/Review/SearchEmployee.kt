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
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.minew.beaconplus.sdk.MTCentralManager
import com.minew.beaconplus.sdk.enums.BluetoothState
import com.minew.beaconplus.sdk.interfaces.OnBluetoothStateChangedListener
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable.ApiCallingInstance
import com.rjsquare.kkmt.Activity.Bussiness.BussinessCheckIn
import com.rjsquare.kkmt.Activity.Bussiness.Bussiness_Location
import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.R
import com.rjsquare.kkmt.RetrofitInstance.Events.NetworkServices
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.EmployeeNotFoundModel
import com.rjsquare.kkmt.RetrofitInstance.OTPCall.SlaveBeaconModel
import com.rjsquare.kkmt.databinding.ActivitySearchEmployeeBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search_employee.view.*
import kotlinx.android.synthetic.main.child_view.view.*
import kotlinx.android.synthetic.main.layout_helper_report.view.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*


class SearchEmployee : AppCompatActivity(), View.OnClickListener {

    private lateinit var mCh1: CheckBox
    private lateinit var mCh2: CheckBox
    private lateinit var mCh3: CheckBox
    private lateinit var mCntEmployeekkmtID: ConstraintLayout
    private lateinit var mTxtSubmit: TextView
    private lateinit var mTxtBacktohome: TextView
    private val BLE_DEVICE_INFO_CALL = 10000L
    private val REQUEST_ENABLE_BT = 3
    private lateinit var edtEmployeekkmtID: EditText

    var PDFString: String = ""
    var photoFileName = "photo.jpg"
    var photoFile: File? = null
    private var mMtCentralManager: MTCentralManager? = null
    lateinit var BeaconMACList: ArrayList<String>
    lateinit var EmpViewList: ArrayList<ConstraintLayout>
    lateinit var SlaveViewList: ArrayList<ConstraintLayout>
    var HandlerCallavailable = false

    private lateinit var mImgClose: ImageView
    private lateinit var imgCamera: ImageView
    private val PERMISSION_COARSE_LOCATION = 2
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    val size = 100
    var notFoundEmployeeReason = ""
    var notFoundEmployeekkmtid = ""


    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
    }

    companion object {
        lateinit var thisSearchEmployee: Activity
        lateinit var DB_SearchEmployee: ActivitySearchEmployeeBinding
        lateinit var inflater: LayoutInflater
        private lateinit var mTxtView: TextView
        var PosList = ArrayList<Int>()

        private fun AnimateDevice(mView: ConstraintLayout) {
            try {
                val dx: Float = mView.x
                val dy: Float = mView.y
//
                mView.visibility = View.VISIBLE
                mView.animate()
                    .x(dx)
                    .y(dy)
                    .setDuration(500)
                    .start()
                val animatorSet = AnimatorSet()
                animatorSet.duration = 500
                animatorSet.interpolator = AccelerateDecelerateInterpolator()
                val animatorList = ArrayList<Animator>()
                val scaleXAnimator =
                    ObjectAnimator.ofFloat(mView, "ScaleX", 0f, 1.2f, 1f)
                animatorList.add(scaleXAnimator)
                val scaleYAnimator =
                    ObjectAnimator.ofFloat(mView, "ScaleY", 0f, 1.2f, 1f)
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

        private fun GetNumber(): Int {
            return Random().nextInt(12)
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

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB_SearchEmployee = DataBindingUtil.setContentView(this, R.layout.activity_search_employee)
        thisSearchEmployee = this

        ApplicationClass.StatusTextWhite(this, false)
        DB_SearchEmployee.rippleBack.startRippleAnimation()
        EmpViewList = ArrayList()
        SlaveViewList = ArrayList()


        SetUpEmpView()
        mCh1 = DB_SearchEmployee.layoutHelperReport.ch1
        mCh2 = DB_SearchEmployee.layoutHelperReport.ch2
        mCh3 = DB_SearchEmployee.layoutHelperReport.ch3
        mCntEmployeekkmtID =
            DB_SearchEmployee.layoutHelperReport.cntReportview.crd_reportemp.cnt_csrpic
        edtEmployeekkmtID =
            DB_SearchEmployee.layoutHelperReport.cntReportview.edt_csr

        mCh1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCh1.isChecked = true
                mCh2.isChecked = false
                mCh3.isChecked = false
                mCntEmployeekkmtID.visibility = View.VISIBLE
            }
        }

        mCh2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCh1.isChecked = false
                mCh2.isChecked = true
                mCh3.isChecked = false
                mCntEmployeekkmtID.visibility = View.GONE
            }
        }

        mCh3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mCh1.isChecked = false
                mCh2.isChecked = false
                mCh3.isChecked = true
                mCntEmployeekkmtID.visibility = View.GONE
            }
        }



        mTxtBacktohome = DB_SearchEmployee.layoutHelperThankyou.txtBacktohome
        mTxtSubmit = DB_SearchEmployee.layoutHelperReport.txtSubmit
        this.imgCamera = DB_SearchEmployee.layoutHelperReport.imgCamera
        mImgClose = DB_SearchEmployee.layoutHelperReport.imgClose

        DB_SearchEmployee.cntNotfound.setOnClickListener(this)
        this.imgCamera.setOnClickListener(this)
        mTxtBacktohome.setOnClickListener(this)
        mTxtSubmit.setOnClickListener(this)
        mImgClose.setOnClickListener(this)
        DB_SearchEmployee.txtUnauthOk.setOnClickListener(this)
        DB_SearchEmployee.txtAlertok.setOnClickListener(this)

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


        runnable = Runnable {
            mMtCentralManager!!.stopScan()
            SlaveBleDeviceInfo()
        }
    }

    private fun SetUpEmpView() {
        EmpViewList.add(DB_SearchEmployee.cntView1)
        EmpViewList.add(DB_SearchEmployee.cntView2)
        EmpViewList.add(DB_SearchEmployee.cntView3)
        EmpViewList.add(DB_SearchEmployee.cntView4)
        EmpViewList.add(DB_SearchEmployee.cntView5)
        EmpViewList.add(DB_SearchEmployee.cntView6)
        EmpViewList.add(DB_SearchEmployee.cntView7)
        EmpViewList.add(DB_SearchEmployee.cntView8)
        EmpViewList.add(DB_SearchEmployee.cntView9)
        EmpViewList.add(DB_SearchEmployee.cntView10)
        EmpViewList.add(DB_SearchEmployee.cntView11)
        EmpViewList.add(DB_SearchEmployee.cntView12)

        for (empView in EmpViewList) {
            empView.visibility = View.INVISIBLE
        }

        DB_SearchEmployee.cntBusinessView.setBackgroundResource(
            ApplicationClass.GetPinView(
                this@SearchEmployee,
                ApplicationClass.selectedMasterModel.mappin!!
            )
        )

        Picasso.with(this).load(ApplicationClass.selectedMasterModel.businessimage)
            .placeholder(R.drawable.ic_expe_logo).into(DB_SearchEmployee.imgBusiness)

        DB_SearchEmployee.txtBusname.text = ApplicationClass.selectedMasterModel.bussiness_name
        DB_SearchEmployee.txtCompanyname.text = ApplicationClass.selectedMasterModel.bussiness_name

    }

    private fun SlaveBleDeviceInfo() {
        DB_SearchEmployee.cntLoader.visibility = View.VISIBLE
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
                ApplicationClass.selectedMasterModel.businessid.toString()

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.SlaveBeaconService>(
                    NetworkServices.SlaveBeaconService::class.java
                )
            val call =
                service.GetSlaveBeaconData(
                    params, BeaconMACList,
                    ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<SlaveBeaconModel> {
                override fun onFailure(call: Call<SlaveBeaconModel>, t: Throwable) {
                    DB_SearchEmployee.cntLoader.visibility = View.GONE
                    Log.e("TAG", "CHECKERROR : " + t)
                }

                override fun onResponse(
                    call: Call<SlaveBeaconModel>,
                    response: Response<SlaveBeaconModel>
                ) {
                    DB_SearchEmployee.cntLoader.visibility = View.GONE
                    Log.e("TAG", "CHECKRESPONSESlave : " + Gson().toJson(response.body()))

                    if (response.body()!!.status.equals(Constants.ResponseSucess)) {
                        ApplicationClass.slaveModellist = ArrayList()
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

        var TotalViews = ApplicationClass.slaveModellist.size

        GenerateRandomPos(TotalViews)
        for (pos in 0..TotalViews - 1) {
            SetUpDataInView(ApplicationClass.slaveModellist[pos], PosList[pos])
        }
    }

    private fun SetUpDataInView(slaveBescon: SlaveBeaconModel.SlaveBescon, Position: Int) {

        when (Position) {
            0 -> {
                DB_SearchEmployee.cntView1.ly_emp1.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView1.ly_emp1.img_emp)
                DB_SearchEmployee.cntView1.visibility = View.VISIBLE
                DB_SearchEmployee.cntView1.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView1)
            }
            1 -> {
                DB_SearchEmployee.cntView2.ly_emp2.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView2.ly_emp2.img_emp)
                DB_SearchEmployee.cntView2.visibility = View.VISIBLE
                DB_SearchEmployee.cntView2.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView2)
            }
            2 -> {
                DB_SearchEmployee.cntView3.ly_emp3.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView3.ly_emp3.img_emp)
                DB_SearchEmployee.cntView3.visibility = View.VISIBLE
                DB_SearchEmployee.cntView3.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView3)
            }
            3 -> {
                DB_SearchEmployee.cntView4.ly_emp4.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView4.ly_emp4.img_emp)
                DB_SearchEmployee.cntView4.visibility = View.VISIBLE
                DB_SearchEmployee.cntView4.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView4)
            }
            4 -> {
                DB_SearchEmployee.cntView5.ly_emp5.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView5.ly_emp5.img_emp)
                DB_SearchEmployee.cntView5.visibility = View.VISIBLE
                DB_SearchEmployee.cntView5.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView5)
            }
            5 -> {
                DB_SearchEmployee.cntView6.ly_emp6.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView6.ly_emp6.img_emp)
                DB_SearchEmployee.cntView6.visibility = View.VISIBLE
                DB_SearchEmployee.cntView6.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView6)
            }
            6 -> {
                DB_SearchEmployee.cntView7.ly_emp7.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView7.ly_emp7.img_emp)
                DB_SearchEmployee.cntView7.visibility = View.VISIBLE
                DB_SearchEmployee.cntView7.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView7)
            }
            7 -> {
                DB_SearchEmployee.cntView8.ly_emp8.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView8.ly_emp8.img_emp)
                DB_SearchEmployee.cntView8.visibility = View.VISIBLE
                DB_SearchEmployee.cntView8.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView8)
            }
            8 -> {
                DB_SearchEmployee.cntView9.ly_emp9.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView9.ly_emp9.img_emp)
                DB_SearchEmployee.cntView9.visibility = View.VISIBLE
                DB_SearchEmployee.cntView9.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView9)
            }
            9 -> {
                DB_SearchEmployee.cntView10.ly_emp10.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView10.ly_emp10.img_emp)
                DB_SearchEmployee.cntView10.visibility = View.VISIBLE
                DB_SearchEmployee.cntView10.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView10)
            }
            10 -> {
                DB_SearchEmployee.cntView11.ly_emp11.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView11.ly_emp11.img_emp)
                DB_SearchEmployee.cntView11.visibility = View.VISIBLE
                DB_SearchEmployee.cntView11.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView11)
            }
            11 -> {
                DB_SearchEmployee.cntView12.ly_emp12.txt_employeename.text = slaveBescon.username
                Picasso.with(this).load(slaveBescon.employeeimage)
                    .placeholder(R.drawable.ic_expe_logo)
                    .into(DB_SearchEmployee.cntView12.ly_emp12.img_emp)
                DB_SearchEmployee.cntView12.visibility = View.VISIBLE
                DB_SearchEmployee.cntView12.setOnClickListener(View.OnClickListener {
                    ApplicationClass.empSlaveModel = slaveBescon
                    ReviewScreen()
                })
                AnimateDevice(DB_SearchEmployee.cntView12)
            }
            else -> { // Note the block

            }
        }
    }

    private fun EmployeeNotFound() {
        DB_SearchEmployee.cntLoader.visibility = View.VISIBLE
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

            params[Constants.paramKey_BussinessId] =
                ApplicationClass.selectedMasterModel.businessid.toString()

            params[Constants.paramKey_Reason] = notFoundEmployeeReason
            params[Constants.paramKey_KKMTID] = notFoundEmployeekkmtid
            params[Constants.paramKey_Image] = PDFString

            val service =
                ApiCallingInstance.retrofitInstance.create<NetworkServices.EmployeeNotFoundService>(
                    NetworkServices.EmployeeNotFoundService::class.java
                )
            val call =
                service.GetEmployeeNotFoundData(
                    params, ApplicationClass.userInfoModel.data!!.access_token!!
                )

            call.enqueue(object : Callback<EmployeeNotFoundModel> {
                override fun onFailure(call: Call<EmployeeNotFoundModel>, t: Throwable) {
                    DB_SearchEmployee.cntLoader.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<EmployeeNotFoundModel>,
                    response: Response<EmployeeNotFoundModel>
                ) {
                    DB_SearchEmployee.cntLoader.visibility = View.GONE
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

    private fun ShowAlert(message: String?) {
        DB_SearchEmployee.txtAlertmsg.text = message
        DB_SearchEmployee.cntAlert.visibility = View.VISIBLE
    }

    private fun ShowThankyouPopup() {
        CloseViews()
        DB_SearchEmployee.layoutHelperThankyou.cntReportThankYouView.visibility = View.VISIBLE
    }

    private fun ShowUnauthorization() {
        CloseViews()
        DB_SearchEmployee.cntUnAuthorized.visibility = View.VISIBLE
    }

    private fun ReviewScreen() {
        ApplicationClass.isNewReview = true
        var HelperIntent = Intent(this, ReviewEdit::class.java)
        startActivity(HelperIntent)
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
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

    private fun HideAlert() {
        Log.e("TAG", "CHECKALERT")
        DB_SearchEmployee.cntAlert.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        if (System.currentTimeMillis() < ApplicationClass.lastClick) return else {
            ApplicationClass.lastClick = System.currentTimeMillis() + ApplicationClass.clickInterval
            if (view == DB_SearchEmployee.cntNotfound) {
                CloseViews()
                DB_SearchEmployee.layoutHelperReport.cntReportview.visibility = View.VISIBLE
            } else if (view == mTxtSubmit) {
                if (mCh1.isChecked) {
                    notFoundEmployeekkmtid = edtEmployeekkmtID.text.toString()
                    notFoundEmployeeReason = getString(R.string.empreson1)
                    if (!notFoundEmployeeReason.equals("".trim(), true)) {
                        EmployeeNotFound()
                    } else {
                        ShowAlert("KKMT ID required.")
                    }
                } else if (mCh2.isChecked) {
                    notFoundEmployeeReason = getString(R.string.empreson2)
                    EmployeeNotFound()

                } else if (mCh3.isChecked) {
                    notFoundEmployeeReason = getString(R.string.empreson3)
                    EmployeeNotFound()
                }
            } else if (view == DB_SearchEmployee.txtAlertok) {
                Log.e("TAG", "AlertOk")
                HideAlert()
            } else if (view == mTxtBacktohome) {
                if (!ApplicationClass.selectedMasterModel.check_in!!.equals("Yes", true)) {
                    BussinessCheckIn.thisBusinessCheckIn.finish()
                }
                Bussiness_Location.thisBussiness_Activity.finish()
                finish()
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out)
            } else if (view == this.imgCamera) {
                TakeCameraPicture()

            } else if (view == mImgClose) {
                CloseViews()
                DB_SearchEmployee.cntEmpmainView.visibility = View.VISIBLE
            } else if (view == DB_SearchEmployee.txtUnauthOk) {
                ApplicationClass.UserLogout(this)
            }
        }
    }

    private fun TakeCameraPicture() {
        val permissions =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        Permissions.check(this, permissions, null, null, object : PermissionHandler() {
            override fun onGranted() {
                // do your task.
                selectImageCam()
            }

            override fun onDenied(
                context: Context?,
                deniedPermissions: java.util.ArrayList<String>?
            ) {
                super.onDenied(context, deniedPermissions)
            }

            override fun onBlocked(
                context: Context?,
                blockedList: java.util.ArrayList<String>?
            ): Boolean {
                return super.onBlocked(context, blockedList)
            }

            override fun onJustBlocked(
                context: Context?,
                justBlockedList: java.util.ArrayList<String>?,
                deniedPermissions: java.util.ArrayList<String>?
            ) {
                super.onJustBlocked(context, justBlockedList, deniedPermissions)
            }
        })
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File {
        val mediaStorageDir =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "KKMT")

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("KKMT", "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === RESULT_OK) {
            DB_SearchEmployee.cntLoader.visibility = View.VISIBLE
            ConvertFileOrImageToString(Uri.fromFile(photoFile))
        }
    }

    private fun selectImageCam() {
        // Initialize intent
        photoFile = getPhotoFileUri(photoFileName)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    this,
                    "com.rjsquare.kkmt.fileprovider",
                    photoFile!!
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, 1)
            }
        } else {
            Log.e("TAG", "ActivityNotFound")
        }
    }

    fun ConvertFileOrImageToString(sUri: Uri) {
        //Convert File to Base64String
        try {
            val baos = ByteArrayOutputStream()
            val `in` = contentResolver.openInputStream(sUri)
            val bytes: ByteArray = getBytes(`in`!!)!!

            PDFString = Base64.encodeToString(bytes, Base64.DEFAULT)

            Log.e("TAG", "ActivityResult: " + PDFString)
            DB_SearchEmployee.cntLoader.visibility = View.GONE
        } catch (e: java.lang.Exception) {
            // TODO: handle exception
            e.printStackTrace()
            Log.d("error", "onActivityResult: $e")
            DB_SearchEmployee.cntLoader.visibility = View.GONE
        }
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMtCentralManager!!.stopScan()
        handler.removeCallbacks(runnable)
    }

    private fun CloseViews() {
        DB_SearchEmployee.cntEmpmainView.visibility = View.GONE
        DB_SearchEmployee.layoutHelperReport.cntReportview.visibility = View.GONE
        DB_SearchEmployee.layoutHelperThankyou.cntReportThankYouView.visibility = View.GONE
    }

}
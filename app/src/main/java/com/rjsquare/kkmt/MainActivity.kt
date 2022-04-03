package com.rjsquare.kkmt

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.minew.beaconplus.sdk.MTCentralManager
import com.minew.beaconplus.sdk.enums.BluetoothState
import com.minew.beaconplus.sdk.enums.ConnectionStatus
import com.minew.beaconplus.sdk.exception.MTException
import com.minew.beaconplus.sdk.interfaces.ConnectionStatueListener
import com.minew.beaconplus.sdk.interfaces.GetPasswordListener
import com.minew.beaconplus.sdk.interfaces.OnBluetoothStateChangedListener

class MainActivity : AppCompatActivity() {

    private val REQUEST_ENABLE_BT = 3
    private val PERMISSION_COARSE_LOCATION = 2

    private var mMtCentralManager: MTCentralManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)


        if (!ensureBleExists()) finish()
        if (!isBLEEnabled()) {
            showBLEDialog()
        }
        initView()
        initManager()
        getRequiredPermissions()
        initListener()
    }

    private fun initListener() {
        mMtCentralManager!!.setMTCentralManagerListener { peripherals ->
            Log.e("demo", "scan size is: " + peripherals.size)
//            mAdapter.setData(peripherals)
        }
//        mAdapter.setOnItemClickListener(object : OnItemClickListener() {
//            fun onItemClick(view: View?, position: Int) {
//                val mtPeripheral: MTPeripheral = mAdapter.getData(position)
//                mMtCentralManager!!.connect(mtPeripheral, connectionStatueListener)
//            }
//
//            fun onItemLongClick(view: View?, position: Int) {}
//        })
    }

    private fun ensureBleExists(): Boolean {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Phone does not support BLE", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private val connectionStatueListener: ConnectionStatueListener =
        object : ConnectionStatueListener {
            override fun onUpdateConnectionStatus(
                connectionStatus: ConnectionStatus,
                getPasswordListener: GetPasswordListener
            ) {
                runOnUiThread {
                    when (connectionStatus) {
                        ConnectionStatus.CONNECTING -> {
                            Log.e("tag", "CONNECTING")
                            Toast.makeText(this@MainActivity, "CONNECTING", Toast.LENGTH_SHORT)
                                .show()
                        }
                        ConnectionStatus.CONNECTED -> {
                            Log.e("tag", "CONNECTED")
                            Toast.makeText(this@MainActivity, "CONNECTED", Toast.LENGTH_SHORT)
                                .show()
                        }
                        ConnectionStatus.READINGINFO -> {
                            Log.e("tag", "READINGINFO")
                            Toast.makeText(this@MainActivity, "READINGINFO", Toast.LENGTH_SHORT)
                                .show()
                        }
                        ConnectionStatus.DEVICEVALIDATING -> {
                            Log.e("tag", "DEVICEVALIDATING")
                            Toast.makeText(
                                this@MainActivity,
                                "DEVICEVALIDATING",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        ConnectionStatus.PASSWORDVALIDATING -> {
                            Log.e("tag", "PASSWORDVALIDATING")
                            Toast.makeText(
                                this@MainActivity,
                                "PASSWORDVALIDATING",
                                Toast.LENGTH_SHORT
                            ).show()
                            val password = "minew123"
                            getPasswordListener.getPassword(password)
                        }
                        ConnectionStatus.SYNCHRONIZINGTIME -> {
                            Log.e("tag", "SYNCHRONIZINGTIME")
                            Toast.makeText(
                                this@MainActivity,
                                "SYNCHRONIZINGTIME",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        ConnectionStatus.READINGCONNECTABLE -> {
                            Log.e("tag", "READINGCONNECTABLE")
                            Toast.makeText(
                                this@MainActivity,
                                "READINGCONNECTABLE",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        ConnectionStatus.READINGFEATURE -> {
                            Log.e("tag", "READINGFEATURE")
                            Toast.makeText(
                                this@MainActivity,
                                "READINGFEATURE",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        ConnectionStatus.READINGFRAMES -> {
                            Log.e("tag", "READINGFRAMES")
                            Toast.makeText(
                                this@MainActivity,
                                "READINGFRAMES",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        ConnectionStatus.READINGTRIGGERS -> {
                            Log.e("tag", "READINGTRIGGERS")
                            Toast.makeText(
                                this@MainActivity,
                                "READINGTRIGGERS",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        ConnectionStatus.COMPLETED -> {
                            Log.e("tag", "COMPLETED")
                            Toast.makeText(this@MainActivity, "COMPLETED", Toast.LENGTH_SHORT)
                                .show()
                        }
                        ConnectionStatus.CONNECTFAILED, ConnectionStatus.DISCONNECTED -> {
                            Log.e("tag", "DISCONNECTED")
                            Toast.makeText(
                                this@MainActivity,
                                "DISCONNECTED",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            override fun onError(e: MTException) {
                Log.e("tag", e.message)
            }
        }


    protected fun isBLEEnabled(): Boolean {
        val bluetoothManager =
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter
        return adapter != null && adapter.isEnabled
    }

    private fun showBLEDialog() {
        val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT)
    }

    private fun initView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
//        mRecycle.setLayoutManager(layoutManager)
//        mAdapter = RecycleAdapter()
//        mRecycle.setAdapter(mAdapter)
//        mRecycle.addItemDecoration(RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL))
    }

    override fun onRequestPermissionsResult(
        Code: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(Code, permissions, grantResults)
        when (Code) {
            PERMISSION_COARSE_LOCATION -> if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                initData()
            } else {
                finish()
            }
        }
    }


    private fun initManager() {
        mMtCentralManager = MTCentralManager.getInstance(this)
        //startservice
        mMtCentralManager!!.startService()
        val bluetoothState: BluetoothState = mMtCentralManager!!.getBluetoothState(this)
        when (bluetoothState) {
            BluetoothState.BluetoothStateNotSupported -> Log.e(
                "tag",
                "BluetoothStateNotSupported"
            )
            BluetoothState.BluetoothStatePowerOff -> Log.e(
                "tag",
                "BluetoothStatePowerOff"
            )
            BluetoothState.BluetoothStatePowerOn -> Log.e(
                "tag",
                "BluetoothStatePowerOn"
            )
        }
        mMtCentralManager!!.setBluetoothChangedListener(OnBluetoothStateChangedListener { state ->
            when (state) {
                BluetoothState.BluetoothStateNotSupported -> Log.e(
                    "tag",
                    "BluetoothStateNotSupported"
                )
                BluetoothState.BluetoothStatePowerOff -> Log.e(
                    "tag",
                    "BluetoothStatePowerOff"
                )
                BluetoothState.BluetoothStatePowerOn -> Log.e(
                    "tag",
                    "BluetoothStatePowerOn"
                )
            }
        })
    }

    private fun getRequiredPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            !== PackageManager.PERMISSION_GRANTED
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
        //三星手机系统可能会限制息屏下扫描，导致息屏后无法获取到广播数据
        //The Samsung mobile phone system may restrict scanning under the screen, resulting in the inability to obtain broadcast data after the screen
        mMtCentralManager!!.startScan()
    }

}
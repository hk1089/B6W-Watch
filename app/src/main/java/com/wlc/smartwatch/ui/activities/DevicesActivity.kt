package com.wlc.smartwatch.ui.activities

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import androidx.activity.result.contract.ActivityResultContracts
import com.wlc.smartwatch.databinding.ActivityDevicesBinding
import com.wlc.smartwatch.models.DevicesData
import com.wlc.smartwatch.ui.adapters.DevicesAdapter
import com.wlc.smartwatch.util.permissions
import com.wlc.wearable.ui.base.BaseActivity
import com.yc.pedometer.sdk.BLEServiceOperate
import com.yc.pedometer.sdk.DeviceScanInterfacer
import com.yc.pedometer.utils.LogUtils
import timber.log.Timber

class DevicesActivity : BaseActivity() {
    private lateinit var binding: ActivityDevicesBinding
    private lateinit var mBLEServiceOperate: BLEServiceOperate
    private lateinit var adapter: DevicesAdapter
    private lateinit var mHandler: Handler
    private var mScanning = false
    private val SCAN_PERIOD: Long = 10000

    private val deviceScanInterfacer = object : DeviceScanInterfacer {
        override fun LeScanCallback(device: BluetoothDevice?, rssi: Int, scanRecord: ByteArray?) {
            runOnUiThread {
                if (device != null){
                    if (TextUtils.isEmpty(device.name)) {
                        return@runOnUiThread
                    }
                    val devicesData = DevicesData(device.name, device.address, rssi)
                    adapter.addData(devicesData)
                    adapter.notifyDataSetChanged()
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTask()
    }

    private fun initTask() {
        mHandler = Handler(Looper.getMainLooper())
        mBLEServiceOperate = BLEServiceOperate
            .getInstance(applicationContext)
        permissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        { isGrant, grantList, deniedList ->
            if (isGrant && deniedList.isEmpty()) {
                mBLEServiceOperate.setDeviceScanListener(deviceScanInterfacer)
                scanLeDevice(true)
            }
        }
        adapter = DevicesAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun scanLeDevice(enable: Boolean) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(Runnable {
                mScanning = false
                mBLEServiceOperate.stopLeScan()
            }, SCAN_PERIOD)
            mScanning = true
            mBLEServiceOperate.startLeScan()
            Timber.i("startLeScan")
        } else {
            mScanning = false
            mBLEServiceOperate.stopLeScan()
        }
    }

    override fun onResume() {
        super.onResume()

        if (!mBLEServiceOperate.isBleEnabled) {
            val enableBtIntent = Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE
            )
            activityResultLauncher.launch(enableBtIntent)
        }
        scanLeDevice(true)
    }
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // User chose not to enable Bluetooth.
                if (result.resultCode == RESULT_CANCELED) {
                    finish()
                }

        }

    override fun onPause() {
        super.onPause()
        scanLeDevice(false)
        adapter.clear()
    }
}
package com.benji.weatherswe

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.benji.device.location.GPSBroadcastReceiver
import com.benji.device.location.Gps
import com.benji.device.location.GpsStatus
import com.benji.device.location.IActivity
import com.benji.device.permission.Permissions
import com.benji.device.permission.Permissions.Companion.PERMISSION_CODE_LOCATION
import com.benji.weatherswe.utils.extensions.showActivityText

class MainActivity : AppCompatActivity(), IActivity {
    private lateinit var permissions: Permissions
    private lateinit var gpsBroadcastReceiver: BroadcastReceiver
    private lateinit var gps: Gps

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE_LOCATION -> {
                when (this.permissions.approvedGrantResults(grantResults)) {
                    true -> gps.checkGpsStatus()
                    false -> showActivityText(getString(R.string.activity_permission_denied))
                }
            }
        }
    }

    private val gpsStatusObserver = Observer<GpsStatus> { status ->
        when (status) {
            is GpsStatus.Enabled -> gps.getLastLocation()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Gps.LOCATION_SETTING_REQUEST -> handleResultCode(resultCode)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleResultCode(resultCode: Int) {
        when (resultCode) {
            Activity.RESULT_OK -> gps.getLastLocation()
            Activity.RESULT_CANCELED -> showActivityText(getString(R.string.activity_location_denied))
        }
    }

    override fun onResume() {
        super.onResume()
        gps.getLastLocation()
    }

    override fun onStart() {
        super.onStart()
        permissions = Permissions(this).also { it.request() }
        gpsBroadcastReceiver =
            GPSBroadcastReceiver().also { it.gpsStatus.observe(this, gpsStatusObserver) }
        gps = Gps(this)
        registerReceiver(gpsBroadcastReceiver, GPSBroadcastReceiver.getIntentFilter())
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(gpsBroadcastReceiver)
    }

    override fun getActivity(): AppCompatActivity = this

    override fun getContext(): Context = applicationContext

}



package com.benji.weatherswe

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.weatherswe.locationweather.LocationHandler
import com.benji.weatherswe.locationweather.PermissionManager
import com.benji.weatherswe.locationweather.PermissionManager.Companion.PERMISSION_CODE_LOCATION
import com.benji.weatherswe.utils.*

class MainActivity : AppCompatActivity() {
    private lateinit var permissionManager: PermissionManager
    private lateinit var gpsBroadcastReceiver: BroadcastReceiver
    private lateinit var locationHandler: LocationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionManager = PermissionManager(this).also { it.requestPermission() }
        gpsBroadcastReceiver = GPSBroadcastReceiver(this)
        locationHandler = LocationHandler(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE_LOCATION -> {
                when (permissionManager.approvedGrantResults(grantResults)) {
                    true -> locationHandler.checkGpsStatus()
                    false -> showText(getString(R.string.activity_permission_denied))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(gpsBroadcastReceiver, GPSBroadcastReceiver.getIntentFilter())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LocationHandler.LOCATION_SETTING_REQUEST -> handleResultCode(resultCode)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleResultCode(resultCode: Int) {
        when (resultCode) {
            Activity.RESULT_OK -> locationHandler.getDeviceLocation()
            Activity.RESULT_CANCELED -> showText(getString(R.string.activity_location_denied))
        }
    }

    fun onReceiveGpsStatus(action: GpsStatus) {
        when (action) {
            is GpsStatus.Enabled -> locationHandler.getDeviceLocation()
        }
    }

    fun onLastLocationReceived(lastLocation: Location) {
        sharedViewModel().lastLocationReceived.value = lastLocation
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(gpsBroadcastReceiver)
    }

}



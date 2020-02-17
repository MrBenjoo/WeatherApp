package com.benji.weatherswe

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.benji.weatherswe.locationweather.PermissionManager
import com.benji.weatherswe.locationweather.PermissionManager.Companion.PERMISSION_CODE_LOCATION
import com.benji.weatherswe.utils.GPSBroadcastReceiver
import com.benji.weatherswe.utils.GpsStatus
import com.benji.weatherswe.utils.LocationSettings
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var permissionManager: PermissionManager
    private lateinit var gpsBroadcastReceiver: BroadcastReceiver
    private var locationSettings: LocationSettings?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionManager = PermissionManager(this).also { it.requestPermission() }
        gpsBroadcastReceiver = GPSBroadcastReceiver(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE_LOCATION -> {
                when (permissionManager.checkGrantResults(grantResults)) {
                    true -> locationSettings = LocationSettings(this)
                    false -> showText("GPS data kommer inte användas.")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(gpsBroadcastReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(gpsBroadcastReceiver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LocationSettings.LOCATION_SETTING_REQUEST -> handleResultCode(resultCode)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun handleResultCode(resultCode: Int) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                locationSettings?.getSettingsResponse()
            }
            Activity.RESULT_CANCELED -> {
                showText("Kan inte lokalisera enheten. Sätt på GPS.")
            }
        }
    }

    fun onLocationSettingsSatisfied(response : LocationSettingsResponse?) {
        sharedViewModel().locationSettingsResponse.value = response
    }

    fun onReceiveGpsStatus(action : GpsStatus) {
        when(action) {
            is GpsStatus.Enabled -> sharedViewModel().gpsStatus.value = GpsStatus.Enabled
            is GpsStatus.Disabled -> sharedViewModel().gpsStatus.value = GpsStatus.Disabled
        }
    }

}

fun AppCompatActivity.showText(text: String) {
    Snackbar.make(
        this.findViewById(android.R.id.content),
        text,
        Snackbar.LENGTH_LONG
    )
        .show()
}

fun MainActivity.sharedViewModel(): SharedViewModel {
    return ViewModelProviders.of(this).get(SharedViewModel::class.java)
}

package com.benji.weatherswe.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.LocationManager
import com.benji.weatherswe.MainActivity


class GPSBroadcastReceiver(private val activity: MainActivity) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            when (isGpsIntent(intent)) {
                true -> {
                    when (isProviderEnabled(context)) {
                        true -> activity.onReceiveGpsStatus(GpsStatus.Enabled)
                        false -> activity.onReceiveGpsStatus(GpsStatus.Disabled)
                    }
                }
            }
        } catch (ex: Exception) {
        }
    }

    private fun isGpsIntent(intent: Intent?): Boolean {
        return intent?.action!!.matches(Regex(LocationManager.PROVIDERS_CHANGED_ACTION))
    }

    private fun isProviderEnabled(context: Context?): Boolean {
        val locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}

sealed class GpsStatus {
    object Enabled : GpsStatus()
    object Disabled : GpsStatus()
}
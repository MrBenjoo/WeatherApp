package com.benji.weatherswe.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import com.benji.weatherswe.MainActivity


class GPSBroadcastReceiver(private val activity: MainActivity) : BroadcastReceiver() {
    private var enabledSent = true
    private var disabledSent = true

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            when (isGpsIntent(intent)) {
                true -> {
                    when (isProviderEnabled(context)) {
                        true -> {
                            if(enabledSent) {
                                activity.onReceiveGpsStatus(GpsStatus.Enabled)
                                enabledSent = false
                                disabledSent = true
                            }
                        }
                        false -> {
                            if(disabledSent) {
                                activity.onReceiveGpsStatus(GpsStatus.Disabled)
                                disabledSent = false
                                enabledSent = true
                            }
                        }
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

    companion object {
       fun getIntentFilter() : IntentFilter {
           return IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
       }
    }
}

sealed class GpsStatus {
    object Enabled : GpsStatus()
    object Disabled : GpsStatus()
}
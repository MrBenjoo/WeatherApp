package com.benji.device.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benji.device.location.GpsUtils.isGpsIntent
import com.benji.device.location.GpsUtils.isGpsProviderEnabled


class GPSBroadcastReceiver() : BroadcastReceiver() {
    private var onFlag = true
    private var offFlag = true

    private val _gpsStatus = MutableLiveData<GpsStatus>()
    val gpsStatus: LiveData<GpsStatus> = _gpsStatus

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            when (isGpsIntent(intent)) {
                true -> {
                    when (isGpsProviderEnabled(context)) {
                        true -> {
                            if(onFlag) {
                                _gpsStatus.postValue(GpsStatus.Enabled)
                                onFlag = false
                                offFlag = true
                            }
                        }
                        false -> {
                            if(offFlag) {
                                offFlag = false
                                onFlag = true
                            }
                        }
                    }
                }
            }
        } catch (ex: Exception) {
        }
    }

    companion object {
       fun getIntentFilter() : IntentFilter {
           return IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
       }
    }

}


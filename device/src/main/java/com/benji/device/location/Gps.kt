package com.benji.device.location

import android.content.IntentSender
import android.os.Looper
import com.benji.device.location.GpsServiceLocator.getFusedLocationProviderClient
import com.benji.device.location.GpsServiceLocator.getLocationRequest
import com.benji.device.location.GpsServiceLocator.getTaskLocationSettingsResponse
import com.benji.device.location.GpsUtils.isProviderEnabled
import com.benji.device.location.GpsUtils.permissionGranted
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.location.IGps
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationSettingsStatusCodes

class Gps(private val callback: IActivity) : IGps, LocationCallback() {

    companion object {
        const val LOCATION_SETTING_REQUEST = 999
    }

    private var mFusedLocationClient = getFusedLocationProviderClient(callback.getContext())
    private val locationRequest = getLocationRequest()


    fun getLastLocation() {
        val context = callback.getContext()
        val readLocationGranted = permissionGranted(context) && isProviderEnabled(context)

        when (readLocationGranted) {
            true -> mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                when (location != null) {
                    true -> LocationEvent.notify(location.toCustomLocation())
                    false -> requestNewLocationData()
                }
            }
        }

    }

    private fun requestNewLocationData() {
        mFusedLocationClient.requestLocationUpdates(locationRequest, this, Looper.myLooper())
    }

    override fun onLocationResult(locationResult: LocationResult) {
        LocationEvent.notify(locationResult.lastLocation.toCustomLocation())
    }

    fun checkGpsStatus() {
        getTaskLocationSettingsResponse(
            callback.getContext(),
            locationRequest
        ).addOnCompleteListener { task ->
            try {
                task.getResult(ApiException::class.java)
            } catch (exception: ApiException) {
                handleException(exception)
            }
        }
    }

    private fun handleException(exception: ApiException) {
        when (exception.statusCode) {
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                onExceptionResolutionRequired(exception)
            }
            // Location settings are not satisfied. However, we have no way to fix the
            // settings so we won't show the dialog.
            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Unit
        }
    }

    /**
     * Location settings are not satisfied. But could be fixed by showing the
     * user a dialog.
     */
    private fun onExceptionResolutionRequired(exception: ApiException) {
        try {
            val resolvable = exception as ResolvableApiException
            resolvable.startResolutionForResult(
                callback.getActivity(),
                LOCATION_SETTING_REQUEST
            )

        } catch (e: IntentSender.SendIntentException) {
            // Ignore the error.

        } catch (e: ClassCastException) {
            // Ignore, should be an impossible error.
        }
    }


}

fun android.location.Location.toCustomLocation(): Location {
    return Location(this.latitude, this.longitude)
}
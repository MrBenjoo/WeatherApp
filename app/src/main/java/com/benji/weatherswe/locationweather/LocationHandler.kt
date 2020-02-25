package com.benji.weatherswe.locationweather

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.location.ILocationHandler
import com.benji.weatherswe.MainActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task


class LocationHandler(
    private val activity: MainActivity
) : ILocationHandler, LocationCallback() {

    companion object {
        const val LOCATION_SETTING_REQUEST = 999
    }

    private var mFusedLocationClient =
        LocationServices.getFusedLocationProviderClient(activity.applicationContext)
    private val locationRequest: LocationRequest

    init {
        locationRequest = getLocationRequest()
    }

    fun getDeviceLocation() {
        when (permissionGranted() && isLocationEnabled()) {
            true -> {
                mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    when (location != null) {
                        true -> activity.onLastLocationReceived(location.toCustomLocation())
                        false -> requestNewLocationData()
                    }
                }
            }
        }
    }


    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(0)
            .setFastestInterval(0)
            .setNumUpdates(1)

        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity.applicationContext)

        mFusedLocationClient.requestLocationUpdates(mLocationRequest, this, Looper.myLooper())
    }

    override fun onLocationResult(locationResult: LocationResult) {
        activity.onLastLocationReceived(locationResult.lastLocation.toCustomLocation())
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            activity.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun permissionGranted(): Boolean =
        (ContextCompat.checkSelfPermission(
            activity.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)


    fun checkGpsStatus() {
        getTaskLocationSettingsResponse().addOnCompleteListener { task ->
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
            resolvable.startResolutionForResult(activity, LocationHandler.LOCATION_SETTING_REQUEST)

        } catch (e: IntentSender.SendIntentException) {
            // Ignore the error.

        } catch (e: ClassCastException) {
            // Ignore, should be an impossible error.
        }
    }

    private fun getLocationRequest(): LocationRequest =
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(30 * 1000)
            .setFastestInterval(5 * 1000)

    private fun getLocationSettingsRequestBuilder(): LocationSettingsRequest.Builder =
        LocationSettingsRequest
            .Builder()
            .addLocationRequest(locationRequest)
            /**
             * Whether or not location is required by the calling app in order to continue.
             * Set this to true if location is required to continue and false if having location
             * provides better results, but is not required.
             * This changes the wording/appearance of the dialog accordingly.
             */
            .setAlwaysShow(false)

    private fun getTaskLocationSettingsResponse(): Task<LocationSettingsResponse> =
        LocationServices
            .getSettingsClient(activity.applicationContext)
            .checkLocationSettings(getLocationSettingsRequestBuilder().build())

}

fun android.location.Location.toCustomLocation(): Location {
    return Location(this.latitude, this.longitude)
}
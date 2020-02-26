package com.benji.device.location

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

object GpsServiceLocator {

    fun getFusedLocationProviderClient(context : Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    fun getLocationRequest(): LocationRequest =
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(0)
            .setFastestInterval(0)
            .setNumUpdates(1)

    fun getTaskLocationSettingsResponse(context : Context, locationRequest : LocationRequest): Task<LocationSettingsResponse> =
        LocationServices
            .getSettingsClient(context)
            .checkLocationSettings(getLocationSettingsRequestBuilder(locationRequest).build())


    private fun getLocationSettingsRequestBuilder(locationRequest : LocationRequest): LocationSettingsRequest.Builder =
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

    fun getLocationManager(context: Context?) : LocationManager {
        return context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

}
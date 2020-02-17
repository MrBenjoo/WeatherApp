package com.benji.weatherswe.utils

import android.content.IntentSender
import android.os.Bundle
import com.benji.weatherswe.MainActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationSettings(private val activity: MainActivity) : ConnectionCallbacks,
    OnConnectionFailedListener {

    companion object {
        const val LOCATION_SETTING_REQUEST = 999
    }

    private val locationRequest: LocationRequest
    private val googleApiClient: GoogleApiClient

    init {
        locationRequest = getLocationRequest()
        googleApiClient = getGoogleApiClient()
        googleApiClient.connect()
    }

    // GoogleApiClient.ConnectionCallbacks
    override fun onConnected(bundle: Bundle?) {
        getSettingsResponse()
    }

    fun getSettingsResponse() {
        if (googleApiClient.isConnected) {
            getTaskLocationSettingsResponse().addOnCompleteListener { task ->
                try {
                    val response = task.getResult(ApiException::class.java)
                    activity.onLocationSettingsSatisfied(response)
                } catch (exception: ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            onExceptionResolutionRequired(exception)
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                        }
                    }
                }
            }
        }
    }

    /**
     * Location settings are not satisfied. But could be fixed by showing the
     * user a dialog.
     */
    private fun onExceptionResolutionRequired(exception: ApiException) {

        try {
            // Cast to a resolvable exception.
            val resolvable = exception as ResolvableApiException

            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            resolvable.startResolutionForResult(activity, LOCATION_SETTING_REQUEST)

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
            .setAlwaysShow(true)

    private fun getTaskLocationSettingsResponse(): Task<LocationSettingsResponse> =
        LocationServices
            .getSettingsClient(activity)
            .checkLocationSettings(getLocationSettingsRequestBuilder().build())

    private fun getGoogleApiClient(): GoogleApiClient =
        GoogleApiClient.Builder(activity.applicationContext)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this).build()


    // GoogleApiClient.ConnectionCallbacks
    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // GoogleApiClient.OnConnectionFailedListener
    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
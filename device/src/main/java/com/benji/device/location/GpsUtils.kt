package com.benji.device.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat

object GpsUtils {

    fun permissionGranted(context: Context): Boolean =
        (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)

    fun isProviderEnabled(context: Context?): Boolean =
        isGpsProviderEnabled(context) || isNetworkProviderEnabled(context)

    fun isGpsProviderEnabled(context: Context?): Boolean =
        GpsServiceLocator.getLocationManager(context).isProviderEnabled(LocationManager.GPS_PROVIDER)

    fun isNetworkProviderEnabled(context: Context?): Boolean =
        GpsServiceLocator.getLocationManager(context).isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    fun isGpsIntent(intent: Intent?): Boolean =
        intent?.action!!.matches(Regex(LocationManager.PROVIDERS_CHANGED_ACTION))


}
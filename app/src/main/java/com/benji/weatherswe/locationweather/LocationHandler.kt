package com.benji.weatherswe.locationweather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.geocoding.sixDecimals
import com.benji.domain.location.ILocationHandler
import com.benji.weatherswe.utils.mainActivity
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class LocationHandler(private val attachedFragment: Fragment) :
    ILocationHandler {

    private val mFusedLocationClient =
        LocationServices.getFusedLocationProviderClient(attachedFragment.activity!!)

    override suspend fun getDeviceLocation(): Location? = when (isLocationEnabled() && permissionGranted()) {
        true -> getLastLocation()
        else -> null
    }

    private suspend fun getLastLocation(): Location? {
        val location = mFusedLocationClient.lastLocation.await()
        return when (location != null) {
            true -> return Location(location.latitude, location.longitude).sixDecimals()
            false -> getDeviceLocation()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            attachedFragment.context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun permissionGranted(): Boolean =
        (ContextCompat.checkSelfPermission(
            attachedFragment.mainActivity().applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)

}
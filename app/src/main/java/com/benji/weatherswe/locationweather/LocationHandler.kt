package com.benji.weatherswe.locationweather

import android.content.Context
import android.location.LocationManager
import androidx.fragment.app.Fragment
import com.benji.domain.location.ILocationHandler
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.geocoding.sixDecimals
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class LocationHandler(private val attachedFragment: Fragment) :
    ILocationHandler {

    private val mFusedLocationClient =
        LocationServices.getFusedLocationProviderClient(attachedFragment.activity!!)

    override suspend fun getDeviceLocation(): Location? = when (isLocationEnabled()) {
        true -> getLastLocation()
        else -> null
    }

    private suspend fun getLastLocation(): Location? {
        val location = mFusedLocationClient.lastLocation.await()
        return Location(location.latitude, location.longitude).sixDecimals()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            attachedFragment.context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


}
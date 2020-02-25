package com.benji.weatherswe.locationweather

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.benji.domain.location.IPermissionManager
import com.benji.weatherswe.MainActivity

class PermissionManager(private val activity: MainActivity): IPermissionManager {

    override fun approvedGrantResults(grantResults: IntArray): Boolean =
        grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED


    companion object {
        const val PERMISSION_CODE_LOCATION = 99
    }

    override fun requestPermission() {
        when (permissionGranted().not()) {
            true -> {
                when (showRequestPermissionRationale()) {
                    true -> {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    }
                    false -> {
                        // No explanation needed, we can request the permission.
                        requestPermissions()
                    }
                }
            }
            false -> Log.d("PermissionManager", "Permission has already been granted")
        }
    }

    override fun permissionGranted(): Boolean =
        (ContextCompat.checkSelfPermission(
            activity.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)

    private fun showRequestPermissionRationale(): Boolean =
        ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_CODE_LOCATION
        )
    }


}
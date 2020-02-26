package com.benji.device.permission

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.benji.device.location.IActivity
import com.benji.domain.location.IPermissions

class Permissions(private val callback: IActivity): IPermissions {

    companion object {
        const val PERMISSION_CODE_LOCATION = 99
    }

    override fun request() {
        when (granted()) {
            false -> when (showRequestPermissionRationale()) {
                true -> {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                }
                false -> requestPermissions()
            }
            true -> Log.d("Permissions", "Permission has already been granted")
        }
    }

    override fun granted(): Boolean =
        (ContextCompat.checkSelfPermission(
            callback.getContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)

    override fun approvedGrantResults(grantResults: IntArray): Boolean =
        grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

    private fun showRequestPermissionRationale(): Boolean =
        ActivityCompat.shouldShowRequestPermissionRationale(
            callback.getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            callback.getActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_CODE_LOCATION
        )
    }


}
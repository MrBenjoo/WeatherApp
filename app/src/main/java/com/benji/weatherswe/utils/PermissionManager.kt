package com.benji.weatherswe.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionManager(private val attachedFragment: Fragment) {
    private val PERMISSION_CODE_LOCATION = 99

    fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                attachedFragment.context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE_LOCATION -> {
                for (index in grantResults.indices) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        if (attachedFragment.shouldShowRequestPermissionRationale(permissions[index])) {
                            attachedFragment.showText("GPS data kommer inte användas.")
                        }
                        return
                    }
                }
            }
        }
    }

    fun requestPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            attachedFragment.requestPermissions(permission, PERMISSION_CODE_LOCATION)
        }
    }


}
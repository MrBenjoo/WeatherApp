package com.benji.weatherswe.locationweather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.benji.domain.location.IPermissionManager
import com.benji.weatherswe.utils.showText

class PermissionManager(private val attachedFragment: Fragment) :
    IPermissionManager {
    private val PERMISSION_CODE_LOCATION = 99

    init {
        requestPermissions()
    }

    override fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                attachedFragment.context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
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

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            attachedFragment.requestPermissions(permission, PERMISSION_CODE_LOCATION)
        }
    }


}
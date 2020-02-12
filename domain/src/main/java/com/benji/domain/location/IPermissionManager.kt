package com.benji.domain.location

interface IPermissionManager {

    fun checkPermissions(): Boolean

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )

}
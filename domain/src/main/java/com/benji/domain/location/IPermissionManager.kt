package com.benji.domain.location

interface IPermissionManager {

    fun requestPermission()

    fun checkGrantResults(grantResults: IntArray): Boolean

    fun permissionGranted() : Boolean

}
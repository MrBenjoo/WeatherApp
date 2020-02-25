package com.benji.domain.location

interface IPermissionManager {

    fun requestPermission()

    fun approvedGrantResults(grantResults: IntArray): Boolean

    fun permissionGranted() : Boolean

}
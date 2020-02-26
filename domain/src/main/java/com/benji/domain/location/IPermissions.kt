package com.benji.domain.location

interface IPermissions {

    fun request()

    fun approvedGrantResults(grantResults: IntArray): Boolean

    fun granted() : Boolean

}
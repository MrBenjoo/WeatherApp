package com.benji.domain.location

import com.benji.domain.domainmodel.geocoding.Location

interface ILocationHandler {

    suspend fun getDeviceLocation(): Location?


}
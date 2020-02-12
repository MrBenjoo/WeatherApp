package com.benji.domain.location

import com.benji.domain.domainmodel.geocoding.Location

interface IReversedGeocoding {

    fun getFromLocation(location : Location) : String

}
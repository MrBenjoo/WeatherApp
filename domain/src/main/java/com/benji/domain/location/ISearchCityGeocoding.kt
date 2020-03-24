package com.benji.domain.location

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location

interface ISearchCityGeocoding {

    fun getFromLocation(location : Location) : ResultWrapper<Exception, String>

}
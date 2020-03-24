package com.benji.weatherswe.searchcity

import com.benji.domain.domainmodel.geocoding.Location

sealed class SearchCityEvent {

    data class OnQueryInput(val query : String?) : SearchCityEvent()
    data class OnSuggestionClick(val index : Int) : SearchCityEvent()
    data class OnLocationReceived(val location : Location) : SearchCityEvent()

}


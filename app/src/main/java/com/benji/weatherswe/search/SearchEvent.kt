package com.benji.weatherswe.search

import com.benji.domain.domainmodel.geocoding.Location

sealed class SearchEvent {

    data class OnQueryInput(val query : String?) : SearchEvent()
    data class OnSuggestionClick(val index : Int) : SearchEvent()
    data class OnLocationReceived(val location : Location) : SearchEvent()

}


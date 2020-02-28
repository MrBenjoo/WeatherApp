package com.benji.weatherswe.search

sealed class SearchEvent {

    data class OnQueryInput(val query : String?) : SearchEvent()
    data class OnSuggestionClick(val index : Int) : SearchEvent()

}


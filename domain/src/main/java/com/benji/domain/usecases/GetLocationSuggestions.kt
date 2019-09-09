package com.benji.domain.usecases

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Suggestions
import com.benji.domain.repository.IGeocodingRepository

class GetLocationSuggestions(private val geocodingRepository: IGeocodingRepository) {

    suspend fun getLocationSuggestions(textSearch: String): ResultWrapper<Exception, Suggestions> {
        return geocodingRepository.getSuggestions(textSearch)
    }


}
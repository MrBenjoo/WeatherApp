package com.benji.data.datasource.remote

import com.benji.domain.domainmodel.geocoding.Candidates
import com.benji.domain.domainmodel.geocoding.Suggestions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeocodingRemoteDataSource(private val geocodingAPI: GeocodingAPI) {

    suspend fun getSuggestions(textSearch: String): Suggestions = withContext(Dispatchers.IO) {
        geocodingAPI.getSuggestions(textSearch)
    }

    suspend fun getCandidate(city: String, magicKey: String): Candidates =
        withContext(Dispatchers.IO) {
            geocodingAPI.getCandidate(city, magicKey)
        }

}

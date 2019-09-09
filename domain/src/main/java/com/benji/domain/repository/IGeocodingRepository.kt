package com.benji.domain.repository

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidates
import com.benji.domain.domainmodel.geocoding.Suggestions

interface IGeocodingRepository {

    suspend fun getSuggestions(textSearch : String) : ResultWrapper<Exception, Suggestions>

    suspend fun getCandidateLocation(city : String, magicKey : String) : ResultWrapper<Exception, Candidates>
}
package com.benji.domain.usecases

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidates
import com.benji.domain.repository.IGeocodingRepository


class GetLocationCandidate(private val geocodingRepository: IGeocodingRepository) {
    suspend fun getCandidateLocation(city: String, magicKey: String)
            : ResultWrapper<Exception, Candidates>
    {
        return geocodingRepository.getCandidateLocation(city, magicKey)
    }



}
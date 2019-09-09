package com.benji.data.repository

import com.benji.data.datasource.remote.GeocodingRemoteDataSource
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidates
import com.benji.domain.domainmodel.geocoding.Suggestions
import com.benji.domain.repository.IGeocodingRepository
import retrofit2.HttpException
import java.lang.Exception

class GeocodingRepository(private val dataSource: GeocodingRemoteDataSource) :
    IGeocodingRepository {

    override suspend fun getSuggestions(textSearch: String): ResultWrapper<Exception, Suggestions> {
        return try {
            ResultWrapper.build { dataSource.getSuggestions(textSearch) }
        } catch (exception: HttpException) {
            ResultWrapper.build { throw exception }
        }
    }

    override suspend fun getCandidateLocation(
        city: String, magicKey: String
    ): ResultWrapper<Exception, Candidates> {
        return try {
            ResultWrapper.build { dataSource.getCandidate(city, magicKey) }
        } catch (exception: HttpException) {
            ResultWrapper.build { throw exception }
        }
    }
}
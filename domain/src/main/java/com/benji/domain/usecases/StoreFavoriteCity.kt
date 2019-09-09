package com.benji.domain.usecases

import com.benji.domain.ResultWrapper
import com.benji.domain.repository.ICityRepository

class StoreFavoriteCity(private val cityRepository: ICityRepository) {

    suspend fun storeFavoriteCity(city: String): ResultWrapper<Exception, Boolean> =
        cityRepository.storeFavoriteCity(city)

}
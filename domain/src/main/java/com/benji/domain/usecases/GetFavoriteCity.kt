package com.benji.domain.usecases

import com.benji.domain.ResultWrapper
import com.benji.domain.repository.ICityRepository

class GetFavoriteCity(private val cityRepository: ICityRepository) {

    suspend fun getFavoriteCity(): ResultWrapper<Exception, String> =
        cityRepository.getFavoriteCity()

}
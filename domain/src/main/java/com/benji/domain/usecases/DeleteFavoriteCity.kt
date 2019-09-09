package com.benji.domain.usecases

import com.benji.domain.ResultWrapper
import com.benji.domain.repository.ICityRepository

class DeleteFavoriteCity(private val cityRepository: ICityRepository) {

    suspend fun deleteFavoriteCity(): ResultWrapper<Exception, Boolean> =
        cityRepository.deleteFavoriteCity()

}
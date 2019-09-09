package com.benji.domain.repository

import com.benji.domain.ResultWrapper

interface ICityRepository {

    fun storeFavoriteCity(city : String) : ResultWrapper<Exception, Boolean>

    fun getFavoriteCity() : ResultWrapper<Exception, String>

    fun deleteFavoriteCity() : ResultWrapper<Exception, Boolean>

}
package com.benji.domain.repository

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Weather


interface IWeatherRepository {

    suspend fun getWeatherForecast(latLng : Location) : ResultWrapper<Exception, Weather>


    suspend fun storeDayForecast(dayForecast: DayForecast) : ResultWrapper<Exception, Unit>


    suspend fun deleteAll() : ResultWrapper<Exception, Unit>


}
package com.benji.domain.repository

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.Weather


interface IWeatherRepository {

    suspend fun getWeatherForecast(latLng : Location) : ResultWrapper<Exception, Weather>

    suspend fun storeWeather(weather: Weather) : ResultWrapper<Exception, Boolean>

    suspend fun deleteWeather(weather: Weather) : ResultWrapper<Exception, Boolean>

}
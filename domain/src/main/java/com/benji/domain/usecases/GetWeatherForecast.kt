package com.benji.domain.usecases

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository

class GetWeatherForecast(private val weatherRepository: IWeatherRepository) {

    suspend fun getWeatherForecast(longitude: String, latitude: String): ResultWrapper<Exception, Weather> =
        weatherRepository.getWeatherForecast(longitude, latitude)

}
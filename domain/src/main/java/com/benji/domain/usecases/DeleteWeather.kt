package com.benji.domain.usecases

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository

class DeleteWeather(private val weatherRepository: IWeatherRepository) {

    suspend fun deleteWeather(weather: Weather): ResultWrapper<Exception, Boolean> =
        weatherRepository.deleteWeather(weather)
}
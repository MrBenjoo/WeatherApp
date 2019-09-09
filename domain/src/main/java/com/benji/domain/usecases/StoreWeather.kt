package com.benji.domain.usecases

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository

class StoreWeather(private val weatherRepository: IWeatherRepository) {

    suspend fun storeWeather(weather: Weather): ResultWrapper<Exception, Boolean> =
        weatherRepository.storeWeather(weather)

}
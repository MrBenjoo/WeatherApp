package com.benji.data.datasource.remote

import com.benji.domain.domainmodel.weather.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WeatherRemoteDataSource(private val weatherAPI: WeatherAPI) {

    suspend fun getWeatherForecast(
        longitude: String,
        latitude: String
    ): Weather = withContext(Dispatchers.IO) {
            weatherAPI.getWeatherForecast(longitude, latitude)
        }


}


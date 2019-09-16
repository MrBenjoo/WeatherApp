package com.benji.data.datasource.remote

import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WeatherRemoteDataSource(private val weatherAPI: WeatherAPI) {

    suspend fun getWeatherForecast(
        latLng : Location
    ): Weather = withContext(Dispatchers.IO) {
            weatherAPI.getWeatherForecast(latLng.x, latLng.y)
        }


}


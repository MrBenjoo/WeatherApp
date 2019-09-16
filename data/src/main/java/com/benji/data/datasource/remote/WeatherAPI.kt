package com.benji.data.datasource.remote

import com.benji.domain.domainmodel.weather.Weather
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherAPI {

    @GET("api/category/pmp3g/version/2/geotype/point/lon/{longitude}/lat/{latitude}/data.json")
    suspend fun getWeatherForecast(
        @Path("longitude") longitude: Double,
        @Path("latitude") latitude: Double
    )
            : Weather

}
package com.benji.data.repository

import com.benji.data.datasource.remote.WeatherRemoteDataSource
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository
import retrofit2.HttpException
import java.lang.Exception

class WeatherRepository(private val weatherRemoteDataSource: WeatherRemoteDataSource) :
    IWeatherRepository {

    override suspend fun getWeatherForecast(
        longitude: String,
        latitude: String
    ): ResultWrapper<Exception, Weather> {
        return try {
            ResultWrapper.build { weatherRemoteDataSource.getWeatherForecast(longitude, latitude) }
        } catch (exception: HttpException) {
            ResultWrapper.build { throw exception }
        }
    }

    override suspend fun storeWeather(weather: Weather): ResultWrapper<Exception, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteWeather(weather: Weather): ResultWrapper<Exception, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
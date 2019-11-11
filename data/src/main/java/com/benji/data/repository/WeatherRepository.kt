package com.benji.data.repository

import com.benji.data.datasource.local.WeatherDao
import com.benji.data.datasource.remote.WeatherRemoteDataSource
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository
import retrofit2.HttpException

class WeatherRepository(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherDao: WeatherDao
) :
    IWeatherRepository {


    override suspend fun getWeatherForecast(
        latLng: Location
    ): ResultWrapper<Exception, Weather> {
        return try {
            ResultWrapper.build { weatherRemoteDataSource.getWeatherForecast(latLng) }
        } catch (exception: HttpException) {
            ResultWrapper.build { throw exception }
        }
    }

    override suspend fun storeDayForecast(dayForecast: DayForecast): ResultWrapper<Exception, Unit> {
        return ResultWrapper.build { Unit} //TODO //return ResultWrapper.build { weatherDao.insert(dayForecast) }
    }

    override suspend fun deleteAll(): ResultWrapper<Exception, Unit> {
        return ResultWrapper.build { weatherDao.deleteAll() }
    }


}
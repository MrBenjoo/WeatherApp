package com.benji.data.repository

import com.benji.data.datasource.remote.WeatherRemoteDataSource
import com.benji.data.utils.DateUtils
import com.benji.data.utils.getHourlyForecast
import com.benji.data.utils.monthDayYearDate
import com.benji.data.utils.toDayForecast
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.sixDecimals
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository
import retrofit2.HttpException

class WeatherRepository(private val weatherRemoteDataSource: WeatherRemoteDataSource) :
    IWeatherRepository {

    override suspend fun getListOfDayForecast(candidate: Candidate): ResultWrapper<Exception, List<DayForecast>> {
        return try {
            ResultWrapper.build {
                val location = candidate.location.sixDecimals()
                val weather = weatherRemoteDataSource.getWeatherForecast(location)
                populateListWithDayForecast(weather, candidate)
            }
        } catch (exception: HttpException) {
            ResultWrapper.build { throw exception }
        }
    }

    private fun populateListWithDayForecast(
        weather: Weather,
        candidate: Candidate
    ): List<DayForecast> {
        var currentDate = DateUtils.getCurrentTime()
        var listOfHourlyForecast = mutableListOf<Hourly>()
        val listOfDayForecast = mutableListOf<DayForecast>()

        weather.timeSeries.forEach { timeStamp ->
            val forecastDate = timeStamp.monthDayYearDate()
            if (forecastDate != currentDate) {
                val day = listOfHourlyForecast.toDayForecast(currentDate, candidate)
                listOfDayForecast.add(day)
                listOfHourlyForecast = mutableListOf()
                currentDate = forecastDate
            }
            listOfHourlyForecast.add(timeStamp.getHourlyForecast())
        }
        return listOfDayForecast
    }
}
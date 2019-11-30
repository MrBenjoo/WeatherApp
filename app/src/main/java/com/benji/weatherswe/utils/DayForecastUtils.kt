package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly

object DayForecastUtils {

    fun getDayForecast(
        date: String,
        candidate: Candidate,
        listOfHourlyData: MutableList<Hourly>
    ): DayForecast {

        val day = DateUtils().getDay(date)
        val city = candidate.address
        val highestTemp = TemperatureUtils.getHighestTemperature(listOfHourlyData)
        val weatherSymbol = WeatherSymbolUtils.getWeatherSymbolDay(listOfHourlyData)

        return DayForecast(date, day, city, highestTemp, listOfHourlyData, weatherSymbol)
    }
}
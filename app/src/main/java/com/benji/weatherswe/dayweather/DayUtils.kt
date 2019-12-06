package com.benji.weatherswe.dayweather

import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.weatherswe.utils.DateUtils
import com.benji.weatherswe.utils.SymbolUtils
import com.benji.weatherswe.utils.TempUtils

object DayUtils {

    fun getDayForecast(
        date: String,
        candidate: Candidate,
        listOfHourlyData: MutableList<Hourly>
    ): DayForecast {

        val day = DateUtils().getDay(date)
        val city = candidate.address
        val highestTemp =
            TempUtils.getHighestTemperature(listOfHourlyData)
        val weatherSymbol =
            SymbolUtils.getWeatherSymbolDay(listOfHourlyData)

        return DayForecast(date, day, city, highestTemp, listOfHourlyData, weatherSymbol)
    }
}
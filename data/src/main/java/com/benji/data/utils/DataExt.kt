package com.benji.data.utils

import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.TimeSeries

fun TimeSeries.getHourlyForecast(): Hourly {
    val validTime = DateUtils.getHourlyTime(this.validTime)
    return Hourly(
        validTime,
        this.parameters,
        TempUtils.getCurrentTemperature(this.parameters),
        SymbolUtils.getWeatherSymbolHour(this.parameters)
    )
}

fun TimeSeries.monthDayYearDate(): String = DateUtils.formatDateFromApi(this.validTime)

fun MutableList<Hourly>.toDayForecast(date: String, candidate: Candidate): DayForecast {
    val day = DateUtils.getDay(date)
    val city = candidate.address
    val highestTemp = TempUtils.getHighestTemperature(this)
    val weatherSymbol = SymbolUtils.getWeatherSymbolDay(this)

    return DayForecast(date, day, city, highestTemp, this, weatherSymbol)
}

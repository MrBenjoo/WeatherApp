package com.benji.weatherswe.utils.extensions

import com.benji.data.utils.SymbolUtils
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.weatherswe.daily.CurrentWeatherOverview

fun Hourly.getHourlyOverview() : HourlyOverview {
    return HourlyOverview(
        this.validTime,
        this.temp,
        SymbolUtils.getWeatherSymbolLottie(this.weatherSymbol)
    )
}

fun DayForecast.getCurrentWeatherOverview() : CurrentWeatherOverview =
    CurrentWeatherOverview(
        SymbolUtils.getWeatherSymbolDescription(this.weatherSymbol),
        SymbolUtils.getWeatherSymbolLottie(this.weatherSymbol),
        this.temperature)

package com.benji.domain.domainmodel.weather


data class DayForecast(
    val date: String = "2019-09-30",
    val day: String,
    val city: String,
    val temperature: String,
    val listOfHourlyData: List<Hourly>,
    val weatherSymbol : Int
)

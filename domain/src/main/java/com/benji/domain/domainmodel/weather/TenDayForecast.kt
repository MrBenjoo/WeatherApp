package com.benji.domain.domainmodel.weather


data class TenDayForecast(val day : String,
                          val city : String,
                          val temperature: String,
                          val listOfHourlyData: List<Hourly>)

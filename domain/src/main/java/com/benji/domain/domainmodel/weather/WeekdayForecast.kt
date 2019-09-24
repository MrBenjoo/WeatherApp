package com.benji.domain.domainmodel.weather


data class WeekdayForecast(val referenceTime: String,
                           val day : String,
                           val image : Int,
                           val temperature: String,
                           val city : String,
                           val currentDayUntilMidnight: List<CurrentDayUntilMidnight>)

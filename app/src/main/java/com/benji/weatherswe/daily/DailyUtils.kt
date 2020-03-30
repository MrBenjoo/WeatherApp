package com.benji.weatherswe.daily

import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.weatherswe.utils.extensions.getHourlyOverview

object DailyUtils {
    private const val CURRENT_DAY = 0
    private const val NEXT_DAY = 1

    /**
     * Weather data for a weekday can be accessed in the list with an index.
     * To be able to get the first five hours of weather data, we need to account for how many
     * hours there are left until midnight. If, for example, there is 2 hours left until midnight, we
     * need to get the weather data (accessed in listOfDayForecast[CURRENT_DAY]) for these hours as well as
     * the weather data for the next 3 hours from the next day (accessed in listOfDayForecast[NEXT_DAY]).
     */
    fun getFiveHourForecast(listOfDayForecast: List<DayForecast>): List<HourlyOverview> {
        val hoursLeftUntilMidnight = listOfDayForecast[CURRENT_DAY].listOfHourlyData.size
        return when (hoursLeftUntilMidnight < 5) {
            true -> lessThanFiveHoursUntilMidnight(
                listOfDayForecast,
                hoursLeftUntilMidnight
            )
            false -> {
                val listOfHourlyOverview = mutableListOf<HourlyOverview>()
                for (i in 0..4) {
                    val hourlyWeather = listOfDayForecast[CURRENT_DAY].listOfHourlyData[i]
                    listOfHourlyOverview.add(hourlyWeather.getHourlyOverview())
                }
                listOfHourlyOverview
            }
        }
    }

    private fun lessThanFiveHoursUntilMidnight(
        listOfDayForecast: List<DayForecast>,
        hoursLeftUntilMidnight: Int
    ): List<HourlyOverview> {
        var listOfHourlyOverview = mutableListOf<HourlyOverview>()

        // current day
        listOfHourlyOverview = getListHourlyOverview(
            hoursLeftUntilMidnight - 1,
            listOfDayForecast[CURRENT_DAY],
            listOfHourlyOverview
        )

        // next day
        val hoursToAdd = 5 - hoursLeftUntilMidnight
        listOfHourlyOverview = getListHourlyOverview(
            hoursToAdd - 1,
            listOfDayForecast[NEXT_DAY],
            listOfHourlyOverview
        )
        return listOfHourlyOverview
    }

    private fun getListHourlyOverview(
        hours: Int,
        dayForecast: DayForecast,
        listOfHourlyOverview: MutableList<HourlyOverview>
    ): MutableList<HourlyOverview> {
        for (i in 0..hours) {
            val hourlyWeather = dayForecast.listOfHourlyData[i]
            listOfHourlyOverview.add(hourlyWeather.getHourlyOverview())
        }
        return listOfHourlyOverview
    }
}
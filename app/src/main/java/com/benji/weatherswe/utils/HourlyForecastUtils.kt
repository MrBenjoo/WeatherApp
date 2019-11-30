package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.domain.domainmodel.weather.TimeSeries

object HourlyForecastUtils {


    fun getHourlyForecastData(timeSeries: TimeSeries): Hourly {
        val parameters = timeSeries.parameters
        val validTime = DateUtils().getHourlyTime(timeSeries.validTime)
        val hourly = Hourly(
            validTime,
            parameters,
            TemperatureUtils.getCurrentTemperature(parameters),
            WeatherSymbolUtils.getWeatherSymbolHour(parameters)
        )
        return hourly
    }

    fun getFiveHourForecastData(listOfTenDayForecast: List<DayForecast>): List<HourlyOverview> {
        val hoursLeftUntilMidnight = listOfTenDayForecast[0].listOfHourlyData.size
        var tempList = mutableListOf<HourlyOverview>()

        if (hoursLeftUntilMidnight < 5) {
            tempList =
                getHourlyOverviewData(hoursLeftUntilMidnight - 1, listOfTenDayForecast[0], tempList)

            val hoursToAdd = 5 - hoursLeftUntilMidnight
            tempList = getHourlyOverviewData(hoursToAdd - 1, listOfTenDayForecast[1], tempList)
        } else {
            for (i in 0..4) {
                tempList.add(
                    HourlyOverview(
                        listOfTenDayForecast[0].listOfHourlyData[i].validTime,
                        listOfTenDayForecast[0].listOfHourlyData[i].temp,
                        WeatherSymbolUtils.getWeatherSymbolLottie(listOfTenDayForecast[0].listOfHourlyData[i].weatherSymbol)
                    )
                )
            }
        }

        return tempList
    }

    private fun getHourlyOverviewData(
        hours: Int,
        forecast: DayForecast,
        tempList: MutableList<HourlyOverview>
    ): MutableList<HourlyOverview> {
        with(forecast) {
            for (i in 0..hours) {
                tempList.add(
                    HourlyOverview(
                        listOfHourlyData[i].validTime,
                        listOfHourlyData[i].temp,
                        WeatherSymbolUtils.getWeatherSymbolLottie(listOfHourlyData[i].weatherSymbol)
                    )
                )
            }
        }
        return tempList
    }




}
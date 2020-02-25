package com.benji.weatherswe.hourweather

import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.domain.domainmodel.weather.TimeSeries
import com.benji.weatherswe.utils.forecast.DateUtils
import com.benji.weatherswe.utils.forecast.SymbolUtils
import com.benji.weatherswe.utils.forecast.TempUtils

object HourlyUtils {


    fun getHourlyForecastData(timeSeries: TimeSeries): Hourly {
        val parameters = timeSeries.parameters
        val validTime = DateUtils().getHourlyTime(timeSeries.validTime)
        return Hourly(
            validTime,
            parameters,
            TempUtils.getCurrentTemperature(parameters),
            SymbolUtils.getWeatherSymbolHour(parameters)
        )
    }

    fun getFiveHourForecastData(listOfTenDayForecast: List<DayForecast>): List<HourlyOverview> {
        val hoursLeftUntilMidnight = listOfTenDayForecast[0].listOfHourlyData.size
        var tempList = mutableListOf<HourlyOverview>()

        if (hoursLeftUntilMidnight < 5) {
            tempList =
                getHourlyOverviewData(
                    hoursLeftUntilMidnight - 1,
                    listOfTenDayForecast[0],
                    tempList
                )

            val hoursToAdd = 5 - hoursLeftUntilMidnight
            tempList = getHourlyOverviewData(
                hoursToAdd - 1,
                listOfTenDayForecast[1],
                tempList
            )
        } else {
            for (i in 0..4) {
                tempList.add(
                    HourlyOverview(
                        listOfTenDayForecast[0].listOfHourlyData[i].validTime,
                        listOfTenDayForecast[0].listOfHourlyData[i].temp,
                        SymbolUtils.getWeatherSymbolLottie(
                            listOfTenDayForecast[0].listOfHourlyData[i].weatherSymbol
                        )
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
                        SymbolUtils.getWeatherSymbolLottie(
                            listOfHourlyData[i].weatherSymbol
                        )
                    )
                )
            }
        }
        return tempList
    }


}
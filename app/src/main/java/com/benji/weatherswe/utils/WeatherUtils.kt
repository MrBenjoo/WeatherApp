package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.domain.domainmodel.weather.Parameter
import com.benji.weatherswe.R
import kotlin.math.roundToInt

class WeatherUtils {

    public fun getParameters(parameters: List<Parameter>): List<Parameter> {
        val list = mutableListOf<Parameter>()
        parameters.forEach { parameter ->

            list.add(
                Parameter(
                    parameter.name,
                    parameter.levelType,
                    parameter.level,
                    parameter.unit,
                    parameter.values
                )
            )
        }

        return list
    }

    fun getHighestTemperature(listOfHourlyData: MutableList<Hourly>): String {
        var tempHighest = 0.0

        listOfHourlyData.forEach { hourly ->
            hourly.parameters.forEach { parameter ->
                if (parameter.name == WeatherConstants.PARAMETER_AIR_TEMPERATURE) {
                    if (parameter.values[0].toDouble() > tempHighest) {
                        tempHighest = parameter.values[0].toDouble()
                    }
                }
            }
        }
        return tempHighest.roundToInt().toString() + "\u00B0"
    }

    fun getCurrentTemperature(parameters: List<Parameter>): String {
        var tempCurrent = 0.0
        parameters.forEach { parameter ->
            if (parameter.name == WeatherConstants.PARAMETER_AIR_TEMPERATURE) {
                tempCurrent = parameter.values[0].toDouble()
            }
        }
        return tempCurrent.roundToInt().toString() + "\u00B0"
    }

    fun getWeatherSymbolParameter(parameters: List<Parameter>): Int {
        var weatherSymbol = 1
        parameters.forEach { parameter ->
            if (parameter.name == WeatherConstants.PARAMETER_WEATHER_SYMBOL) {
                weatherSymbol = parameter.values[0].toInt()
            }
        }
        return weatherSymbol
    }

    fun getWeatherSymbol(listOfHourlyData: MutableList<Hourly>): Int {
        var weatherSymbol = 1
        var numberOfValues = 0

        listOfHourlyData.forEach { hourly ->
            numberOfValues++
            hourly.parameters.forEach { parameter ->
                if (parameter.name == WeatherConstants.PARAMETER_WEATHER_SYMBOL) {
                    weatherSymbol += parameter.values[0].toInt()
                }
            }
        }

        return (weatherSymbol / numberOfValues).toDouble().roundToInt()
    }


    fun getWeatherSymbolImage(weatherSymbol: Int): Int = when (weatherSymbol) {
        1 -> R.raw.lottie_weather_sunny
        2 -> R.raw.lottie_weather_partly_cloudy
        3 -> R.raw.lottie_weather_partly_cloudy
        4 -> R.raw.lottie_weather_partly_cloudy
        5 -> R.raw.lottie_weather_cloudy
        6 -> R.raw.lottie_weather_cloudy
        7 -> R.raw.lottie_weather_foggy
        8 -> R.raw.lottie_weather_partly_raining
        9 -> R.raw.lottie_weather_partly_raining
        10 -> R.raw.lottie_weather_partly_raining
        11 -> R.raw.lottie_weather_thunderstorm
        12 -> R.raw.lottie_weather_partly_raining
        13 -> R.raw.lottie_weather_partly_raining
        14 -> R.raw.lottie_weather_partly_raining
        15 -> R.raw.lottie_weather_snow
        16 -> R.raw.lottie_weather_snow
        17 -> R.raw.lottie_weather_snow
        18 -> R.raw.lottie_weather_partly_raining
        19 -> R.raw.lottie_weather_partly_raining
        20 -> R.raw.lottie_weather_partly_raining
        21 -> R.raw.lottie_weather_thunder
        22 -> R.raw.lottie_weather_partly_raining
        23 -> R.raw.lottie_weather_partly_raining
        24 -> R.raw.lottie_weather_partly_raining
        25 -> R.raw.lottie_weather_snow
        26 -> R.raw.lottie_weather_snow
        27 -> R.raw.lottie_weather_snow
        else -> R.raw.lottie_weather_sunny
    }

    fun getWeatherSymbolText(weatherSymbol: Int): String = when (weatherSymbol) {
        1 -> WeatherConstants.WEATHER_SYMBOL_CLEAR_SKY
        2 -> WeatherConstants.WEATHER_SYMBOL_NEARLY_CLEAR_SKY
        3 -> WeatherConstants.WEATHER_SYMBOL_VARIABLE_CLOUDINESS
        4 -> WeatherConstants.WEATHER_SYMBOL_HALFCLEAR_SKY
        5 -> WeatherConstants.WEATHER_SYMBOL_CLOUDY_SKY
        6 -> WeatherConstants.WEATHER_SYMBOL_OVERCAST
        7 -> WeatherConstants.WEATHER_SYMBOL_FOG
        8 -> WeatherConstants.WEATHER_SYMBOL_LIGHT_RAIN_SHOWERS
        9 -> WeatherConstants.WEATHER_SYMBOL_MODERATE_RAIN_SHOWERS
        10 -> WeatherConstants.WEATHER_SYMBOL_HEAVY_RAIN_SHOWERS
        11 -> WeatherConstants.WEATHER_SYMBOL_THUNDERSTORM
        12 -> WeatherConstants.WEATHER_SYMBOL_LIGHT_SLEET_SHOWERS
        13 -> WeatherConstants.WEATHER_SYMBOL_MODERATE_SLEET_SHOWERS
        14 -> WeatherConstants.WEATHER_SYMBOL_HEAVY_SLEET_SHOWERS
        15 -> WeatherConstants.WEATHER_SYMBOL_LIGHT_SNOW_SHOWERS
        16 -> WeatherConstants.WEATHER_SYMBOL_MODERATE_SNOW_SHOWERS
        17 -> WeatherConstants.WEATHER_SYMBOL_HEAVY_SNOW_SHOWERS
        18 -> WeatherConstants.WEATHER_SYMBOL_LIGHT_RAIN
        19 -> WeatherConstants.WEATHER_SYMBOL_MODERATE_RAIN
        20 -> WeatherConstants.WEATHER_SYMBOL_HEAVY_RAIN
        21 -> WeatherConstants.WEATHER_SYMBOL_THUNDER
        22 -> WeatherConstants.WEATHER_SYMBOL_LIGHT_SLEET
        23 -> WeatherConstants.WEATHER_SYMBOL_MODERATE_SLEET
        24 -> WeatherConstants.WEATHER_SYMBOL_HEAVY_SLEET
        25 -> WeatherConstants.WEATHER_SYMBOL_LIGHT_SNOWFALL
        26 -> WeatherConstants.WEATHER_SYMBOL_MODERATE_SNOWFALL
        27 -> WeatherConstants.WEATHER_SYMBOL_HEAVY_SNOWFALL
        else -> "N/A"
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
                        getWeatherSymbolImage(listOfTenDayForecast[0].listOfHourlyData[i].weatherSymbol)
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
                        getWeatherSymbolImage(listOfHourlyData[i].weatherSymbol)
                    )
                )
            }
        }
        return tempList
    }


}
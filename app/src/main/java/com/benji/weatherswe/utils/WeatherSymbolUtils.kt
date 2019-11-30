package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.Parameter
import com.benji.weatherswe.R

object WeatherSymbolUtils {

    fun getWeatherSymbolHour(parameters: List<Parameter>): Int {
        var weatherSymbol = 1
        parameters.forEach { parameter ->
            if (parameter.name == WeatherConstants.PARAMETER_WEATHER_SYMBOL) {
                weatherSymbol = parameter.values[0].toInt()
            }
        }
        return weatherSymbol
    }

    fun getWeatherSymbolDay(listOfHourlyData: MutableList<Hourly>): Int {
        val frequenciesByWeatherSymbol =
            listOfHourlyData.groupingBy { it.weatherSymbol }.eachCount()
        return frequenciesByWeatherSymbol.maxBy { it.value }?.key!!
    }

    fun getWeatherSymbolLottie(weatherSymbol: Int): Int = when (weatherSymbol) {
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

    fun getWeatherSymbolDescription(weatherSymbol: Int): String = when (weatherSymbol) {
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

}
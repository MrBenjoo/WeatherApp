package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.Parameter
import kotlin.math.roundToInt

object TemperatureUtils {

    fun getHighestTemperature(listOfHourlyData: MutableList<Hourly>): String =
        listOfHourlyData.maxBy { it.temp.toDouble() }?.temp ?: "N/A"


    fun getCurrentTemperature(parameters: List<Parameter>): String {
        var tempCurrent = 0.0
        parameters.forEach { parameter ->
            if (parameter.name == WeatherConstants.PARAMETER_AIR_TEMPERATURE) {
                tempCurrent = parameter.values[0].toDouble()
            }
        }
        return tempCurrent.roundToInt().toString()
    }
}
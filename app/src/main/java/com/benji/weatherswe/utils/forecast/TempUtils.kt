package com.benji.weatherswe.utils.forecast

import com.benji.domain.constants.ParameterConstants
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.Parameter
import kotlin.math.roundToInt

object TempUtils {

    fun getHighestTemperature(listOfHourlyData: MutableList<Hourly>): String =
        listOfHourlyData.maxBy { it.temp.toDouble() }?.temp ?: "N/A"

    fun getCurrentTemperature(parameters: List<Parameter>): String {
        var tempCurrent = 0.0
        val parameter = parameters.find { it.name == ParameterConstants.PARAMETER_AIR_TEMPERATURE }
        parameter?.let { tempCurrent = parameter.values[0].toDouble() }
        return tempCurrent.roundToInt().toString()
    }
}
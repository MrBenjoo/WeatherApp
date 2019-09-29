package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.Parameter

class WeatherUtils {

    public fun getParameters(parameters: List<Parameter>): List<Parameter> {
        val list = mutableListOf<Parameter>()
        val level = parameters[0].level
        val levelType = parameters[1].levelType
        val name = parameters[2].name
        val unit = parameters[3].unit
        val values = parameters[4].values
        val parameter = Parameter(level, levelType, name, unit, values)
        list.add(parameter)
        return list
    }


}
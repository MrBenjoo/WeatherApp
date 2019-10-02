package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.Parameter

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


}
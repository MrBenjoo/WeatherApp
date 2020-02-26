package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.Hourly
import com.benji.weatherswe.utils.forecast.SymbolUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SymbolUtilsTest {

    @Test
    fun `getWeatherSymbolDay() with 1 as the highest symbol occurrence in the list should return 1 as the weather symbol`() {
        val hourly = mutableListOf<Hourly>()
        hourly.add(Hourly("", emptyList(), "", 1))
        hourly.add(Hourly("", emptyList(), "", 2))
        hourly.add(Hourly("", emptyList(), "", 55))
        hourly.add(Hourly("", emptyList(), "", 1))
        hourly.add(Hourly("", emptyList(), "", 1000))

        val symbol = SymbolUtils.getWeatherSymbolDay(hourly)

        assertEquals(1, symbol)
    }

    @Test
    fun `getWeatherSymbolDay() with 1 and 2 as the same amount of symbol occurrence in the list should return 2 as the weather symbol`() {
        val hourly = mutableListOf<Hourly>()
        hourly.add(Hourly("", emptyList(), "", 2))
        hourly.add(Hourly("", emptyList(), "", 1))
        hourly.add(Hourly("", emptyList(), "", 1))
        hourly.add(Hourly("", emptyList(), "", 2))
        hourly.add(Hourly("", emptyList(), "", 22))

        val symbol = SymbolUtils.getWeatherSymbolDay(hourly)

        assertEquals(2, symbol)
    }

    @Test
    fun `getWeatherSymbolDay() with as the same amount of symbol occurrence in the list should return 2 as the weather symbol`() {
        val hourly = mutableListOf<Hourly>()
        hourly.add(Hourly("", emptyList(), "", 1))
        hourly.add(Hourly("", emptyList(), "", 1))
        hourly.add(Hourly("", emptyList(), "", 1))
        hourly.add(Hourly("", emptyList(), "", 1))
        hourly.add(Hourly("", emptyList(), "", 1))

        val symbol = SymbolUtils.getWeatherSymbolDay(hourly)

        assertEquals(1, symbol)
    }

}
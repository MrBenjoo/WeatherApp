package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.Hourly
import com.benji.weatherswe.utils.forecast.TempUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TempUtilsTest {

    @Test
    fun `getHighestTemperature() with a list containing a max temp of 10 should return 10`() {
        val mockedlistOfHourlyData = mutableListOf<Hourly>()
        mockedlistOfHourlyData.add(Hourly("13:00", emptyList(), "10", 13))
        mockedlistOfHourlyData.add(Hourly("14:00", emptyList(), "9", 13))
        mockedlistOfHourlyData.add(Hourly("15:00", emptyList(), "8", 13))
        val maxTemp = TempUtils.getHighestTemperature(mockedlistOfHourlyData)

        assertEquals("10", maxTemp)
    }

    @Test
    fun `getHighestTemperature() with a list containing only temp of 5 should return 5`() {
        val mockedlistOfHourlyData = mutableListOf<Hourly>()
        mockedlistOfHourlyData.add(Hourly("13:00", emptyList(), "5", 13))
        mockedlistOfHourlyData.add(Hourly("14:00", emptyList(), "5", 13))
        mockedlistOfHourlyData.add(Hourly("15:00", emptyList(), "5", 13))
        val maxTemp = TempUtils.getHighestTemperature(mockedlistOfHourlyData)

        assertEquals("5", maxTemp)
    }

}
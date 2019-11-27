package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.weatherswe.no_test_only_helper_functions.TestUtils
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class WeatherUtilsTest {

    private lateinit var weatherUtils: WeatherUtils
    private val moshiBuild = Moshi.Builder().build()

    @BeforeEach
    fun setup() {
        weatherUtils = WeatherUtils()
    }

    @Test
    fun `getFiveHourForecastData() from 23'00 to 03'00 should return valid times from 23'00 to 03'00`() {
        val fakeListOfTenDayForecast = mutableListOf<DayForecast>()

        val jsonDayForecast2300AsString = TestUtils.loadJsonFromResources("JSON_DAYFORECAST_2300")
        val dayForecast2300 = moshiBuild.adapter(DayForecast::class.java).fromJson(jsonDayForecast2300AsString)!!
        fakeListOfTenDayForecast.add(dayForecast2300)

        val jsonDayForecast0000To0300AsString = TestUtils.loadJsonFromResources("JSON_DAYFORECAST_0000_TO_0300")
        val dayForecast0000To0300=  moshiBuild.adapter(DayForecast::class.java).fromJson(jsonDayForecast0000To0300AsString)!!
        fakeListOfTenDayForecast.add(dayForecast0000To0300)

        val returnedHourlyOverviewList = weatherUtils.getFiveHourForecastData(fakeListOfTenDayForecast)

        assertEquals("2019-11-20T23:00:00Z", returnedHourlyOverviewList[0].validTime)
        assertEquals("2019-11-21T00:00:00Z", returnedHourlyOverviewList[1].validTime)
        assertEquals("2019-11-21T01:00:00Z", returnedHourlyOverviewList[2].validTime)
        assertEquals("2019-11-21T02:00:00Z", returnedHourlyOverviewList[3].validTime)
        assertEquals("2019-11-21T03:00:00Z", returnedHourlyOverviewList[4].validTime)
    }

    @Test
    fun `getFiveHourForecastData() from 20'00 to 00'00 should return valid times from 20'00 to 00'00`() {
        val fakeListOfTenDayForecast = mutableListOf<DayForecast>()

        val json20To23String = TestUtils.loadJsonFromResources("JSON_DAYFORECAST_2000_TO_2300")
        val dayForecast20To23Clock =  moshiBuild.adapter(DayForecast::class.java).fromJson(json20To23String)!!
        fakeListOfTenDayForecast.add(dayForecast20To23Clock)

        val jsonMidnightString = TestUtils.loadJsonFromResources("JSON_DAYFORECAST_0000")
        val dayForecastMidnightClock = moshiBuild.adapter(DayForecast::class.java).fromJson(jsonMidnightString)!!
        fakeListOfTenDayForecast.add(dayForecastMidnightClock)

        val returnedHourlyOverviewList = weatherUtils.getFiveHourForecastData(fakeListOfTenDayForecast)

        assertEquals("2019-11-20T20:00:00Z", returnedHourlyOverviewList[0].validTime)
        assertEquals("2019-11-20T21:00:00Z", returnedHourlyOverviewList[1].validTime)
        assertEquals("2019-11-20T22:00:00Z", returnedHourlyOverviewList[2].validTime)
        assertEquals("2019-11-20T23:00:00Z", returnedHourlyOverviewList[3].validTime)
        assertEquals("2019-11-21T00:00:00Z", returnedHourlyOverviewList[4].validTime)
    }


    @Test
    fun `getFiveHourForecastData() from 12'00 to 16'00 should return valid times from 12'00 to 16'00`() {
        val fakeListOfTenDayForecast = mutableListOf<DayForecast>()

        val jsonDayForecastFrom1200To1600AsString = TestUtils.loadJsonFromResources("JSON_DAYFORECAST_1200_TO_1600")
        val dayForecastFrom1200To1600 =  moshiBuild.adapter(DayForecast::class.java).fromJson(jsonDayForecastFrom1200To1600AsString)!!
        fakeListOfTenDayForecast.add(dayForecastFrom1200To1600)

        val returnedHourlyOverviewList = weatherUtils.getFiveHourForecastData(fakeListOfTenDayForecast)

        assertEquals("2019-11-20T12:00:00Z", returnedHourlyOverviewList[0].validTime)
        assertEquals("2019-11-20T13:00:00Z", returnedHourlyOverviewList[1].validTime)
        assertEquals("2019-11-20T14:00:00Z", returnedHourlyOverviewList[2].validTime)
        assertEquals("2019-11-20T15:00:00Z", returnedHourlyOverviewList[3].validTime)
        assertEquals("2019-11-20T16:00:00Z", returnedHourlyOverviewList[4].validTime)
    }

}
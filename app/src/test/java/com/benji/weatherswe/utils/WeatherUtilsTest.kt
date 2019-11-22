package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.weather.DayForecast
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.*


internal class WeatherUtilsTest {

    private lateinit var weatherUtils: WeatherUtils

    @BeforeEach
    fun setup() {
        weatherUtils = WeatherUtils()
    }

    @Test
    fun `getFiveHourForecastData() from 23'00 to 03'00 should return valid times from 23'00 to 03'00`() {
        val fakeListOfTenDayForecast = mutableListOf<DayForecast>()

        val jsonDayForecast2300AsInputStream = this.javaClass.classLoader!!.getResourceAsStream("JSON_DAYFORECAST_2300")
        val jsonDayForecast2300AsString = convertStreamToString(jsonDayForecast2300AsInputStream)
        val dayForecast2300 =  Moshi.Builder().build().adapter(DayForecast::class.java).fromJson(jsonDayForecast2300AsString)!!
        fakeListOfTenDayForecast.add(dayForecast2300)

        val jsonDayForecast0000To0300AsInputStream = this.javaClass.classLoader!!.getResourceAsStream("JSON_DAYFORECAST_0000_TO_0300")
        val jsonDayForecast0000To0300AsString = convertStreamToString(jsonDayForecast0000To0300AsInputStream)
        val dayForecast0000To0300=  Moshi.Builder().build().adapter(DayForecast::class.java).fromJson(jsonDayForecast0000To0300AsString)!!
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

        val json20To23TextAsInputStream = this.javaClass.classLoader!!.getResourceAsStream("JSON_DAYFORECAST_2000_TO_2300")
        val json20To23String = convertStreamToString(json20To23TextAsInputStream)
        val dayForecast20To23Clock =  Moshi.Builder().build().adapter(DayForecast::class.java).fromJson(json20To23String)!!
        fakeListOfTenDayForecast.add(dayForecast20To23Clock)

        val jsonMidnightTextAsInputStream = this.javaClass.classLoader!!.getResourceAsStream("JSON_DAYFORECAST_0000")
        val jsonMidnightString = convertStreamToString(jsonMidnightTextAsInputStream)
        val dayForecastMidnightClock =  Moshi.Builder().build().adapter(DayForecast::class.java).fromJson(jsonMidnightString)!!
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

        val jsonDayForecastFrom1200To1600AsInputStream = this.javaClass.classLoader!!.getResourceAsStream("JSON_DAYFORECAST_1200_TO_1600")
        val jsonDayForecastFrom1200To1600AsString = convertStreamToString(jsonDayForecastFrom1200To1600AsInputStream)
        val dayForecastFrom1200To1600 =  Moshi.Builder().build().adapter(DayForecast::class.java).fromJson(jsonDayForecastFrom1200To1600AsString)!!
        fakeListOfTenDayForecast.add(dayForecastFrom1200To1600)

        val returnedHourlyOverviewList = weatherUtils.getFiveHourForecastData(fakeListOfTenDayForecast)

        assertEquals("2019-11-20T12:00:00Z", returnedHourlyOverviewList[0].validTime)
        assertEquals("2019-11-20T13:00:00Z", returnedHourlyOverviewList[1].validTime)
        assertEquals("2019-11-20T14:00:00Z", returnedHourlyOverviewList[2].validTime)
        assertEquals("2019-11-20T15:00:00Z", returnedHourlyOverviewList[3].validTime)
        assertEquals("2019-11-20T16:00:00Z", returnedHourlyOverviewList[4].validTime)
    }

    @Throws(Exception::class)
    fun convertStreamToString(`is`: InputStream): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line = reader.readLine()
        while ( line != null) {
            sb.append(line).append("\n")
            line = reader.readLine()
        }
        reader.close()
        return sb.toString()
    }

    @Throws(Exception::class)
    fun getStringFromFile(filePath: String): String {
        val fl = File(filePath)
        val fin = FileInputStream(fl)
        val ret = convertStreamToString(fin)
        //Make sure you close all streams.
        fin.close()
        return ret
    }
}
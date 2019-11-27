package com.benji.weatherswe.dayweather

import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository
import com.benji.weatherswe.InstantExecutorExtension
import com.benji.weatherswe.no_test_only_helper_functions.TestUtils
import com.benji.weatherswe.utils.DispatcherProvider
import com.squareup.moshi.Moshi
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(InstantExecutorExtension::class)
internal class DayWeatherViewModelTest {

    private lateinit var viewModel: DayWeatherViewModel
    private lateinit var mockedWeatherData: Weather

    private val weatherRepository: IWeatherRepository = mockk()
    private val dispatcher: DispatcherProvider = mockk()

    private val candidateMalmo = Candidate("Malmö", Location(55.60, 13.00), 100)

    @BeforeEach
    fun setup() {
        viewModel = DayWeatherViewModel(dispatcher, weatherRepository)
        val jsonWeatherString = TestUtils.loadJsonFromResources("JSON_WEATHER")

        val moshiBuild = Moshi.Builder().build()
        mockedWeatherData = moshiBuild.adapter(Weather::class.java).fromJson(jsonWeatherString)!!
    }

    @Test
    fun `processWeatherData() With Valid Candidate And Mocked Weather Data Should Set A List Of Ten Day Forecast`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData)

        with(viewModel.listOfTenDayForecast) {
            assertEquals("14:00", value!![0].listOfHourlyData[0].validTime)
            assertEquals("15:00", value!![0].listOfHourlyData[1].validTime)
            assertEquals("16:00", value!![0].listOfHourlyData[2].validTime)
            assertEquals("17:00", value!![0].listOfHourlyData[3].validTime)
            assertEquals("18:00", value!![0].listOfHourlyData[4].validTime)
        }
    }

    @Test
    fun `processWeatherData() with valid candidate and mocked weather data should set last valid time to 2300 on first day`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData)

        with(viewModel.listOfTenDayForecast) {
            assertEquals("23:00", value!![0].listOfHourlyData[9].validTime)
            assertThrows(IndexOutOfBoundsException::class.java) { value!![0].listOfHourlyData[10].validTime }
        }
    }

    @Test
    fun `processWeatherData() with valid candidate and mocked weather data should set first valid time to 0000 on second day`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData)

        with(viewModel.listOfTenDayForecast) {
            assertEquals("00:00", value!![1].listOfHourlyData[0].validTime)
        }
    }


    @Test
    fun `processWeatherData() with valid candidate and mocked weather data should set 10 hours of weather data on first day`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData)
        assertEquals(10, viewModel.listOfTenDayForecast.value!![0].listOfHourlyData.size)
    }

    @Test
    fun `processWeatherData() with valid candidate and mocked weather data should set 24 hours of weather data on second day`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData)
        assertEquals(24, viewModel.listOfTenDayForecast.value!![1].listOfHourlyData.size)
    }

    @Test
    fun `getHourlyForecastData() 2300 should return 2300 as valid time`() {
        val hourly = viewModel.getHourlyForecastData(mockedWeatherData.timeSeries[9])
        assertEquals("23:00", hourly.validTime)
        assertEquals("7°", hourly.temp)
    }

    @Test
    fun `getHourlyForecastData() 0000 should return 0000 as valid time`() {
        val hourly = viewModel.getHourlyForecastData(mockedWeatherData.timeSeries[10])
        assertEquals("00:00", hourly.validTime)
        assertEquals("7°", hourly.temp)
    }

    @Test
    fun `getHourlyForecastData() 0100 should return 0100 as valid time`() {
        val hourly = viewModel.getHourlyForecastData(mockedWeatherData.timeSeries[11])
        assertEquals("01:00", hourly.validTime)
        assertEquals("7°", hourly.temp)
    }


}
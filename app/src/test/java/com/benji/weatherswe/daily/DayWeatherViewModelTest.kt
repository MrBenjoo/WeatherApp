package com.benji.weatherswe.daily

import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IGeocodingRepository
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

    private lateinit var viewModel: DailyViewModel
    private lateinit var mockedWeatherData: Weather

    private val weatherRepository: IWeatherRepository = mockk()
    private val geocodingRepository: IGeocodingRepository = mockk()
    private val dispatcher: DispatcherProvider = mockk()

    private val candidateMalmo = Candidate("Malmö", Location(55.60, 13.00), 100)

    @BeforeEach
    fun setup() {
        viewModel = DailyViewModel(dispatcher, weatherRepository, geocodingRepository)
        val jsonWeatherString = TestUtils.loadJsonFromResources("JSON_WEATHER")

        val moshiBuild = Moshi.Builder().build()
        mockedWeatherData = moshiBuild.adapter(Weather::class.java).fromJson(jsonWeatherString)!!
    }

    @Test
    fun `processWeatherData() With Valid Candidate And Mocked Weather Data Should Set A List Of Ten Day Forecast`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData, "2019-11-27")

        with(viewModel.listOfDayForecast) {
            assertEquals("14:00", value!![0].listOfHourlyData[0].validTime)
            assertEquals("15:00", value!![0].listOfHourlyData[1].validTime)
            assertEquals("16:00", value!![0].listOfHourlyData[2].validTime)
            assertEquals("17:00", value!![0].listOfHourlyData[3].validTime)
            assertEquals("18:00", value!![0].listOfHourlyData[4].validTime)
        }
    }

    @Test
    fun `processWeatherData() with valid candidate and mocked weather data should set last valid time to 2300 on first day`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData, "2019-11-27")

        with(viewModel.listOfDayForecast) {
            assertEquals("23:00", value!![0].listOfHourlyData[9].validTime)
            assertThrows(IndexOutOfBoundsException::class.java) { value!![0].listOfHourlyData[10].validTime }
        }
    }

    @Test
    fun `processWeatherData() with valid candidate and mocked weather data should set first valid time to 0000 on second day`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData, "2019-11-27")

        with(viewModel.listOfDayForecast) {
            assertEquals("00:00", value!![1].listOfHourlyData[0].validTime)
        }
    }


    @Test
    fun `processWeatherData() with valid candidate and mocked weather data should set 10 hours of weather data on first day`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData, "2019-11-27")
        assertEquals(10, viewModel.listOfDayForecast.value!![0].listOfHourlyData.size)
    }

    @Test
    fun `processWeatherData() with valid candidate and mocked weather data should set 24 hours of weather data on second day`() {
        viewModel.processWeatherData(candidateMalmo, mockedWeatherData, "2019-11-27")
        assertEquals(24, viewModel.listOfDayForecast.value!![1].listOfHourlyData.size)
    }




}
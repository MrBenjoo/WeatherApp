package com.benji.weatherswe.dayweather

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository
import com.benji.weatherswe.InstantExecutorExtension
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.weatherswe.utils.sixDecimals
import com.squareup.moshi.Moshi
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class DayWeatherViewModelTest {

    private lateinit var dayWeatherViewModel: DayWeatherViewModel
    private lateinit var weather: Weather

    private val weatherRepository: IWeatherRepository = mockk()
    private val dispatcher: DispatcherProvider = mockk()

    private val candidate = Candidate("Malmö", Location(13.8, 56.8), 100)
    private lateinit var nullCandidate : Candidate

    @BeforeEach
    fun setup() {
        dayWeatherViewModel = DayWeatherViewModel(dispatcher, weatherRepository)
        weather =
            Moshi.Builder().build().adapter(Weather::class.java).fromJson(Constants.JSON_WEATHER)!!
    }

    @Test
    fun `GetWeatherForecast() With Valid Candidate Should Return Valid Weather Object`() =
        runBlocking {

            every {
                dispatcher.provideUIContext()
            } returns Dispatchers.Unconfined

            coEvery {
                weatherRepository.getWeatherForecast(candidate.location.sixDecimals())
            } returns ResultWrapper.build { weather }

            dayWeatherViewModel.getWeatherForecast(candidate)

            assertEquals(weather, dayWeatherViewModel.weather.value)
        }

   @Test
   fun `getHourlyForecastData should return valid Hourly object`() {
       val hourly = dayWeatherViewModel.getHourlyForecastData(weather.timeSeries[0])
       assertEquals("12:00", hourly.validTime)
       assertEquals("12°", hourly.temp)
   }


}
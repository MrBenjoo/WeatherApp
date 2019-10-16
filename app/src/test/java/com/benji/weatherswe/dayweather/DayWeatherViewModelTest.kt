package com.benji.weatherswe.dayweather

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository
import com.benji.weatherswe.InstantExecutorExtension
import com.benji.weatherswe.utils.DispatcherProvider
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


    @BeforeEach
    fun setup() {
        dayWeatherViewModel = DayWeatherViewModel(dispatcher, weatherRepository)
        weather =
            Moshi.Builder().build().adapter(Weather::class.java).fromJson(Constants.JSON_WEATHER)!!
    }

    @Test
    fun `On Get Weather Forecast Should Return Valid Weather Object And Set MutableLiveData Value`() =
        runBlocking {

            every {
                dispatcher.provideUIContext()
            } returns Dispatchers.Unconfined

            coEvery {
                weatherRepository.getWeatherForecast(Location(13.8, 56.8))
            } returns ResultWrapper.build { weather }

            dayWeatherViewModel.getWeatherForecast(Candidate("Malmö", Location(13.8, 56.8), 100))

            assertEquals(weather, dayWeatherViewModel.weather.value)
        }


}
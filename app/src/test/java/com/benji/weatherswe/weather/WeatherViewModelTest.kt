package com.benji.weatherswe.weather

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.usecases.GetWeatherForecast
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.weatherswe.InstantExecutorExtension
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
internal class WeatherViewModelTest {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weather: Weather

    private val weatherForecastUseCase: GetWeatherForecast = mockk()
    private val dispatcher: DispatcherProvider = mockk()


    @BeforeEach
    fun setup() {
        weatherViewModel = WeatherViewModel(dispatcher, weatherForecastUseCase)
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
                weatherForecastUseCase.getWeatherForecast(Location(13.8, 56.8))
            } returns ResultWrapper.build { weather }

            weatherViewModel.getWeatherForecast(Location(13.8, 56.8))

            assertEquals(weather, weatherViewModel.weather.value)
        }


}
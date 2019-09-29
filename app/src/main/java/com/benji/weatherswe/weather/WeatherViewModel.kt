package com.benji.weatherswe.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.*
import com.benji.domain.usecases.GetWeatherForecast
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.weatherswe.utils.DateUtils
import com.benji.weatherswe.utils.WeatherUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class WeatherViewModel(
    private val dispatcher: DispatcherProvider,
    private val weatherForecastUseCase: GetWeatherForecast
) : ViewModel(), CoroutineScope {

    val weather = MutableLiveData<Weather>()
    val listOfTenDayForecast = MutableLiveData<List<TenDayForecast>>()
    val weatherForecastError = MutableLiveData<String>()
    private var jobTracker: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker

    fun getWeatherForecast(latLng: Location) = launch {
        val data = weatherForecastUseCase.getWeatherForecast(latLng)
        when (data) {
            is ResultWrapper.Success -> {
                processWeatherData(data.value)
                weather.value = data.value
            }
            is ResultWrapper.Error -> weatherForecastError.value = data.error.toString()
        }
    }

    fun processWeatherData(weather: Weather) {
        val dateUtils = DateUtils()
        val weatherUtils = WeatherUtils()

        var dayOfWeek = dateUtils.getFormattedTime(dateUtils.getCurrentTime())

        val listOfHourlyData = mutableListOf<Hourly>()
        val listOfTenDayForecast = mutableListOf<TenDayForecast>()

        weather.timeSeries.forEach { timeSeries ->

            val dayOfWeekAPI = dateUtils.getFormattedTime(timeSeries.validTime)

            listOfHourlyData.add(
                Hourly(
                    weatherUtils.getParameters(
                        timeSeries.parameters
                    ),
                    timeSeries.validTime
                )
            )

            if (dayOfWeekAPI != dayOfWeek) {
                listOfTenDayForecast.add(
                    TenDayForecast(
                        dateUtils.getDay(dayOfWeek),
                        "Malmö",
                        "18C",
                        listOfHourlyData
                    )
                )
                listOfHourlyData.clear()
                dayOfWeek = dayOfWeekAPI
            }
        }

        this.listOfTenDayForecast.value = listOfTenDayForecast
    }

    override fun onCleared() {
        super.onCleared()
        jobTracker.cancel()
    }

}




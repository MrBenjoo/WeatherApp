package com.benji.weatherswe.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.TimeSeries
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository
import com.benji.weatherswe.utils.DateUtils
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.weatherswe.utils.WeatherUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class WeatherViewModel(
    private val dispatcher: DispatcherProvider,
    private val weatherRepository: IWeatherRepository,
    private val city: String
) : ViewModel(), CoroutineScope {
    private val TAG = "WeatherViewModel"

    val weather = MutableLiveData<Weather>()
    val listOfTenDayForecast = MutableLiveData<List<DayForecast>>()
    val weatherForecastError = MutableLiveData<String>()
    private var jobTracker = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker

    fun getWeatherForecast(latLng: Location) = launch {
        val data = weatherRepository.getWeatherForecast(latLng)
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

        var currentDate = dateUtils.getCurrentTime()

        var listOfHourlyData = mutableListOf<Hourly>()
        val listOfTenDayForecast = mutableListOf<DayForecast>()


        weather.timeSeries.forEach { timeSeries ->
            val apiDate = dateUtils.getFormattedTime(timeSeries.validTime)

            listOfHourlyData.add(getHourlyForecastData(timeSeries))

            if (apiDate != currentDate) {
                val day = DayForecast(
                    currentDate,
                    dateUtils.getDay(currentDate),
                    city,
                    "18C",
                    listOfHourlyData
                )

                listOfTenDayForecast.add(day)
                listOfHourlyData = mutableListOf()
                currentDate = apiDate
            }
        }


        this.listOfTenDayForecast.value = listOfTenDayForecast
    }

    private fun getHourlyForecastData(timeSeries: TimeSeries): Hourly {
        val parameters = WeatherUtils().getParameters(timeSeries.parameters)
        val validTime = DateUtils().getHourlyTime(timeSeries.validTime)
        val hourly = Hourly(validTime, parameters)
        return hourly
    }


    override fun onCleared() {
        super.onCleared()
        jobTracker.cancel()
    }

}




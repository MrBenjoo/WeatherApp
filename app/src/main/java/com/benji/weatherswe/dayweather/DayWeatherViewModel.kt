package com.benji.weatherswe.dayweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.TimeSeries
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IWeatherRepository
import com.benji.weatherswe.BaseViewModel
import com.benji.weatherswe.utils.DateUtils
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.weatherswe.utils.WeatherUtils
import com.benji.weatherswe.utils.sixDecimals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class DayWeatherViewModel(
    private val dispatcher: DispatcherProvider,
    private val weatherRepository: IWeatherRepository
) : BaseViewModel(), CoroutineScope {
    private val TAG = "DayWeatherViewModel"

    val weather = MutableLiveData<Weather>()
    val listOfTenDayForecast = MutableLiveData<List<DayForecast>>()
    val weatherForecastError = MutableLiveData<String>()

    private var jobTracker = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker

    fun getWeatherForecast(candidate: Candidate) = launch {
        setInFlightState()
        val data = weatherRepository.getWeatherForecast(candidate.location.sixDecimals())
        when (data) {
            is ResultWrapper.Success -> {
                processWeatherData(candidate, data.value)
                weather.value = data.value
            }
            is ResultWrapper.Error -> weatherForecastError.value = data.error.toString()
        }
        setCompletedState()
    }

    fun processWeatherData(candidate: Candidate, weather: Weather) {
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
                    candidate.address,
                    WeatherUtils().getHighestTemperature(listOfHourlyData),
                    listOfHourlyData,
                    WeatherUtils().getWeatherSymbol(listOfHourlyData)
                )

                listOfTenDayForecast.add(day)
                listOfHourlyData = mutableListOf()
                currentDate = apiDate
            }
        }

        this.listOfTenDayForecast.value = listOfTenDayForecast
    }

    fun getHourlyForecastData(timeSeries: TimeSeries): Hourly {
        val parameters = WeatherUtils().getParameters(timeSeries.parameters)
        val validTime = DateUtils().getHourlyTime(timeSeries.validTime)
        val hourly = Hourly(
            validTime,
            parameters,
            WeatherUtils().getCurrentTemperature(parameters),
            WeatherUtils().getWeatherSymbolParameter(parameters)
        )
        return hourly
    }


    override fun onCleared() {
        super.onCleared()
        jobTracker.cancel()
    }

}




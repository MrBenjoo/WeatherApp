package com.benji.weatherswe.dayweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.*
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

    val listOfTenDayForecast = MutableLiveData<List<DayForecast>>()
    val weatherForecastError = MutableLiveData<String>()

    private var jobTracker = Job()

    private val _forecastFirstHour = MutableLiveData<HourlyOverview>()
    val forecastFirstHour: LiveData<HourlyOverview>
        get() = _forecastFirstHour

    private val _forecastSecondHour = MutableLiveData<HourlyOverview>()
    val forecastSecondHour: LiveData<HourlyOverview>
        get() = _forecastSecondHour

    private val _forecastThirdHour = MutableLiveData<HourlyOverview>()
    val forecastThirdHour: LiveData<HourlyOverview>
        get() = _forecastThirdHour

    private val _forecastFourthHour = MutableLiveData<HourlyOverview>()
    val forecastFourthHour: LiveData<HourlyOverview>
        get() = _forecastFourthHour

    private val _forecastFifthHour = MutableLiveData<HourlyOverview>()
    val forecastFifthHour: LiveData<HourlyOverview>
        get() = _forecastFifthHour


    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker

    fun getWeatherForecast(candidate: Candidate) = launch {
        setInFlightState()
        val data = weatherRepository.getWeatherForecast(candidate.location.sixDecimals())
        when (data) {
            is ResultWrapper.Success -> {
                processWeatherData(candidate, data.value)
                setFiveHourForecastData(listOfTenDayForecast.value!!)
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

            listOfHourlyData.add(getHourlyForecastData(timeSeries))

        }

        this.listOfTenDayForecast.value = listOfTenDayForecast
    }

    private fun setFiveHourForecastData(listOfTenDayForecast: List<DayForecast>) {
        val tempList = WeatherUtils().getFiveHourForecastData(listOfTenDayForecast)
        for (i in 0..4) {
            when (i) {
                0 -> _forecastFirstHour.value = HourlyOverview(
                    tempList[i].validTime,
                    tempList[i].temp,
                    tempList[i].weatherSymbol
                )
                1 -> _forecastSecondHour.value = HourlyOverview(
                    tempList[i].validTime,
                    tempList[i].temp,
                    tempList[i].weatherSymbol
                )
                2 -> _forecastThirdHour.value = HourlyOverview(
                    tempList[i].validTime,
                    tempList[i].temp,
                    tempList[i].weatherSymbol
                )

                3 -> _forecastFourthHour.value = HourlyOverview(
                    tempList[i].validTime,
                    tempList[i].temp,
                    tempList[i].weatherSymbol
                )
                4 -> _forecastFifthHour.value = HourlyOverview(
                    tempList[i].validTime,
                    tempList[i].temp,
                    tempList[i].weatherSymbol
                )
            }
        }
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




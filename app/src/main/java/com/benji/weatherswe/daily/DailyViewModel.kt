package com.benji.weatherswe.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.domain.repository.IWeatherRepository
import com.benji.weatherswe.base.BaseViewModel
import com.benji.weatherswe.utils.extensions.getCurrentWeatherOverview
import kotlinx.coroutines.launch

class DailyViewModel(
    private val weatherRepository: IWeatherRepository
) : BaseViewModel() {

    private val _forecastFirstHour = MutableLiveData<HourlyOverview>()
    val forecastFirstHour: LiveData<HourlyOverview> = _forecastFirstHour

    private val _forecastSecondHour = MutableLiveData<HourlyOverview>()
    val forecastSecondHour: LiveData<HourlyOverview> = _forecastSecondHour

    private val _forecastThirdHour = MutableLiveData<HourlyOverview>()
    val forecastThirdHour: LiveData<HourlyOverview> = _forecastThirdHour

    private val _forecastFourthHour = MutableLiveData<HourlyOverview>()
    val forecastFourthHour: LiveData<HourlyOverview> = _forecastFourthHour

    private val _forecastFifthHour = MutableLiveData<HourlyOverview>()
    val forecastFifthHour: LiveData<HourlyOverview> = _forecastFifthHour

    private val _listOfDayForecast = MutableLiveData<List<DayForecast>>()
    val listOfDayForecast: LiveData<List<DayForecast>> = _listOfDayForecast

    private val _currentWeatherOverview = MutableLiveData<CurrentWeatherOverview>()
    val currentWeatherOverview: LiveData<CurrentWeatherOverview> = _currentWeatherOverview

    fun getForecastData(candidate: Candidate) = viewModelScope.launch {
        setLoadingState()
        val data = weatherRepository.getListOfDayForecast(candidate)
        when (data) {
            is ResultWrapper.Success -> {
                _listOfDayForecast.value = data.value
                listOfDayForecast.value?.let { setFiveHourForecast(it) }
                setForecastOverview()
            }
            is ResultWrapper.Error -> _error.value = "Ett fel uppstod."
        }
        setCompletedState()
    }

    private fun setFiveHourForecast(listOfDayForecast: List<DayForecast>) {
        val listOfHourlyOverview = DailyUtils.getFiveHourForecast(listOfDayForecast)
        for (hour in 0..4) {
            val hourlyOverview = listOfHourlyOverview[hour]
            when (hour) {
                0 -> _forecastFirstHour.value = hourlyOverview
                1 -> _forecastSecondHour.value = hourlyOverview
                2 -> _forecastThirdHour.value = hourlyOverview
                3 -> _forecastFourthHour.value = hourlyOverview
                4 -> _forecastFifthHour.value = hourlyOverview
            }
        }
    }

    private fun setForecastOverview() {
        listOfDayForecast.value?.let {
            _currentWeatherOverview.value = it[0].getCurrentWeatherOverview()
        }
    }
}

data class CurrentWeatherOverview(
    val symbolDescription: String,
    val weatherSymbol: Int,
    val temperature: String
)




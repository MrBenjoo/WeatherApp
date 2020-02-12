package com.benji.weatherswe.dayweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benji.data.ErrorHandlerImpl
import com.benji.domain.domainmodel.ErrorEntity
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.*
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.repository.IGeocodingRepository
import com.benji.domain.repository.IWeatherRepository
import com.benji.weatherswe.BaseViewModel
import com.benji.weatherswe.hourweather.HourlyUtils
import com.benji.weatherswe.utils.DateUtils
import com.benji.weatherswe.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DayWeatherViewModel(
    private val dispatcher: DispatcherProvider,
    private val weatherRepository: IWeatherRepository,
    private val geoCodingRepository: IGeocodingRepository
) : BaseViewModel(), CoroutineScope {

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

    private val _citySuggestions = MutableLiveData<List<String>>()
    val citySuggestions: LiveData<List<String>> = _citySuggestions

    private var suggestions: List<Suggestion> = mutableListOf()

    private val _candidate = MutableLiveData<Candidate>()
    val candidate: LiveData<Candidate> = _candidate

    private var jobTracker = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker


    fun getForecast(candidate: Candidate) = launch {
        setInFlightState()

        val data = weatherRepository.getWeatherForecast(candidate.location.sixDecimals())
        handleWeatherData(data, candidate)

        setCompletedState()
    }

    private fun handleWeatherData(data: ResultWrapper<Exception, Weather>, candidate: Candidate) =
        when (data) {
            is ResultWrapper.Success -> {
                val forecast = data.value
                val date = DateUtils().getCurrentTime()

                processWeatherData(candidate, forecast, date)
                listOfDayForecast.value?.let { setHourlyOverview(it) }
            }
            is ResultWrapper.Error -> TODO("implement logic for ResultWrapper.Error")
        }


    fun processWeatherData(candidate: Candidate, weather: Weather, date: String) {
        var currentDate = date
        var listOfHourlyData = mutableListOf<Hourly>()
        val listOfDayForecast = mutableListOf<DayForecast>()

        weather.timeSeries.forEach { timeSeries ->
            val forecastDate = DateUtils().getFormattedTime(timeSeries.validTime)

            if (forecastDate != currentDate) {
                val day = DayUtils.getDayForecast(currentDate, candidate, listOfHourlyData)
                listOfDayForecast.add(day)
                listOfHourlyData = mutableListOf()
                currentDate = forecastDate
            }

            listOfHourlyData.add(HourlyUtils.getHourlyForecastData(timeSeries))
        }

        this._listOfDayForecast.value = listOfDayForecast
    }

    fun setHourlyOverview(forecastTenDays: List<DayForecast>) {
        val listOfHourlyOverview = HourlyUtils.getFiveHourForecastData(forecastTenDays)
        for (i in 0..4) {
            val hourlyOverview = listOfHourlyOverview[i]
            when (i) {
                0 -> _forecastFirstHour.value = hourlyOverview
                1 -> _forecastSecondHour.value = hourlyOverview
                2 -> _forecastThirdHour.value = hourlyOverview
                3 -> _forecastFourthHour.value = hourlyOverview
                4 -> _forecastFifthHour.value = hourlyOverview
            }
        }
    }

    fun onMenuFavoriteClick() {
        Log.d("DayWeatherViewModel", "onMenuFavoriteClick")
    }

    fun onSearchCity(query: String) = launch {
        if (query.trim() != "") {
            val data = geoCodingRepository.getSuggestions(query)
            when (data) {
                is ResultWrapper.Success -> onSuggestionsReceived(data.value.suggestions)
                is ResultWrapper.Error -> {
                    handleErrors(ErrorHandlerImpl().getError(data.error))
                }
            }
        }
    }

    private fun handleErrors(error: ErrorEntity) = when (error) {
        is ErrorEntity.Network -> setError("Nätverksfel")
        is ErrorEntity.NotFound -> setError("Ingen data tillgänglig för tillfället.")
        is ErrorEntity.AccessDenied -> setError("Förbjuden åtkomst.")
        is ErrorEntity.ServiceUnavailable -> setError("Servern är inte redo att hantera begäran")
        is ErrorEntity.Unknown -> setError("Ett fel uppstod.")
    }


    fun onSuggestionClicked(index: Int) = launch {
        setInFlightState()

        val city = suggestions[index].text
        val magicKey = suggestions[index].magicKey

        val data = geoCodingRepository.getCandidateLocation(city, magicKey)
        handleGeoCodingData(data)

        setCompletedState()
    }

    private fun handleGeoCodingData(data: ResultWrapper<Exception, Candidates>) {
        when (data) {
            is ResultWrapper.Success -> {
                val candidates = data.value
                _candidate.value = candidates.returnCandidateWithHighestScore()
            }
            is ResultWrapper.Error -> {
                handleErrors(ErrorHandlerImpl().getError(data.error))
            }
        }
    }


    private fun onSuggestionsReceived(suggestions: List<Suggestion>) {
        this.suggestions = suggestions
        _citySuggestions.value = suggestions.returnCities()
    }

    override fun onCleared() {
        super.onCleared()
        jobTracker.cancel()
    }

}




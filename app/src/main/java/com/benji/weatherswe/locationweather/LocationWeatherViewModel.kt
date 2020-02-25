package com.benji.weatherswe.locationweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.*
import com.benji.domain.location.IReversedGeocoding
import com.benji.domain.repository.IGeocodingRepository
import com.benji.weatherswe.BaseViewModel
import com.benji.weatherswe.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LocationWeatherViewModel(
    private val dispatcher: DispatcherProvider,
    private val geocodingRepository: IGeocodingRepository,
    private val reveresedGeocoding: IReversedGeocoding
) : BaseViewModel(), CoroutineScope {

    // Used to retrieve the name of the city when the user clicks on a suggestion from the list
    private var suggestions: List<Suggestion> = mutableListOf()

    // The suggestions that are shown in the list
    private val _citySuggestions = MutableLiveData<List<String>>()
    val citySuggestions: LiveData<List<String>> = _citySuggestions

    private val _candidate = MutableLiveData<Candidate>()
    val candidate: LiveData<Candidate> = _candidate

    private var jobTracker = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher.provideUIContext() + jobTracker


    /**
     * @param textSearch the text that the user inputs in the search field when searching for
     * a city.
     */
    fun getCitySuggestions(textSearch: String) = launch {
        val data = geocodingRepository.getSuggestions(textSearch)
        when (data) {
            is ResultWrapper.Success -> onSuggestionsReceived(data.value.suggestions)
            is ResultWrapper.Error -> Log.d("LocationVM", "error: " + data.error.message)
        }
    }


    /**
     * Set the new list of suggestion and list of cities.
     *
     * @param suggestions the list of suggestions that is retrieved from the API.
     *
     */
    private fun onSuggestionsReceived(suggestions: List<Suggestion>) {
        this.suggestions = suggestions
        _citySuggestions.value = suggestions.returnCities()
    }


    /**
     * Called when the user clicks on a city in the list of suggestions.
     *
     * @param index used as an integer value to know which city the user selected from the list
     * in order to get the magicKey and city name from the list.
     */
    fun onSuggestionClicked(index: Int) = launch {
        setInFlightState()
        val city = suggestions[index].text
        val magicKey = suggestions[index].magicKey

        val data = geocodingRepository.getCandidateLocation(city, magicKey)
        handleGeoCodingData(data)

        setCompletedState()
    }

    private fun handleGeoCodingData(data: ResultWrapper<Exception, Candidates>) = when (data) {
        is ResultWrapper.Success -> {
            val candidates = data.value
            _candidate.value = candidates.returnCandidateWithHighestScore()
        }
        is ResultWrapper.Error -> Log.d("LocWVM", "handleGeoCodingData --> ResultWrapper.Error")
    }

    override fun onCleared() {
        super.onCleared()
        jobTracker.cancel()
    }

    fun onLocationReceived(location: Location?) {
        location?.let {
            val city = reveresedGeocoding.getFromLocation(location)
            _candidate.value = Candidate(city, location, 100)
        }
    }


}




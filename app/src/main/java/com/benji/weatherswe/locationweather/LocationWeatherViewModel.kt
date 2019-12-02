package com.benji.weatherswe.locationweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.CompareScore
import com.benji.domain.domainmodel.geocoding.Suggestion
import com.benji.domain.repository.IGeocodingRepository
import com.benji.weatherswe.BaseViewModel
import com.benji.weatherswe.utils.DispatcherProvider
import com.benji.weatherswe.utils.returnCities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class LocationWeatherViewModel(
    private val dispatcher: DispatcherProvider,
    private val geocodingRepository: IGeocodingRepository
) : BaseViewModel(), CoroutineScope {

    // Used to retrieve the name of the city when the user clicks on a suggestion from the list
    private var suggestions: List<Suggestion> = mutableListOf()

    // The suggestions that are shown in the list
    private val _citySuggestions = MutableLiveData<List<String>>()
    val citySuggestions: LiveData<List<String>>
        get() = _citySuggestions

    private val _candidate = MutableLiveData<Candidate>()
    val candidate: LiveData<Candidate>
        get() = _candidate

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
            is ResultWrapper.Error -> TODO("Handle error values")
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
        when (data) {
            is ResultWrapper.Success -> {
                _candidate.value = Collections.max(data.value.candidates, CompareScore())
            }
            is ResultWrapper.Error -> TODO("Handle error values")
        }
        setCompletedState()
    }

    override fun onCleared() {
        super.onCleared()
        jobTracker.cancel()
    }

}




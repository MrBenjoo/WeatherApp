package com.benji.weatherswe.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.CompareScore
import com.benji.domain.domainmodel.geocoding.Suggestion
import com.benji.domain.usecases.GetLocationCandidate
import com.benji.domain.usecases.GetLocationSuggestions
import com.benji.weatherswe.utils.returnCities
import kotlinx.coroutines.launch
import java.util.*

class StartPageViewModel(
    private val getLocationSuggestions: GetLocationSuggestions,
    private val getLocationCandidate: GetLocationCandidate
) : ViewModel() {

    // Used to retrieve the name of the city when the user clicks on a suggestion from the list
    private var suggestions: List<Suggestion> = mutableListOf()

    // The suggestions that are shown in the list
    val citySuggestions: MutableLiveData<List<String>> = MutableLiveData()

    // The error message that is displayed in the fragment
    val errorMessage : MutableLiveData<String> = MutableLiveData()


    /**
     * @param textSearch the text that the user inputs in the search field when searching for
     * a city.
     */
    fun getCitySuggestions(textSearch: String) = viewModelScope.launch {
        val data = getLocationSuggestions.getLocationSuggestions(textSearch)
        when (data) {
            is ResultWrapper.Success -> onSuggestionsReceived(data.value.suggestions)
            is ResultWrapper.Error -> errorMessage.value = data.error.message
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
        citySuggestions.value = suggestions.returnCities()
    }


    /**
     * Called when the user clicks on a city in the list of suggestions.
     *
     * @param index used as an integer value to know which city the user selected from the list
     * in order to get the magicKey and city name from the list.
     */
    fun onSuggestionClicked(index: Int) = viewModelScope.launch {
        val city = suggestions[index].text
        val magicKey = suggestions[index].magicKey

        val data = getLocationCandidate.getCandidateLocation(city, magicKey)
        when (data) {
            is ResultWrapper.Success -> {
                val candidate = Collections.max(data.value.candidates, CompareScore())
            }
            is ResultWrapper.Error -> errorMessage.value = data.error.message
        }
    }

}




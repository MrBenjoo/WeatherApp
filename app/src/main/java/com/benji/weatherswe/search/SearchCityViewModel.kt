package com.benji.weatherswe.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.Suggestion
import com.benji.domain.domainmodel.geocoding.returnCandidateWithHighestScore
import com.benji.domain.domainmodel.geocoding.returnCities
import com.benji.domain.repository.IGeocodingRepository
import kotlinx.coroutines.launch

class SearchCityViewModel(private val repo: IGeocodingRepository) : ViewModel() {

    private val _citySuggestions = MutableLiveData<List<String>>()
    val citySuggestions: LiveData<List<String>> = _citySuggestions

    private val _candidate = MutableLiveData<Candidate>()
    val candidate: LiveData<Candidate> = _candidate

    // Used to retrieve the name of the city when the user clicks on a suggestion from the list
    private var suggestions: List<Suggestion> = mutableListOf()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryInput -> onQueryInput(event.query)
            is SearchEvent.OnSuggestionClick -> onSuggestionClicked(event.index)
        }
    }

    private fun onQueryInput(query: String?) {
        when (query != null && query.trim() != "") {
            true -> getCitySuggestions(query)
            false -> Unit
        }
    }

    private fun onSuggestionClicked(index: Int) = viewModelScope.launch {
        val city = suggestions[index].text
        val magicKey = suggestions[index].magicKey

        when (val data = repo.getCandidateLocation(city, magicKey)) {
            is ResultWrapper.Success -> {
                _candidate.value = data.value.returnCandidateWithHighestScore()
            }
            is ResultWrapper.Error -> {
                Log.d("SearchCityViewModel", "error: " + data.error.message)
            }
        }
    }


    private fun getCitySuggestions(query: String) = viewModelScope.launch {
        when (val data = repo.getSuggestions(query)) {
            is ResultWrapper.Success -> {
                suggestions = data.value.suggestions
                _citySuggestions.value = suggestions.returnCities()
            }
            is ResultWrapper.Error -> {
                Log.d("SearchCityViewModel", "error: " + data.error.message)
            }
        }
    }


}

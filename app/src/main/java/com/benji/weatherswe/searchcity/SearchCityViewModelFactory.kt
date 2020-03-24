package com.benji.weatherswe.searchcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benji.domain.repository.IGeocodingRepository

class SearchCityViewModelFactory(private val repo: IGeocodingRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = SearchCityViewModelFactory(repo) as T

}
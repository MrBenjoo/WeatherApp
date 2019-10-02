package com.benji.weatherswe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.DayForecast

class SharedViewModel: ViewModel() {
    val candidate = MutableLiveData<Candidate>()

    lateinit var currentDayForecast : DayForecast

}
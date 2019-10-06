package com.benji.weatherswe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.Parameter

class SharedViewModel: ViewModel() {

    lateinit var hourly: Hourly

    lateinit var hourlyMap: Map<String, Parameter>

    val candidate = MutableLiveData<Candidate>()

    lateinit var currentDayForecast : DayForecast


}
package com.benji.weatherswe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.Parameter

class SharedViewModel : ViewModel() {

    lateinit var hourly: Hourly

    lateinit var hourlyMap: Map<String, Parameter>

    lateinit var candidate: Candidate

    lateinit var currentDayForecast: DayForecast

    lateinit var todayDate : String


}
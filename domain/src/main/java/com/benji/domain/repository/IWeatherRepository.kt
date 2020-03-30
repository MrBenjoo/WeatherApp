package com.benji.domain.repository

import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.DayForecast

interface IWeatherRepository {

    suspend fun getListOfDayForecast(candidate: Candidate) : ResultWrapper<Exception, List<DayForecast>>

}
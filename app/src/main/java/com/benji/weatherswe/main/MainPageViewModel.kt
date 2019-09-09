package com.benji.weatherswe.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benji.domain.ResultWrapper
import com.benji.domain.usecases.GetWeatherForecast
import kotlinx.coroutines.launch

class MainPageViewModel(private val weatherForecastUseCase: GetWeatherForecast) : ViewModel() {

    fun getWeatherForecast(longitude: String, latitude: String) {
        viewModelScope.launch {
            val data = weatherForecastUseCase.getWeatherForecast(longitude, latitude)
            when (data) {
                is ResultWrapper.Success -> Log.d("WeatherDataValue", data.value.toString())
                is ResultWrapper.Error -> Log.d("WeatherDataError", data.error.toString())
            }
        }
    }
}

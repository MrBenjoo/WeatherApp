package com.benji.weatherswe.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.usecases.GetWeatherForecast
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherForecastUseCase: GetWeatherForecast) : ViewModel() {
    val weather = MutableLiveData<Weather>()

    fun getWeatherForecast(latLng : Location) {
        viewModelScope.launch {
            val data = weatherForecastUseCase.getWeatherForecast(latLng)
            when (data) {
                is ResultWrapper.Success -> weather.value = data.value
                is ResultWrapper.Error -> Log.d("WeatherDataError", data.error.toString())
            }
        }
    }
}

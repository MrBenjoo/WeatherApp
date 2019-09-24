package com.benji.weatherswe.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benji.domain.ResultWrapper
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.weather.CurrentDayUntilMidnight
import com.benji.domain.domainmodel.weather.Weather
import com.benji.domain.usecases.GetWeatherForecast
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*



class WeatherViewModel(private val weatherForecastUseCase: GetWeatherForecast) : ViewModel() {
    val weather = MutableLiveData<Weather>()


    fun getWeatherForecast(latLng: Location) {
        viewModelScope.launch {
            val data = weatherForecastUseCase.getWeatherForecast(latLng)

            val inFormat = SimpleDateFormat("yyyy-MM-dd")

            var dayOfWeekDate = inFormat.parse(inFormat.format(Calendar.getInstance().time))

            var counter = 0
            when (data) {
                is ResultWrapper.Success -> {
                    for (serie in data.value.timeSeries) {

                        val dayOfWeekDateAPI = inFormat.parse(serie.validTime)
                        // count the forecast points in the current day
                        if (dayOfWeekDate >= dayOfWeekDateAPI) {
                            Log.d("WeatherViewModel", serie.validTime)
                        }

                        // midnight
                        else {
                            Log.d("WeatherViewModel", serie.validTime)
                            counter++
                            dayOfWeekDate = dayOfWeekDateAPI
                        }


                    }
                    weather.value = data.value
                }
                is ResultWrapper.Error -> Log.d("WeatherDataError", data.error.toString())
            }
        }
    }




}




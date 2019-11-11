package com.benji.weatherswe.currentweather


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.WeatherConstants
import com.benji.weatherswe.utils.WeatherUtils
import com.benji.weatherswe.utils.sharedViewModel
import kotlinx.android.synthetic.main.fragment_current_weather.*


class CurrentWeatherFragment : Fragment() {
    private val TAG = "CurrentWeatherFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLottieWeatherSymbol()
        setTVWeatherSymbol()
        setTime()
        setWeatherCity()
        setDate()
        setDay()
        setAirPressure()
        setThunderProbability()
        setPrecipitation()
        setWind()
        setTemperature()
    }

    fun setLottieWeatherSymbol() {
        lottie_current_weather_symbol.setAnimation(
            WeatherUtils().getWeatherSymbolImage(
                sharedViewModel().hourly.weatherSymbol
            )
        )
    }

    fun setTVWeatherSymbol() {
        tv_current_weather_symbol.text =
            WeatherUtils().getWeatherSymbolText(sharedViewModel().hourly.weatherSymbol)
    }

    fun setTime() {
        tv_current_weather_time.text = sharedViewModel().hourly.validTime
    }

    fun setWeatherCity() {
        tv_current_weather_city.text = sharedViewModel().candidate.address
    }

    fun setDay() {
        tv_current_weather_today.text = sharedViewModel().currentDayForecast.day
    }

    fun setDate(){
        tv_current_weather_date.text = sharedViewModel().currentDayForecast.date
    }

    fun setAirPressure() {
        val hourly = sharedViewModel().hourlyMap
        val airPressure = (hourly[WeatherConstants.PARAMETER_AIR_PRESSURE]
            ?: error("N/A")).values[0] + " hPa"
        tv_current_weather_air_pressure_value.text = airPressure
    }

    fun setThunderProbability() {
        val hourly = sharedViewModel().hourlyMap
        val thunderProbability = (hourly[WeatherConstants.PARAMETER_THUNDER_PROBABILITY]
            ?: error("N/A")).values[0] + " %"
        tv_current_weather_thunder_value.text = thunderProbability
    }

    fun setPrecipitation() {
        val hourly = sharedViewModel().hourlyMap
        val precipitation = (hourly[WeatherConstants.PARAMETER_MAXIMUM_PRECIPITATION_INTENSITY]
            ?: error("N/A")).values[0] + " mm/h"
        tv_current_weather_precipitation_value.text = precipitation
    }

    fun setWind() {
        val hourly = sharedViewModel().hourlyMap
        val wind =
            (hourly[WeatherConstants.PARAMETER_WIND_SPEED] ?: error("N/A")).values[0] + " m/s"
        tv_current_weather_wind_value.text = wind
    }

    fun setTemperature() {
        tv_current_weather_temperature.text = sharedViewModel().hourly.temp
    }

}

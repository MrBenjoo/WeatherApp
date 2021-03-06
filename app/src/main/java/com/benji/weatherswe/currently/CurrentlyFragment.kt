package com.benji.weatherswe.currently

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benji.data.utils.SymbolUtils
import com.benji.domain.constants.ParameterConstants
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.extensions.sharedViewModel
import kotlinx.android.synthetic.main.fragment_currently.*

class CurrentlyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currently, container, false)
    }

    override fun onStart() {
        super.onStart()
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

    private fun setLottieWeatherSymbol() {
        lottie_current_weather_symbol.setAnimation(
            SymbolUtils.getWeatherSymbolLottie(
                sharedViewModel().hourly.weatherSymbol
            )
        )
    }

    private fun setTVWeatherSymbol() {
        tv_current_weather_symbol.text =
            SymbolUtils.getWeatherSymbolDescription(sharedViewModel().hourly.weatherSymbol)
    }

    private fun setTime() {
        tv_current_weather_time.text = sharedViewModel().hourly.validTime
    }

    private fun setWeatherCity() {
        tv_current_weather_city.text = sharedViewModel().candidate.address
    }

    private fun setDay() {
        tv_current_weather_today.text = sharedViewModel().currentDayForecast.day
    }

    private fun setDate() {
        tv_current_weather_date.text = sharedViewModel().currentDayForecast.date
    }

    private fun setAirPressure() {
        val hourly = sharedViewModel().hourlyMap
        val airPressure = (hourly[ParameterConstants.PARAMETER_AIR_PRESSURE]
            ?: error("N/A")).values[0] + " hPa"
        tv_current_weather_air_pressure_value.text = airPressure
    }

    private fun setThunderProbability() {
        val hourly = sharedViewModel().hourlyMap
        val thunderProbability = (hourly[ParameterConstants.PARAMETER_THUNDER_PROBABILITY]
            ?: error("N/A")).values[0] + " %"
        tv_current_weather_thunder_value.text = thunderProbability
    }

    private fun setPrecipitation() {
        val hourly = sharedViewModel().hourlyMap
        val precipitation = (hourly[ParameterConstants.PARAMETER_MAXIMUM_PRECIPITATION_INTENSITY]
            ?: error("N/A")).values[0] + " mm/h"
        tv_current_weather_precipitation_value.text = precipitation
    }

    private fun setWind() {
        val hourly = sharedViewModel().hourlyMap
        val wind =
            (hourly[ParameterConstants.PARAMETER_WIND_SPEED] ?: error("N/A")).values[0] + " m/s"
        tv_current_weather_wind_value.text = wind
    }

    private fun setTemperature() {
        tv_current_weather_temperature.text = sharedViewModel().hourly.temp + "\u00B0"
    }
}

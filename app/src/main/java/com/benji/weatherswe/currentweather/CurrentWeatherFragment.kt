package com.benji.weatherswe.currentweather


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.WeatherConstants
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
        val hourly = sharedViewModel().hourlyMap

        tv_current_weather_city.text = sharedViewModel().candidate.address
        tv_current_weather_date.text = sharedViewModel().todayDate

        tv_current_weather_time.text = sharedViewModel().hourly.validTime

        tv_current_weather_temperature.text =
            (hourly[WeatherConstants.PARAMETER_AIR_TEMPERATURE] ?: error("N/A")).values[0]
        tv_current_weather_wind_value.text =
            (hourly[WeatherConstants.PARAMETER_WIND_SPEED] ?: error("N/A")).values[0]
        tv_current_weather_precipitation_value.text = "N/A" // TODO can be rain, snow etc.
        tv_current_weather_air_pressure_value.text =
            (hourly[WeatherConstants.PARAMETER_AIR_PRESSURE] ?: error("N/A")).values[0]

    }
}

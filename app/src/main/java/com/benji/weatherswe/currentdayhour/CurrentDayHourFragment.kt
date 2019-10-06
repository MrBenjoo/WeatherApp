package com.benji.weatherswe.currentdayhour


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.WeatherConstants
import com.benji.weatherswe.utils.sharedViewModel
import kotlinx.android.synthetic.main.fragment_current_day_hour.*


class CurrentDayHourFragment : Fragment() {
    private val TAG = "CurrentDayHourFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_day_hour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hourly = sharedViewModel().hourlyMap

        tv_current_day_hour_city.text = sharedViewModel().candidate.value?.address
        tv_current_day_hour_time.text = sharedViewModel().hourly.validTime

        tv_current_day_hour_temperature.text = (hourly[WeatherConstants.PARAMETER_AIR_TEMPERATURE] ?: error("N/A")).values[0]
        tv_current_day_hour_wind_value.text = (hourly[WeatherConstants.PARAMETER_WIND_SPEED] ?: error("N/A")).values[0]
        tv_current_day_hour_precipitation_value.text = "N/A" // TODO can be rain, snow etc.
        tv_current_day_hour_air_pressure_value.text = (hourly[WeatherConstants.PARAMETER_AIR_PRESSURE] ?: error("N/A")).values[0]

    }
}

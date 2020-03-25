package com.benji.weatherswe.hourly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.extensions.activitySharedViewModel
import com.benji.weatherswe.utils.extensions.navigate
import com.benji.weatherswe.utils.forecast.SymbolUtils
import kotlinx.android.synthetic.main.fragment_hour_weather.*

class HourlyFragment : Fragment() {
    private lateinit var adapter: HourlyAdapter

    private val listClickObserver = Observer<Hourly> { rowData ->
        activitySharedViewModel().hourly = rowData
        activitySharedViewModel().hourlyMap = rowData.parameters.associateBy { it.name }
        navigate(R.id.action_hourWeatherFragment_to_currentWeatherFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_hour_weather, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_current_day)
        recyclerView.setHasFixedSize(true)
        adapter = HourlyAdapter(emptyList())
        recyclerView.adapter = adapter
        return root
    }

    override fun onStart() {
        super.onStart()
        val forecast = activitySharedViewModel().currentDayForecast.listOfHourlyData
        adapter.updateHourlyForecast(forecast)
        adapter.rowData.observe(this, listClickObserver)
        tv_hour_weather_day.text = activitySharedViewModel().currentDayForecast.day
        tv_hour_weather_date.text = activitySharedViewModel().currentDayForecast.date
        tv_hour_weather_city.text = activitySharedViewModel().candidate.address
        tv_hour_weather_temp.text = activitySharedViewModel().currentDayForecast.temperature + "\u00B0"
        tv_hour_weather_description.text = SymbolUtils.getWeatherSymbolDescription(activitySharedViewModel().currentDayForecast.weatherSymbol)
    }
}

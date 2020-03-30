package com.benji.weatherswe.hourly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.benji.data.utils.SymbolUtils
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.extensions.navigate
import com.benji.weatherswe.utils.extensions.sharedViewModel
import kotlinx.android.synthetic.main.fragment_hourly.*

class HourlyFragment : Fragment() {
    private var adapter: HourlyAdapter?= null

    private val listClickObserver = Observer<Hourly> { rowData ->
        sharedViewModel().hourly = rowData
        sharedViewModel().hourlyMap = rowData.parameters.associateBy { it.name }
        navigate(R.id.action_hourWeatherFragment_to_currentWeatherFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_hourly, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_current_day)
        recyclerView.setHasFixedSize(true)
        adapter = HourlyAdapter(emptyList())
        recyclerView.adapter = adapter
        return root
    }

    override fun onStart() {
        super.onStart()
        val forecast = sharedViewModel().currentDayForecast.listOfHourlyData
        adapter?.updateHourlyForecast(forecast)
        adapter?.rowData?.observe(this, listClickObserver)
        tv_hour_weather_day.text = sharedViewModel().currentDayForecast.day
        tv_hour_weather_date.text = sharedViewModel().currentDayForecast.date
        tv_hour_weather_city.text = sharedViewModel().candidate.address
        tv_hour_weather_temp.text = sharedViewModel().currentDayForecast.temperature + "\u00B0"
        tv_hour_weather_description.text = SymbolUtils.getWeatherSymbolDescription(sharedViewModel().currentDayForecast.weatherSymbol)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}

package com.benji.weatherswe.hourweather


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.Parameter
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.navigate
import com.benji.weatherswe.utils.sharedViewModel
import kotlinx.android.synthetic.main.fragment_hour_weather.*


class HourWeatherFragment : Fragment() {
    private val TAG = "HourWeatherFragment"

    private val listClickObserver = Observer<Hourly> { rowData ->
        sharedViewModel().hourly = rowData
        sharedViewModel().hourlyMap = rowData.parameters.associate {
            Pair(
                it.name,
                Parameter(it.name, it.levelType, it.level, it.unit, it.values)
            )
        }
        navigate(R.id.action_hourWeatherFragment_to_currentWeatherFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hour_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateRecyclerView()
    }

    private fun populateRecyclerView() {
        val forecast = sharedViewModel().currentDayForecast.listOfHourlyData
        recyclerview_current_day.setHasFixedSize(true)
        val adapter = HourWeatherAdapter(forecast)
        adapter.rowData.observe(this, listClickObserver)
        recyclerview_current_day.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_hour_weather_date.text = sharedViewModel().todayDate
        tv_hour_weather_city.text = sharedViewModel().candidate.address
    }
}

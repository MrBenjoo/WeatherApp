package com.benji.weatherswe.dayweather

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.weatherswe.R
import com.benji.weatherswe.dayweather.servicelocator.DayWeatherServiceLocator
import com.benji.weatherswe.utils.DateUtils
import com.benji.weatherswe.utils.mainActivity
import com.benji.weatherswe.utils.navigate
import com.benji.weatherswe.utils.sharedViewModel
import kotlinx.android.synthetic.main.fragment_day_weather.*


class DayWeatherFragment : Fragment() {
    private lateinit var viewModelDay: DayWeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter
    private val TAG = "DayWeatherFragment"


    private val weatherObserver = Observer<List<DayForecast>> { weekdayForecast ->
        weatherAdapter.setList(weekdayForecast)
    }

    private val listClickObserver = Observer<RowData> { rowData ->
        Log.d(TAG, "sharedViewModel().toString() = " + sharedViewModel().toString())
        sharedViewModel().currentDayForecast = rowData.dayForecast
        sharedViewModel().todayDate = DateUtils().getTodayDate()
        navigate(R.id.action_dayWeatherFragment_to_hourWeatherFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) // required in order to show the items in the menu
        return inflater.inflate(R.layout.fragment_day_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_weather_day_clock.text = DateUtils().getDayAndClock()
        tv_day_weather_city.text  = sharedViewModel().candidate.address
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //setupToolbar(toolbar_weather, null)
        recyclerview_day_weather.setHasFixedSize(true)
        weatherAdapter = WeatherAdapter(emptyList())
        weatherAdapter.rowData.observe(this, listClickObserver)
        recyclerview_day_weather.adapter = weatherAdapter


        viewModelDay =
            DayWeatherServiceLocator.provideWeatherViewModel(
                this,
                mainActivity().applicationContext
            )

        viewModelDay.listOfTenDayForecast.observe(this, weatherObserver)

        viewModelDay.getWeatherForecast(sharedViewModel().candidate)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_weather_view, menu)
    }


}

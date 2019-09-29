package com.benji.weatherswe.weather

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.domain.domainmodel.weather.TenDayForecast
import com.benji.weatherswe.R
import com.benji.weatherswe.weather.servicelocator.WeatherServiceLocator
import com.benji.weatherswe.utils.setupToolbar
import com.benji.weatherswe.utils.sharedViewModel
import com.benji.weatherswe.utils.sixDecimals
import kotlinx.android.synthetic.main.weather_fragment.*


class WeatherFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter


    private val weatherObserver = Observer<List<TenDayForecast>> { weekdayForecast ->
        weatherAdapter.setList(weekdayForecast)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) // required in order to show the items in the menu
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupToolbar(toolbar_weather, null)
        recyclerview_weather.setHasFixedSize(true)
        weatherAdapter = WeatherAdapter(emptyList())
        recyclerview_weather.adapter = weatherAdapter

        viewModel = WeatherServiceLocator.provideWeatherViewModel(this)

        viewModel.listOfTenDayForecast.observe(this, weatherObserver)

        viewModel.getWeatherForecast(sharedViewModel()
            .candidate
            .value!!
            .location
            .sixDecimals())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_weather_view, menu)
    }


}

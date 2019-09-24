package com.benji.weatherswe.weather

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.domain.domainmodel.weather.Weather
import com.benji.weatherswe.R
import com.benji.weatherswe.weather.servicelocator.WeatherServiceLocator
import com.benji.weatherswe.utils.setupToolbar
import com.benji.weatherswe.utils.sharedViewModel
import com.benji.weatherswe.utils.sixDecimals
import kotlinx.android.synthetic.main.weather_fragment.*


class WeatherFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel


    private val weatherObserver = Observer<Weather> {weather ->

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
        recyclerview_weather.adapter = WeatherAdapter(emptyList())

        viewModel = WeatherServiceLocator.provideWeatherViewModel(this)

        viewModel.weather.observe(this, weatherObserver)

        viewModel.getWeatherForecast(sharedViewModel()
            .candidate
            .value!!
            .location
            .sixDecimals())
    }

    /*
    private fun fakeListOnlyToTest(): List<WeekdayForecast>? {

        val sunnyDrawable = ContextCompat.getDrawable(mainActivity().applicationContext, R.drawable.ic_sunny)!!
        val dayForecastModel =
            WeekdayForecast("Idag", sunnyDrawable, "18")

        val list = arrayListOf<WeekdayForecast>()

        for(i in 0..5) {
            list.add(dayForecastModel)
        }

        return list
    }
    */


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_weather_view, menu)
    }


}

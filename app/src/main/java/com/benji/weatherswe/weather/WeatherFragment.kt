package com.benji.weatherswe.weather

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.*
import com.benji.weatherswe.weather.servicelocator.WeatherServiceLocator
import kotlinx.android.synthetic.main.weather_fragment.*


class WeatherFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherAdapter: WeatherAdapter


    private val weatherObserver = Observer<List<DayForecast>> { weekdayForecast ->
        weatherAdapter.setList(weekdayForecast)
    }

    private val listClickObserver = Observer<RowData> { rowData ->
        sharedViewModel().currentDayForecast = rowData.dayForecast
        navigate(R.id.action_mainPageFragment_to_currentDayFragment)
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

        //setupToolbar(toolbar_weather, null)
        recyclerview_weather.setHasFixedSize(true)
        weatherAdapter = WeatherAdapter(emptyList())
        weatherAdapter.rowData.observe(this, listClickObserver)
        recyclerview_weather.adapter = weatherAdapter


        viewModel =
            WeatherServiceLocator.provideWeatherViewModel(this, mainActivity().applicationContext)

        viewModel.listOfTenDayForecast.observe(this, weatherObserver)

        viewModel.getWeatherForecast(
            sharedViewModel()
                .candidate
                .value!!
                .location
                .sixDecimals()
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_weather_view, menu)
    }


}

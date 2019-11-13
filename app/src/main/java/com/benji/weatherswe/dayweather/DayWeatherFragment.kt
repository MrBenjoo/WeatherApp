package com.benji.weatherswe.dayweather

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.domain.domainmodel.State
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.weatherswe.R
import com.benji.weatherswe.dayweather.servicelocator.DayWeatherServiceLocator
import com.benji.weatherswe.utils.*
import kotlinx.android.synthetic.main.fragment_day_weather.*
import kotlinx.android.synthetic.main.location_weather_fragment.*


class DayWeatherFragment : Fragment() {
    private lateinit var viewModel: DayWeatherViewModel
    private lateinit var dayWeatherAdapter: DayWeatherAdapter
    private val TAG = "DayWeatherFragment"


    private val weatherObserver = Observer<List<DayForecast>> { weekdayForecast ->
        dayWeatherAdapter.setList(weekdayForecast)
        tv_day_weather_temp.text = weekdayForecast[0].temperature
        tv_day_weather_description.text = WeatherUtils().getWeatherSymbolText(weekdayForecast[0].weatherSymbol)
        img_day_weather_symbol.setAnimation(WeatherUtils().getWeatherSymbolImage(weekdayForecast[0].weatherSymbol))
        img_day_weather_symbol.playAnimation()

        for(i in 0..4) {
            when(i) {
                0 -> {
                    lottie_day_weather_1.setAnimation(WeatherUtils().getWeatherSymbolImage(weekdayForecast[0].listOfHourlyData[i].weatherSymbol))
                    lottie_day_weather_1.playAnimation()
                    tv_day_weather_lottie_temp_1.text = weekdayForecast[0].listOfHourlyData[i].temp
                    tv_day_weather_lottie_time_1.text = weekdayForecast[0].listOfHourlyData[i].validTime
                }
                1 -> {
                    lottie_day_weather_2.setAnimation(WeatherUtils().getWeatherSymbolImage(weekdayForecast[0].listOfHourlyData[i].weatherSymbol))
                    lottie_day_weather_2.playAnimation()
                    tv_day_weather_lottie_temp_2.text = weekdayForecast[0].listOfHourlyData[i].temp
                    tv_day_weather_lottie_time_2.text = weekdayForecast[0].listOfHourlyData[i].validTime
                }

                2 -> {
                    lottie_day_weather_3.setAnimation(WeatherUtils().getWeatherSymbolImage(weekdayForecast[0].listOfHourlyData[i].weatherSymbol))
                    lottie_day_weather_3.playAnimation()
                    tv_day_weather_lottie_temp_3.text = weekdayForecast[0].listOfHourlyData[i].temp
                    tv_day_weather_lottie_time_3.text = weekdayForecast[0].listOfHourlyData[i].validTime
                }

                3 -> {
                    lottie_day_weather_4.setAnimation(WeatherUtils().getWeatherSymbolImage(weekdayForecast[0].listOfHourlyData[i].weatherSymbol))
                    lottie_day_weather_4.playAnimation()
                    tv_day_weather_lottie_temp_4.text = weekdayForecast[0].listOfHourlyData[i].temp
                    tv_day_weather_lottie_time_4.text = weekdayForecast[0].listOfHourlyData[i].validTime
                }

                4 -> {
                    lottie_day_weather_5.setAnimation(WeatherUtils().getWeatherSymbolImage(weekdayForecast[0].listOfHourlyData[i].weatherSymbol))
                    lottie_day_weather_5.playAnimation()
                    tv_day_weather_lottie_temp_5.text = weekdayForecast[0].listOfHourlyData[i].temp
                    tv_day_weather_lottie_time_5.text = weekdayForecast[0].listOfHourlyData[i].validTime
                }
            }

        }
    }

    private fun handleState(state: State?) {
        when (state) {
            State.InFlight -> {
                loading_day_bar.visibility = View.VISIBLE
            }
            State.Complete, State.Idle, State.Gone -> {
                loading_day_bar.visibility = View.GONE
            }
            else -> {
                loading_day_bar.visibility = View.VISIBLE
            }
        }
    }

    private fun observeState() {
        viewModel.state.observe(
            this,
            Observer { state ->
                handleState(state)
            }
        )
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
        dayWeatherAdapter = DayWeatherAdapter(emptyList())
        dayWeatherAdapter.rowData.observe(this, listClickObserver)
        recyclerview_day_weather.adapter = dayWeatherAdapter


        viewModel =
            DayWeatherServiceLocator.provideWeatherViewModel(
                this,
                mainActivity().applicationContext
            )

        observeState()

        viewModel.listOfTenDayForecast.observe(this, weatherObserver)

        viewModel.getWeatherForecast(sharedViewModel().candidate)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_weather_view, menu)
    }


}

package com.benji.weatherswe.dayweather

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.benji.domain.domainmodel.State
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.weatherswe.R
import com.benji.weatherswe.dayweather.servicelocator.DayWeatherServiceLocator
import com.benji.weatherswe.utils.extensions.*
import com.benji.weatherswe.utils.forecast.DateUtils
import com.benji.weatherswe.utils.forecast.SymbolUtils
import kotlinx.android.synthetic.main.fragment_day_weather.*


class DayWeatherFragment : Fragment() {

    private lateinit var viewModel: DayWeatherViewModel
    private lateinit var dayWeatherAdapter: DayWeatherAdapter
    private val TAG = "DayWeatherFragment"

    private val errorObserver = Observer<String> { errorMessage ->
        showTopText(errorMessage)
    }

    private val weatherObserver = Observer<List<DayForecast>> { weekdayForecast ->
        val candidateJson = activitySharedViewModel().candidate.toJson()
        prefsStoreCandidate(candidateJson)

        val weatherSymbol = weekdayForecast[0].weatherSymbol
        val weatherSymbolDescription = SymbolUtils.getWeatherSymbolDescription(weatherSymbol)
        val weatherSymbolLottie = SymbolUtils.getWeatherSymbolLottie(weatherSymbol)

        tv_day_weather_city.text = activitySharedViewModel().candidate.address
        tv_day_weather_description.text = weatherSymbolDescription
        img_day_weather_symbol.setAnimation(weatherSymbolLottie)
        img_day_weather_symbol.playAnimation()
        tv_day_weather_temp.text = weekdayForecast[0].temperature + "\u00B0"

        dayWeatherAdapter.setList(weekdayForecast)
    }


    private val setWeatherFirstHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_1.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_1.playAnimation()
        tv_day_weather_lottie_temp_1.text = hourlyOverview.temp + "\u00B0"
        tv_day_weather_lottie_time_1.text = hourlyOverview.validTime
    }

    private val setWeatherSecondHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_2.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_2.playAnimation()
        tv_day_weather_lottie_temp_2.text = hourlyOverview.temp + "\u00B0"
        tv_day_weather_lottie_time_2.text = hourlyOverview.validTime
    }

    private val setWeatherThirdHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_3.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_3.playAnimation()
        tv_day_weather_lottie_temp_3.text = hourlyOverview.temp + "\u00B0"
        tv_day_weather_lottie_time_3.text = hourlyOverview.validTime
    }

    private val setWeatherFourthHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_4.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_4.playAnimation()
        tv_day_weather_lottie_temp_4.text = hourlyOverview.temp + "\u00B0"
        tv_day_weather_lottie_time_4.text = hourlyOverview.validTime
    }

    private val setWeatherFifthHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_5.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_5.playAnimation()
        tv_day_weather_lottie_temp_5.text = hourlyOverview.temp + "\u00B0"
        tv_day_weather_lottie_time_5.text = hourlyOverview.validTime
    }

    private val listClickObserver = Observer<RowData> { rowData ->
        activitySharedViewModel().currentDayForecast = rowData.dayForecast
        activitySharedViewModel().todayDate = DateUtils().getTodayDate()
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
        recyclerview_day_weather.setHasFixedSize(true)
        setupToolbar(toolbar_day_weather)
        dayWeatherAdapter = DayWeatherAdapter(emptyList())
        tv_weather_day_time.text = DateUtils().getDayAndClock()
        tv_day_weather_city.text = activitySharedViewModel().candidate.address
        hideKeyBoard(view)
        viewModel = DayWeatherServiceLocator.provideViewModel(this)
    }

    /**
     * remove reference (activity --> toolbar) to no longer visible toolbar to avoid memory leak
     */
    override fun onDestroyView() {
        super.onDestroyView()
        mainActivity().setSupportActionBar(null)
    }

    override fun onStart() {
        super.onStart()
        dayWeatherAdapter.rowData.observe(viewLifecycleOwner, listClickObserver)
        recyclerview_day_weather.adapter = dayWeatherAdapter

        viewModel.listOfDayForecast.observe(viewLifecycleOwner, weatherObserver)
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
        viewModel.forecastFirstHour.observe(viewLifecycleOwner, setWeatherFirstHour)
        viewModel.forecastSecondHour.observe(viewLifecycleOwner, setWeatherSecondHour)
        viewModel.forecastThirdHour.observe(viewLifecycleOwner, setWeatherThirdHour)
        viewModel.forecastFourthHour.observe(viewLifecycleOwner, setWeatherFourthHour)
        viewModel.forecastFifthHour.observe(viewLifecycleOwner, setWeatherFifthHour)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)

        viewModel.getForecast(activitySharedViewModel().candidate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    private val stateObserver = Observer<State> { state ->
        when (state) {
            State.InFlight -> {
                loading_day_bar.visibility = View.GONE
            }
            State.Complete, State.Idle, State.Gone -> {
                loading_day_bar.visibility = View.GONE
            }
            else -> {
                loading_day_bar.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_weather_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_item_search -> {
                //navigate(R.id.action_dayWeatherFragment_to_searchCityFragment)
                val bundle = bundleOf("navigatedFrom" to "dayWeatherFragment")
                findNavController().navigate(R.id.action_dayWeatherFragment_to_searchCityFragment, bundle)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}

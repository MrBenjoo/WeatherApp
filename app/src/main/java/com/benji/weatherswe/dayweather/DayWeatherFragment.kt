package com.benji.weatherswe.dayweather

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.domain.domainmodel.State
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.weatherswe.R
import com.benji.weatherswe.dayweather.servicelocator.DayWeatherServiceLocator
import com.benji.weatherswe.utils.*
import kotlinx.android.synthetic.main.fragment_day_weather.*


class DayWeatherFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var viewModel: DayWeatherViewModel
    private lateinit var dayWeatherAdapter: DayWeatherAdapter
    private lateinit var searchMenuItem: MenuItem
    private val TAG = "DayWeatherFragment"

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.onSearchCity(newText)
        return true
    }

    private val candidateObserver = Observer<Candidate> { candidate ->
        sharedViewModel().candidate = candidate
        viewModel.getWeatherForecast(candidate)
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.apply {
            setOnQueryTextListener(this@DayWeatherFragment)
            queryHint = string(R.string.search_hint)
        }
        val searchAutoComplete =
            searchView.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
        searchAutoComplete.setHintTextColor(getColor(R.color.colorPrimary))

        searchAutoComplete.setOnItemClickListener { _, _, index, _ ->
            searchMenuItem.collapseActionView()
            viewModel.onSuggestionClicked(index)
        }

        initArrayAdapter()
        searchAutoComplete.setAdapter(arrayAdapter)
    }

    private val citySuggestionsObserver = Observer<List<String>> { suggestions ->
        arrayAdapter.clear()
        arrayAdapter.addAll(suggestions)
        arrayAdapter.notifyDataSetChanged()
    }

    private fun initArrayAdapter() {
        arrayAdapter = ArrayAdapter(
            mainActivity().applicationContext,
            android.R.layout.simple_dropdown_item_1line,
            emptyList<String>()
        )
    }

    private val weatherObserver = Observer<List<DayForecast>> { weekdayForecast ->
        val candidateJson = sharedViewModel().candidate.toJson()
        prefsStoreCandidate(candidateJson)

        val weatherSymbol = weekdayForecast[0].weatherSymbol
        val weatherSymbolDescription = WeatherSymbolUtils.getWeatherSymbolDescription(weatherSymbol)
        val weatherSymbolLottie = WeatherSymbolUtils.getWeatherSymbolLottie(weatherSymbol)

        tv_day_weather_city.text = sharedViewModel().candidate.address
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
        recyclerview_day_weather.setHasFixedSize(true)
        dayWeatherAdapter = DayWeatherAdapter(emptyList())
        setupToolbar(toolbar_day_weather)
        tv_weather_day_time.text = DateUtils().getDayAndClock()
        tv_day_weather_city.text = sharedViewModel().candidate.address
        hideKeyBoard(view)
        viewModel = DayWeatherServiceLocator.provideWeatherViewModel(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dayWeatherAdapter.rowData.observe(viewLifecycleOwner, listClickObserver)
        recyclerview_day_weather.adapter = dayWeatherAdapter

        viewModel.listOfTenDayForecast.observe(viewLifecycleOwner, weatherObserver)
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
        viewModel.candidate.observe(viewLifecycleOwner, candidateObserver)
        viewModel.forecastFirstHour.observe(viewLifecycleOwner, setWeatherFirstHour)
        viewModel.forecastSecondHour.observe(viewLifecycleOwner, setWeatherSecondHour)
        viewModel.forecastThirdHour.observe(viewLifecycleOwner, setWeatherThirdHour)
        viewModel.forecastFourthHour.observe(viewLifecycleOwner, setWeatherFourthHour)
        viewModel.forecastFifthHour.observe(viewLifecycleOwner, setWeatherFifthHour)
        viewModel.citySuggestions.observe(viewLifecycleOwner, citySuggestionsObserver)

        viewModel.getWeatherForecast(sharedViewModel().candidate)
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

        searchMenuItem = menu.findItem(R.id.action_item_search)
        setupSearchView(searchMenuItem.actionView as SearchView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_item_favorite) {
            viewModel.onMenuFavoriteClick()
            true

        } else super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }


}

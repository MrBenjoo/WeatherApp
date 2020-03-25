package com.benji.weatherswe.daily

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.weatherswe.R
import com.benji.weatherswe.daily.servicelocator.DailyServiceLocator.provideViewModel
import com.benji.weatherswe.utils.extensions.*
import com.benji.weatherswe.utils.forecast.DateUtils
import com.benji.weatherswe.utils.forecast.SymbolUtils
import kotlinx.android.synthetic.main.fragment_day_weather.*

class DailyFragment : Fragment() {

    private val viewModel: DailyViewModel by lazy { provideViewModel(this) }
    private var dayWeatherAdapter: DayWeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) { activity?.finish() }
    }

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
        dayWeatherAdapter?.setList(weekdayForecast)
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
        val root = inflater.inflate(R.layout.fragment_day_weather, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_day_weather)
        val toolbar = root.findViewById<Toolbar>(R.id.toolbar_day_weather)
        recyclerView.setHasFixedSize(true)
        setupToolbar(toolbar)
        dayWeatherAdapter = DayWeatherAdapter(emptyList())
        recyclerView.adapter = dayWeatherAdapter
        return root
    }

    override fun onStart() {
        super.onStart()
        view?.let { hideKeyBoard(it) }

        tv_weather_day_time.text = DateUtils().getDay()

        tv_day_weather_city.text = activitySharedViewModel().candidate.address

        dayWeatherAdapter?.rowData?.observe(viewLifecycleOwner, listClickObserver)

        viewModel.listOfDayForecast.observe(viewLifecycleOwner, weatherObserver)
        viewModel.forecastFirstHour.observe(viewLifecycleOwner, setWeatherFirstHour)
        viewModel.forecastSecondHour.observe(viewLifecycleOwner, setWeatherSecondHour)
        viewModel.forecastThirdHour.observe(viewLifecycleOwner, setWeatherThirdHour)
        viewModel.forecastFourthHour.observe(viewLifecycleOwner, setWeatherFourthHour)
        viewModel.forecastFifthHour.observe(viewLifecycleOwner, setWeatherFifthHour)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)

        viewModel.getForecast(activitySharedViewModel().candidate)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_weather_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_item_search -> {
                val bundle = bundleOf("navigatedFrom" to DailyFragment::class.java.simpleName)
                findNavController().navigate(
                    R.id.action_dayWeatherFragment_to_searchCityFragment,
                    bundle
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dayWeatherAdapter = null
    }
}

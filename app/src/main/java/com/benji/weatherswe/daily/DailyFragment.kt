package com.benji.weatherswe.daily

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.benji.data.utils.DateUtils
import com.benji.domain.domainmodel.State
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.domain.domainmodel.weather.HourlyOverview
import com.benji.weatherswe.R
import com.benji.weatherswe.daily.servicelocator.DailyServiceLocator.provideViewModel
import com.benji.weatherswe.utils.animation.WeatherSymbolAnimator
import com.benji.weatherswe.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_daily.*

class DailyFragment : Fragment() {

    private val viewModel: DailyViewModel by lazy { provideViewModel(this) }
    private var dayWeatherAdapter: DayWeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Finishes the activity when back button is pressed.
        initBackPressFinishActivity()

        // Store Candidate object in SharedPreferences because we want to navigate to this fragment
        // instead of SearchFragment if we have already searched for a city. In SearchFragment we
        // check if we have stored a candidate, if yes then we immediately navigate to this fragment.
        prefsStoreCandidate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) // required in order to show the items in the menu
        val root = inflater.inflate(R.layout.fragment_daily, container, false)
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
        hideKeyBoard()
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
        viewModel.listOfDayForecast.observe(viewLifecycleOwner, dayForecastObserver)
        viewModel.currentWeatherOverview.observe(viewLifecycleOwner, setCurrentWeatherOverview)
        dayWeatherAdapter?.rowData?.observe(viewLifecycleOwner, onClickDayForecastObserver)
        viewModel.forecastFirstHour.observe(viewLifecycleOwner, setWeatherFirstHour)
        viewModel.forecastSecondHour.observe(viewLifecycleOwner, setWeatherSecondHour)
        viewModel.forecastThirdHour.observe(viewLifecycleOwner, setWeatherThirdHour)
        viewModel.forecastFourthHour.observe(viewLifecycleOwner, setWeatherFourthHour)
        viewModel.forecastFifthHour.observe(viewLifecycleOwner, setWeatherFifthHour)
        viewModel.state.observe(viewLifecycleOwner, progressBarObserver)
        viewModel.getForecastData(sharedViewModel().candidate)
    }

    private val errorObserver = Observer<String> { message ->
        showTopText(message)
    }

    private val dayForecastObserver = Observer<List<DayForecast>> { listOfDayForecast ->
        dayWeatherAdapter?.setList(listOfDayForecast)
    }

    private val setCurrentWeatherOverview =
        Observer<CurrentWeatherOverview> { currentWeatherOverview ->
            tv_weather_day_time.text = DateUtils.getDay()
            tv_day_weather_city.text = sharedViewModel().candidate.address
            tv_day_weather_description.text = currentWeatherOverview.symbolDescription
            tv_day_weather_temp.text = getString(
                R.string.forecast_hourly_overview,
                currentWeatherOverview.temperature,
                "\u00B0"
            )
        }

    private val onClickDayForecastObserver = Observer<RowData> { rowData ->
        sharedViewModel().currentDayForecast = rowData.dayForecast
        sharedViewModel().todayDate = DateUtils.getTodayDate()
        navigate(R.id.action_dayWeatherFragment_to_hourWeatherFragment)
    }

    private val setWeatherFirstHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_1.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_1.playAnimation()
        tv_day_weather_lottie_temp_1.text =
            getString(R.string.forecast_hourly_overview, hourlyOverview.temp, "\u00B0")
        tv_day_weather_lottie_time_1.text = hourlyOverview.validTime
    }

    private val setWeatherSecondHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_2.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_2.playAnimation()
        tv_day_weather_lottie_temp_2.text =
            getString(R.string.forecast_hourly_overview, hourlyOverview.temp, "\u00B0")
        tv_day_weather_lottie_time_2.text = hourlyOverview.validTime
    }

    private val setWeatherThirdHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_3.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_3.playAnimation()
        tv_day_weather_lottie_temp_3.text =
            getString(R.string.forecast_hourly_overview, hourlyOverview.temp, "\u00B0")
        tv_day_weather_lottie_time_3.text = hourlyOverview.validTime
    }

    private val setWeatherFourthHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_4.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_4.playAnimation()
        tv_day_weather_lottie_temp_4.text =
            getString(R.string.forecast_hourly_overview, hourlyOverview.temp, "\u00B0")
        tv_day_weather_lottie_time_4.text = hourlyOverview.validTime
    }

    private val setWeatherFifthHour = Observer<HourlyOverview> { hourlyOverview ->
        lottie_day_weather_5.setAnimation(hourlyOverview.weatherSymbol)
        lottie_day_weather_5.playAnimation()
        tv_day_weather_lottie_temp_5.text =
            getString(R.string.forecast_hourly_overview, hourlyOverview.temp, "\u00B0")
        tv_day_weather_lottie_time_5.text = hourlyOverview.validTime
    }

    private val progressBarObserver = Observer<State> { state ->
        when (state) {
            is State.Loading -> showView(progress_loader)
            is State.Gone -> hideView(progress_loader)
            is State.Complete -> {
                hideView(progress_loader)
                WeatherSymbolAnimator(view!!).also { it.beginAnimation() }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_weather_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // When we are navigation to SearchFragment, a bundle containing a String is passed.
            // The String value is used in the SearchFragment's onStart method to determine if
            // we have navigated from this fragment (in that case, the fragment will allow us to
            // search for a city) or if the application has been restarted (in that case,
            // the fragment will check if a candidate object has been stored in the
            // SharedPreferences, if yes then navigate to this fragment otherwise let the user
            // search for a city.
            R.id.action_item_search -> navigateToSearchFragment()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dayWeatherAdapter = null
    }
}

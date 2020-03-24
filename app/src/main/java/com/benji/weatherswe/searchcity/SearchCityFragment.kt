package com.benji.weatherswe.searchcity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.benji.device.location.LocationEvent
import com.benji.device.network.Event
import com.benji.device.network.NetworkEvents
import com.benji.domain.constants.Constants
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.weatherswe.R
import com.benji.weatherswe.searchcity.servicelocator.SearchCityServiceLocator.provideAutoCompleteCityAdapter
import com.benji.weatherswe.searchcity.servicelocator.SearchCityServiceLocator.provideViewModel
import com.benji.weatherswe.utils.AutoCompleteCityAdapter
import com.benji.weatherswe.utils.extensions.*
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.search_city_fragment.*


class SearchCityFragment : Fragment(), TextWatcher {

    private val viewModel: SearchCityViewModel by lazy { provideViewModel(this) }


    private val arrayAdapter: AutoCompleteCityAdapter by lazy { provideAutoCompleteCityAdapter(this) }


    override fun afterTextChanged(text: Editable?) {
        viewModel.onEvent(SearchCityEvent.OnQueryInput(text.toString()))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAutoCompleteTextView()
        initObservers()
    }

    private fun initAutoCompleteTextView() {
        with(autoCompleteTv_search_city) {
            setAdapter(arrayAdapter)
            addTextChangedListener(this@SearchCityFragment)
            setOnItemClickListener { _, _, index, _ ->
                viewModel.onEvent(
                    SearchCityEvent.OnSuggestionClick(
                        index
                    )
                )
            }

            onClearListener = object : ClearableAutoCompleteTextView.OnClearListener {
                override fun onClear() {
                    when (text.isNotEmpty()) {
                        true -> setText("")
                        false -> {
                            hideKeyBoard(this@with)
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.citySuggestions.observe(viewLifecycleOwner, citySuggestionsObserver)
        viewModel.candidate.observe(viewLifecycleOwner, candidateObserver)
    }

    private val citySuggestionsObserver = Observer<List<String>> { suggestions ->
        arrayAdapter.updateList(suggestions)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when(arguments?.getString("navigatedFrom")) {
            "start" -> {
                Log.d("SearchCityFragment", "navigatedFrom: start")
                when (val candidate = prefsLoadLatestCandidate()) {
                    Constants.PREF_DEFAULT_VALUE -> noCitySearched()
                    else -> loadLatestSearchedCity(candidate)
                }
            }
            "dayWeatherFragment" -> Log.d("SearchCityFragment", "navigatedFrom: dayWeatherFragment")
        }
    }

    private fun loadLatestSearchedCity(candidate: String) {
        val moshi = Moshi.Builder().build()
        activitySharedViewModel().candidate =
            moshi.adapter(Candidate::class.java).fromJson(candidate)!!
        navigate(R.id.action_searchCityFragment_to_dayWeatherFragment)
    }

    private fun noCitySearched() {
        viewModel.citySuggestions.observe(viewLifecycleOwner, citySuggestionsObserver)

        viewModel.candidate.observe(viewLifecycleOwner, candidateObserver)


        LocationEvent.observe(viewLifecycleOwner, locationObserver)
        NetworkEvents.observe(viewLifecycleOwner, networkEventObserver)
    }

    private val networkEventObserver = Observer<Event> { event ->
        when (event) {
            is Event.ConnectivityLost -> Unit // showView(tv_location_connection)
            is Event.ConnectivityAvailable -> {
                 // hideView(tv_location_connection)
                showText(getString(R.string.connection_success))
            }
        }
    }

    private val locationObserver = Observer<Location> { location ->
        viewModel.onEvent(SearchCityEvent.OnLocationReceived(location))
    }

    private val candidateObserver = Observer<Candidate> { candidate ->
        activitySharedViewModel().candidate = candidate
        navigate(R.id.action_searchCityFragment_to_dayWeatherFragment)
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_city_fragment, container, false)
    }
}

enum class NavigationID() {
    DayWeatherFragment,
    StartFragment
}

package com.benji.weatherswe.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.device.location.LocationEvent
import com.benji.device.network.Event
import com.benji.device.network.NetworkEvents
import com.benji.domain.constants.Constants.PREF_DEFAULT_VALUE
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.weatherswe.R
import com.benji.weatherswe.daily.DailyFragment
import com.benji.weatherswe.search.servicelocator.SearchServiceLocator.provideViewModel
import com.benji.weatherswe.utils.extensions.*
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), TextWatcher {
    private val viewModel: SearchViewModel by lazy { provideViewModel(this) }
    private var arrayAdapter: AutoCompleteCityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val autoCompleteTv =
            root.findViewById<SearchAutoComplete>(R.id.autoCompleteTv_search_city)
        arrayAdapter = AutoCompleteCityAdapter(
            mainActivity().baseContext,
            emptyList()
        )
        autoCompleteTv.setAdapter(arrayAdapter)
        return root
    }

    override fun afterTextChanged(text: Editable?) {
        viewModel.onEvent(SearchEvent.OnQueryInput(text.toString()))
    }

    private val locationObserver = Observer<Location> { location ->
        viewModel.onEvent(SearchEvent.OnLocationReceived(location))
    }

    private val candidateObserver = Observer<Candidate> { candidate ->
        hideKeyBoard()
        sharedViewModel().candidate = candidate
        navigate(R.id.action_searchCityFragment_to_dayWeatherFragment)
    }

    private val citySuggestionsObserver = Observer<List<String>> { suggestions ->
        arrayAdapter?.updateList(suggestions)
    }

    private val networkEventObserver = Observer<Event> { event ->
        when (event) {
            is Event.ConnectivityLost -> Unit
            is Event.ConnectivityAvailable -> showText(getString(R.string.connection_success))
        }
    }

    private fun initAutoCompleteTextView() {
        with(autoCompleteTv_search_city) {
            addTextChangedListener(this@SearchFragment)
            setOnItemClickListener { _, _, index, _ ->
                viewModel.onEvent(SearchEvent.OnSuggestionClick(index))
            }
            onClearListener = object : SearchAutoComplete.OnClearListener {
                override fun onClear() {
                    when (text.isNotEmpty()) {
                        true -> setText("")
                        false -> {
                            hideKeyBoard()
                            navigate(R.id.action_searchCityFragment_to_dayWeatherFragment)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initAutoCompleteTextView()
        val navigatedFrom = arguments?.getString("navigatedFrom")
        val candidate = prefsLoadLatestCandidate()
        when {
            candidate == PREF_DEFAULT_VALUE || navigatedFrom == DailyFragment::class.java.simpleName -> noCitySearched(navigatedFrom)
            else -> loadLatestSearchedCity(candidate)
        }
    }

    private fun loadLatestSearchedCity(candidate: String) {
        val moshi = Moshi.Builder().build()
        sharedViewModel().candidate =
            moshi.adapter(Candidate::class.java).fromJson(candidate)!!
        navigate(R.id.action_searchCityFragment_to_dayWeatherFragment)
    }

    private fun noCitySearched(navigatedFrom: String?) {
        viewModel.citySuggestions.observe(viewLifecycleOwner, citySuggestionsObserver)
        viewModel.candidate.observe(viewLifecycleOwner, candidateObserver)
        when(navigatedFrom) {
            "start" -> LocationEvent.observe(viewLifecycleOwner, locationObserver)
        }
        NetworkEvents.observe(viewLifecycleOwner, networkEventObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        arrayAdapter = null
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}


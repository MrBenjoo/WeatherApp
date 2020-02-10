package com.benji.weatherswe.locationweather

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.domain.domainmodel.State
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.weatherswe.R
import com.benji.weatherswe.locationweather.servicelocator.LocationWeatherServiceLocator.provideSearchCityViewModel
import com.benji.weatherswe.utils.*
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.location_weather_fragment.*


class LocationWeatherFragment : Fragment(), TextWatcher {
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var viewModel: LocationWeatherViewModel

    override fun afterTextChanged(text: Editable?) {
        if (text.toString().trim() != "") {
            viewModel.getCitySuggestions(text.toString())
        }
    }

    private val candidateObserver = Observer<Candidate> { candidate ->
        sharedViewModel().candidate = candidate
        navigate(R.id.action_locationWeatherFragment_to_dayWeatherFragment)
    }

    private fun initAutoCompleteTextView() = with(auto_complete_tv_location_weather_search) {
        setAdapter(arrayAdapter)
        threshold = 1
        addTextChangedListener(this@LocationWeatherFragment)
        setOnItemClickListener { _, _, index, _ -> viewModel.onSuggestionClicked(index) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = provideSearchCityViewModel(this)
        initArrayAdapter()
        initAutoCompleteTextView()
    }

    private val citySuggestionsObserver = Observer<List<String>> { suggestions ->
        arrayAdapter.clear()
        arrayAdapter.addAll(suggestions)
        arrayAdapter.notifyDataSetChanged()
    }

    /**
     * set adapter for autoCompleteTextView to null in order
     * to remove reference to context and avoid memory leak
     * https://medium.com/@yfujiki/tracing-simple-memory-leak-around-recyclerview-using-leakcanary-927460532d53
     */
    override fun onDestroyView() {
        super.onDestroyView()
        auto_complete_tv_location_weather_search.setAdapter(null)
    }

    private fun initArrayAdapter() {
        arrayAdapter = ArrayAdapter(
            mainActivity().applicationContext,
            android.R.layout.simple_dropdown_item_1line,
            emptyList<String>()
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when (val candidate = prefsLoadLatestCandidate()) {
            Constants.PREF_DEFAULT_VALUE -> noCitySearched()
            else -> loadLatestSearchedCity(candidate)
        }
    }

    private fun loadLatestSearchedCity(candidate: String) {
        val moshi = Moshi.Builder().build()
        sharedViewModel().candidate = moshi.adapter(Candidate::class.java).fromJson(candidate)!!
        navigate(R.id.action_locationWeatherFragment_to_dayWeatherFragment)
    }

    private fun noCitySearched() {
        viewModel.citySuggestions.observe(viewLifecycleOwner, citySuggestionsObserver)
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
        viewModel.candidate.observe(viewLifecycleOwner, candidateObserver)
    }


    private val stateObserver = Observer<State> { state ->
        when (state) {
            State.InFlight -> {
                loading_location_bar.visibility = View.VISIBLE
            }
            State.Complete, State.Idle, State.Gone -> {
                loading_location_bar.visibility = View.GONE
            }
            else -> {
                loading_location_bar.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_weather_fragment, container, false)
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

}

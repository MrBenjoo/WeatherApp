package com.benji.weatherswe.locationweather

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.weatherswe.R
import com.benji.weatherswe.locationweather.servicelocator.LocationWeatherServiceLocator.provideSearchCityViewModel
import com.benji.weatherswe.utils.mainActivity
import com.benji.weatherswe.utils.navigate
import com.benji.weatherswe.utils.sharedViewModel
import com.benji.weatherswe.utils.showText
import kotlinx.android.synthetic.main.location_weather_fragment.*


class LocationWeatherFragment : Fragment(), TextWatcher {
    private lateinit var viewModel: LocationWeatherViewModel
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_weather_fragment, container, false)
    }

    override fun afterTextChanged(text: Editable?) {
        if (text.toString().trim() != "") {
            viewModel.getCitySuggestions(text.toString())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = provideSearchCityViewModel(this)
        initArrayAdapter()
        initAutoCompleteTextView()
        observeCitySuggestions()
        observeErrorMessages()
        observeCandidate()
    }

    private fun observeCandidate() {
        viewModel.candidate.observe(
            this,
            Observer { candidate ->
                sharedViewModel().candidate = candidate
                navigate(R.id.action_locationWeatherFragment_to_dayWeatherFragment)
            }
        )
    }

    private fun initArrayAdapter() {
        arrayAdapter = ArrayAdapter(
            mainActivity().applicationContext,
            android.R.layout.simple_dropdown_item_1line,
            emptyList<String>()
        )
    }

    private fun initAutoCompleteTextView() = with(auto_complete_tv_location_weather_search) {
        setAdapter(arrayAdapter)
        threshold = 1
        addTextChangedListener(this@LocationWeatherFragment)
        setOnItemClickListener { _, _, index, _ -> viewModel.onSuggestionClicked(index) }
    }

    private fun observeCitySuggestions() {
        viewModel.citySuggestions.observe(
            this,
            Observer { suggestions ->
                arrayAdapter.clear()
                arrayAdapter.addAll(suggestions)
                arrayAdapter.notifyDataSetChanged()
            }
        )
    }

    private fun observeErrorMessages() {
        viewModel.errorMessage.observe(
            this,
            Observer { errorMessage ->
                showText(errorMessage)
            }
        )
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

}

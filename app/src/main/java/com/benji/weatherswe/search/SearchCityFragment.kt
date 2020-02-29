package com.benji.weatherswe.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.weatherswe.R
import com.benji.weatherswe.search.SearchCityServiceLocator.provideAutoCompleteCityAdapter
import com.benji.weatherswe.search.SearchCityServiceLocator.provideViewModel
import com.benji.weatherswe.utils.AutoCompleteCityAdapter
import com.benji.weatherswe.utils.extensions.activitySharedViewModel
import com.benji.weatherswe.utils.extensions.hideKeyBoard
import com.benji.weatherswe.utils.extensions.navigate
import kotlinx.android.synthetic.main.search_city_fragment.*


class SearchCityFragment : Fragment(), TextWatcher {

    private val viewModel: SearchCityViewModel by lazy { provideViewModel(this) }


    private val arrayAdapter: AutoCompleteCityAdapter by lazy { provideAutoCompleteCityAdapter(this) }


    override fun afterTextChanged(text: Editable?) {
        viewModel.onEvent(SearchEvent.OnQueryInput(text.toString()))
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
                    SearchEvent.OnSuggestionClick(
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

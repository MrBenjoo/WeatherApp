package com.benji.weatherswe.start

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
import com.benji.weatherswe.start.servicelocator.StartServiceLocator.provideStartPageViewModel
import com.benji.weatherswe.utils.mainActivity
import com.benji.weatherswe.utils.showText
import kotlinx.android.synthetic.main.start_page_fragment.*


class StartPageFragment : Fragment(), TextWatcher {
    private lateinit var viewModel: StartPageViewModel
    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.start_page_fragment, container, false)
    }

    override fun afterTextChanged(text: Editable?) {
        if (text.toString().trim() != "") {
            viewModel.getCitySuggestions(text.toString())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = provideStartPageViewModel(this)
        initArrayAdapter()
        initAutoCompleteTextView()
        observeCitySuggestions()
        observeErrorMessages()
    }


    private fun initArrayAdapter() {
        arrayAdapter = ArrayAdapter(
            mainActivity().applicationContext,
            android.R.layout.simple_dropdown_item_1line,
            emptyList<String>()
        )
    }

    private fun initAutoCompleteTextView() = with(autoCompleteTv_start) {
        setAdapter(arrayAdapter)
        threshold = 1
        addTextChangedListener(this@StartPageFragment)
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

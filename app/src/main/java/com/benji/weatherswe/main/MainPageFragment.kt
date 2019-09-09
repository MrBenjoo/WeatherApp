package com.benji.weatherswe.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.benji.weatherswe.R
import com.benji.weatherswe.main.servicelocator.MainPageServiceLocator
import com.benji.weatherswe.start.servicelocator.StartServiceLocator


class MainPageFragment : Fragment() {
    private lateinit var viewModel: MainPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = MainPageServiceLocator.provideMainPageViewModel(this)

    }

}

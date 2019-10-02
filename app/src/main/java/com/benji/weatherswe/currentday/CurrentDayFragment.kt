package com.benji.weatherswe.currentday


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.sharedViewModel
import com.benji.weatherswe.weather.WeatherAdapter
import kotlinx.android.synthetic.main.fragment_current_day.*

class CurrentDayFragment : Fragment() {
    private val TAG = "CurrentDayFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateRecyclerView()
    }

    private fun populateRecyclerView() {
        val forecast = sharedViewModel().currentDayForecast.listOfHourlyData
        //recyclerview_current_day.setHasFixedSize(true)
        val adapter = CurrentDayAdapter(forecast)
        recyclerview_current_day.adapter = adapter
    }

}

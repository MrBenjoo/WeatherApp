package com.benji.weatherswe.currentday


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.domain.domainmodel.weather.Parameter
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.navigate
import com.benji.weatherswe.utils.sharedViewModel
import kotlinx.android.synthetic.main.fragment_current_day.*


class CurrentDayFragment : Fragment() {
    private val TAG = "CurrentDayFragment"

    private val listClickObserver = Observer<Hourly> { rowData ->
        sharedViewModel().hourly = rowData
        sharedViewModel().hourlyMap = rowData.parameters.associate {
            Pair(
                it.name,
                Parameter(it.name, it.levelType, it.level, it.unit, it.values)
            )
        }
        navigate(R.id.action_currentDayFragment_to_currentDayHourFragment)
    }

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
        recyclerview_current_day.setHasFixedSize(true)
        val adapter = CurrentDayAdapter(forecast)
        adapter.rowData.observe(this, listClickObserver)
        recyclerview_current_day.adapter = adapter
    }

}

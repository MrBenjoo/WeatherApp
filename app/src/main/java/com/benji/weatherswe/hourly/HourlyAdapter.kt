package com.benji.weatherswe.hourly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.forecast.SymbolUtils
import kotlinx.android.synthetic.main.item_day_forecast.view.*

class HourlyAdapter(private var listOfHourlyData: List<Hourly>) :
    RecyclerView.Adapter<HourlyAdapter.MainViewHolder>() {


    private val _rowData = MutableLiveData<Hourly>()
    val rowData: LiveData<Hourly> = _rowData


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(
            inflater.inflate(R.layout.item_day_forecast, parent, false)
        )
    }

    fun updateHourlyForecast(newListOfHourlyData: List<Hourly>) {
        this.listOfHourlyData = newListOfHourlyData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listOfHourlyData.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        with(holder) {
            val hourly = listOfHourlyData[position]
            day.text = hourly.validTime
            temperature.text = hourly.temp + "\u00B0"
            image.setAnimation(SymbolUtils.getWeatherSymbolLottie(hourly.weatherSymbol))
            bind(hourly, this@HourlyAdapter._rowData)
        }
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.tv_row_list_day
        val image = itemView.img_row_list_weather
        val temperature = itemView.tv_row_list_temperature

        fun bind(hourlyData: Hourly, hourly: MutableLiveData<Hourly>) {
            itemView.setOnClickListener { hourly.value = hourlyData }
        }
    }
}



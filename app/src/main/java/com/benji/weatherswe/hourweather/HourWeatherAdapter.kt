package com.benji.weatherswe.hourweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.weatherswe.R
import com.benji.weatherswe.utils.WeatherUtils
import kotlinx.android.synthetic.main.item_day_forecast.view.*

class HourWeatherAdapter(private var listOfHourlyData: List<Hourly>) :
    RecyclerView.Adapter<HourWeatherAdapter.MainViewHolder>() {
    val rowData = MutableLiveData<Hourly>()
    private val TAG = "HourWeatherAdapter"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(
            inflater.inflate(R.layout.item_day_forecast, parent, false)
        )
    }

    override fun getItemCount(): Int = listOfHourlyData.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        with(holder) {
            val hourly = listOfHourlyData[position]

            day.text = hourly.validTime
            temperature.text = hourly.temp
            image.setAnimation(WeatherUtils().getWeatherSymbolImage(hourly.weatherSymbol))

            bind(hourly, this@HourWeatherAdapter.rowData)
        }
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.tv_row_list_day
        val image = itemView.img_row_list_weather
        val temperature = itemView.tv_row_list_temperature

        fun bind(hourlyData: Hourly, hourly: MutableLiveData<Hourly>) {
            itemView.setOnClickListener {
                    hourly.value = hourlyData
            }
        }
    }
}



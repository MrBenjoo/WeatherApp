package com.benji.weatherswe.dayweather


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.weatherswe.R
import kotlinx.android.synthetic.main.item_day_forecast.view.*


class WeatherAdapter(var data: List<DayForecast>?) :
    RecyclerView.Adapter<WeatherAdapter.MainViewHolder>() {
    private val TAG = "WeatherAdapter"
    val rowData = MutableLiveData<RowData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(
            inflater.inflate(R.layout.item_day_forecast, parent, false)
        )
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        data?.let {
            with(holder) {
                val forecast = it[position]
                day.text = forecast.day
                //image.background = it[position].image
                temperature.text = forecast.temperature
                bind(forecast, this@WeatherAdapter.rowData)
            }
        }
    }


    fun setList(list: List<DayForecast>) {
        data = list
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.tv_main_list_day
        val image = itemView.img_main_list_weather
        val temperature = itemView.tv_main_list_temp

        fun bind(forecast: DayForecast, rowData: MutableLiveData<RowData>) {
            itemView.setOnClickListener { rowData.value = RowData(adapterPosition, forecast) }
        }
    }

}

data class RowData(val position: Int, val dayForecast: DayForecast)
package com.benji.weatherswe.weather


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benji.weatherswe.R
import com.benji.domain.domainmodel.weather.WeekdayForecast
import kotlinx.android.synthetic.main.item_day_forecast.view.*


class WeatherAdapter(var data: List<WeekdayForecast>?) : RecyclerView.Adapter<WeatherAdapter.MainViewHolder>() {
    private val TAG = "WeatherAdapter"

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
                day.text = it[position].day
                //image.background = it[position].image
                temperature.text = it[position].temperature
            }
        }
    }

    fun setList(list: List<WeekdayForecast>) {
        data = list
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.tv_main_list_day
        val image = itemView.img_main_list_weather
        val temperature = itemView.tv_main_list_temp
    }

}
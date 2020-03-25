package com.benji.weatherswe.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.weatherswe.R
import kotlinx.android.synthetic.main.item_day_forecast.view.*

class DayWeatherAdapter(var listOfDayForecast: List<DayForecast>?) :
    RecyclerView.Adapter<DayWeatherAdapter.MainViewHolder>() {

    private val _rowData = MutableLiveData<RowData>()
    val rowData: LiveData<RowData> = _rowData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(
            inflater.inflate(R.layout.item_day_forecast, parent, false)
        )
    }

    override fun getItemCount(): Int = listOfDayForecast?.size ?: 0

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        listOfDayForecast?.let {
            with(holder) {
                val forecast = it[position]
                weekday.text = forecast.day
                weatherSymbol.setAnimation(getWeatherSymbolImage(forecast.weatherSymbol))
                temperature.text = forecast.temperature + "\u00B0"
                bind(forecast, this@DayWeatherAdapter._rowData)
            }
        }
    }

    private fun getWeatherSymbolImage(weatherSymbol: Int): Int = when (weatherSymbol) {
        1 -> R.raw.lottie_weather_sunny
        2 -> R.raw.lottie_weather_partly_cloudy
        3 -> R.raw.lottie_weather_partly_cloudy
        4 -> R.raw.lottie_weather_partly_cloudy
        5 -> R.raw.lottie_weather_cloudy
        6 -> R.raw.lottie_weather_cloudy
        7 -> R.raw.lottie_weather_foggy
        8 -> R.raw.lottie_weather_partly_raining
        9 -> R.raw.lottie_weather_partly_raining
        10 -> R.raw.lottie_weather_partly_raining
        11 -> R.raw.lottie_weather_thunderstorm
        12 -> R.raw.lottie_weather_partly_raining
        13 -> R.raw.lottie_weather_partly_raining
        14 -> R.raw.lottie_weather_partly_raining
        15 -> R.raw.lottie_weather_snow
        16 -> R.raw.lottie_weather_snow
        17 -> R.raw.lottie_weather_snow
        18 -> R.raw.lottie_weather_partly_raining
        19 -> R.raw.lottie_weather_partly_raining
        20 -> R.raw.lottie_weather_partly_raining
        21 -> R.raw.lottie_weather_thunder
        22 -> R.raw.lottie_weather_partly_raining
        23 -> R.raw.lottie_weather_partly_raining
        24 -> R.raw.lottie_weather_partly_raining
        25 -> R.raw.lottie_weather_snow
        26 -> R.raw.lottie_weather_snow
        27 -> R.raw.lottie_weather_snow
        else -> R.raw.lottie_weather_sunny
    }

    fun setList(newListOfDayForecast: List<DayForecast>) {
        listOfDayForecast = newListOfDayForecast
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weekday: AppCompatTextView = itemView.tv_row_list_day
        val weatherSymbol: LottieAnimationView = itemView.img_row_list_weather
        val temperature: AppCompatTextView = itemView.tv_row_list_temperature

        fun bind(forecast: DayForecast, rowData: MutableLiveData<RowData>) {
            itemView.setOnClickListener { rowData.value = RowData(adapterPosition, forecast) }
        }
    }

}

data class RowData(val position: Int, val dayForecast: DayForecast)
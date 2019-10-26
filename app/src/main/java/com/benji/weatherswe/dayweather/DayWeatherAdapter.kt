package com.benji.weatherswe.dayweather


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.benji.domain.domainmodel.weather.DayForecast
import com.benji.weatherswe.R
import kotlinx.android.synthetic.main.item_day_forecast.view.*


class DayWeatherAdapter(var data: List<DayForecast>?) :
    RecyclerView.Adapter<DayWeatherAdapter.MainViewHolder>() {

    private val TAG = "DayWeatherAdapter"
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
                image.setAnimation(getWeatherSymbolImage(forecast.weatherSymbol))
                temperature.text = forecast.temperature
                bind(forecast, this@DayWeatherAdapter.rowData)
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


    fun setList(list: List<DayForecast>) {
        data = list
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.tv_row_list_day
        val image = itemView.img_row_list_weather
        val temperature = itemView.tv_row_list_temperature

        fun bind(forecast: DayForecast, rowData: MutableLiveData<RowData>) {
            itemView.setOnClickListener { rowData.value = RowData(adapterPosition, forecast) }
        }
    }

}

data class RowData(val position: Int, val dayForecast: DayForecast)
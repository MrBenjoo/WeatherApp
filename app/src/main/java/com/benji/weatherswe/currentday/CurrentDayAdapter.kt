package com.benji.weatherswe.currentday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.benji.domain.domainmodel.weather.Hourly
import com.benji.weatherswe.R
import kotlinx.android.synthetic.main.item_day_forecast.view.*

class CurrentDayAdapter(private var listOfHourlyData: List<Hourly>) :
    RecyclerView.Adapter<CurrentDayAdapter.MainViewHolder>() {
    val rowData = MutableLiveData<Hourly>()
    private val TAG = "CurrentDayAdapter"


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
            temperature.text = "18C"

            bind(hourly, this@CurrentDayAdapter.rowData)
        }
    }

    fun setList(list: List<Hourly>) {
        listOfHourlyData = list
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.tv_main_list_day
        val image = itemView.img_main_list_weather
        val temperature = itemView.tv_main_list_temp

        fun bind(hourlyData: Hourly, hourly: MutableLiveData<Hourly>) {
            itemView.setOnClickListener {
                itemView.setOnClickListener {
                    hourly.value = hourlyData
                }
            }
        }

    }
}



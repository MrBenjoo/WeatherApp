package com.benji.weatherswe.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.benji.weatherswe.R

class AutoCompleteCityAdapter(context: Context, private val listOfCities: List<String>) :
    ArrayAdapter<String>(context, 0, listOfCities), Filterable {

    private var selectedListOfCities: List<String> = listOfCities


    override fun getCount(): Int {
        return selectedListOfCities.size
    }

    override fun getItem(position: Int): String? {
        return selectedListOfCities[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mConvertView = convertView
        val viewHolder : ViewHolder

        if (mConvertView == null) {
            mConvertView = LayoutInflater.from(context).inflate(R.layout.row_autocomplete, parent, false)

            viewHolder = ViewHolder()
            viewHolder.cityTextView = mConvertView?.findViewById(R.id.tv_row_autocomplete)

            mConvertView!!.tag = viewHolder
        } else {
            viewHolder = convertView!!.tag as ViewHolder
        }

        viewHolder.cityTextView?.text = getItem(position)

        return mConvertView
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            @SuppressLint("DefaultLocale")
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint?.toString()?.toLowerCase()

                val filterResults = FilterResults()

                filterResults.values = if (queryString==null || queryString.isEmpty())
                    listOfCities
                else
                    listOfCities.filter { it.toLowerCase().contains(queryString) }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                selectedListOfCities = results?.values as List<String>
                notifyDataSetChanged()
            }
        }
    }

    fun updateList(suggestions: List<String>) {
        selectedListOfCities = suggestions
        notifyDataSetChanged()
    }
}

class ViewHolder {
    var cityTextView : TextView? = null
}
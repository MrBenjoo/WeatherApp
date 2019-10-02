package com.benji.weatherswe.utils

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.benji.domain.domainmodel.geocoding.Location
import com.benji.domain.domainmodel.geocoding.Suggestion
import com.benji.weatherswe.MainActivity
import com.benji.weatherswe.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import java.math.BigDecimal
import java.math.RoundingMode

fun androidx.fragment.app.Fragment.showText(text: String) {
    Snackbar.make(
        mainActivity().findViewById(android.R.id.content),
        text,
        Snackbar.LENGTH_LONG
    )
        .show()
}


fun androidx.fragment.app.Fragment.mainActivity(): MainActivity = (activity as MainActivity)

fun androidx.fragment.app.Fragment.navigate(id: Int) {
    view!!.findNavController().navigate(id)
}

fun androidx.fragment.app.Fragment.navigate(id: Int, bundle: Bundle) {
    view!!.findNavController().navigate(id, bundle)
}

fun androidx.fragment.app.Fragment.sharedViewModel(): SharedViewModel {
    return ViewModelProviders.of(mainActivity()).get(SharedViewModel::class.java)
}

fun List<Suggestion>.returnCities(): List<String> {
    val listOfCities = mutableListOf<String>()
    forEach { suggestion -> listOfCities.add(suggestion.text) }
    return listOfCities
}

fun Location.sixDecimals(): Location {
    val longitude = BigDecimal(x).setScale(5, RoundingMode.CEILING)
    val latitude = BigDecimal(y).setScale(5, RoundingMode.CEILING)
    return Location(longitude.toDouble(), latitude.toDouble())
}

fun androidx.fragment.app.Fragment.setupToolbar(
    toolbar: Toolbar,
    listener: Toolbar.OnMenuItemClickListener?
) {
    mainActivity().setSupportActionBar(toolbar)
    mainActivity().supportActionBar?.title = null
    toolbar.setNavigationOnClickListener { mainActivity().onBackPressed() }
    toolbar.setOnMenuItemClickListener(listener)
}
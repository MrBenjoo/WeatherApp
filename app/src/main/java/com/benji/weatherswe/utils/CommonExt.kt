package com.benji.weatherswe.utils

import com.benji.domain.domainmodel.geocoding.Suggestion
import com.benji.weatherswe.MainActivity
import com.google.android.material.snackbar.Snackbar

fun androidx.fragment.app.Fragment.showText(text: String) {
    Snackbar.make(
        mainActivity().findViewById(android.R.id.content),
        text,
        Snackbar.LENGTH_LONG
    )
        .show()
}


fun androidx.fragment.app.Fragment.mainActivity(): MainActivity = (activity as MainActivity)

fun List<Suggestion>.returnCities() : List<String> {
    val listOfCities = mutableListOf<String>()
    forEach { suggestion -> listOfCities.add(suggestion.text) }
    return listOfCities
}
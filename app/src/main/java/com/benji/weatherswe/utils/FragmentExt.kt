package com.benji.weatherswe.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.androidadvance.topsnackbar.TSnackbar
import com.benji.domain.domainmodel.geocoding.Candidate
import com.benji.weatherswe.MainActivity
import com.benji.weatherswe.R
import com.benji.weatherswe.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi

fun androidx.fragment.app.Fragment.mainActivity(): MainActivity = (activity as MainActivity)

fun androidx.fragment.app.Fragment.navigate(id: Int) {
    view!!.findNavController().navigate(id)
}

fun androidx.fragment.app.Fragment.sharedViewModel(): SharedViewModel {
    return ViewModelProviders.of(mainActivity()).get(SharedViewModel::class.java)
}

fun androidx.fragment.app.Fragment.setupToolbar(
    toolbar: Toolbar
) {
    mainActivity().setSupportActionBar(toolbar)
    mainActivity().supportActionBar?.title = null
}

fun androidx.fragment.app.Fragment.prefsStoreCandidate(candidateJson: String) {
    val prefs = mainActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    prefs.edit().putString(Constants.PREF_KEY_CITY, candidateJson).apply()
}

fun androidx.fragment.app.Fragment.prefsLoadLatestCandidate(): String {
    val prefs = mainActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
    return prefs.getString(Constants.PREF_KEY_CITY, Constants.PREF_DEFAULT_VALUE).toString()
}

fun androidx.fragment.app.Fragment.hideKeyBoard(view: View) {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun androidx.fragment.app.Fragment.string(id: Int): String = context!!.resources.getString(id)

fun androidx.fragment.app.Fragment.getColor(color: Int): Int {
    return ContextCompat.getColor(this.context!!, color)
}

fun androidx.fragment.app.Fragment.setupSearchAutoComplete(searchView: SearchView): SearchView.SearchAutoComplete {
    val searchAutoComplete =
        searchView.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
    searchAutoComplete.setHintTextColor(getColor(R.color.colorPrimary))
    return searchAutoComplete
}

fun androidx.fragment.app.Fragment.showText(text: String) {
    Snackbar.make(
        mainActivity().findViewById(android.R.id.content),
        text,
        Snackbar.LENGTH_LONG
    )
        .show()
}

fun androidx.fragment.app.Fragment.showTopText(text: String) {
    val snackbar = TSnackbar.make(
        mainActivity().findViewById(android.R.id.content),
        text,
        TSnackbar.LENGTH_LONG
    )
        .setActionTextColor(Color.WHITE)

    snackbar.view.setBackgroundColor(Color.RED)
    snackbar.show()
}


fun Candidate.toJson(): String {
    return Moshi.Builder().build().adapter(Candidate::class.java)
        .toJson(this)
}
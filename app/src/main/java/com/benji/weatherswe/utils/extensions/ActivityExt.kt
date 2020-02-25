package com.benji.weatherswe.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.benji.weatherswe.MainActivity
import com.benji.weatherswe.SharedViewModel
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showActivityText(text: String) {
    Snackbar.make(
        this.findViewById(android.R.id.content),
        text,
        Snackbar.LENGTH_LONG
    )
        .show()
}

fun MainActivity.sharedViewModel(): SharedViewModel {
    return ViewModelProviders.of(this).get(SharedViewModel::class.java)
}
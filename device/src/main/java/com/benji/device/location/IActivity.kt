package com.benji.device.location

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

interface IActivity {

    fun getActivity() : AppCompatActivity

    fun getContext() : Context

}
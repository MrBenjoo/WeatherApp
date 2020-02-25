package com.benji.weatherswe

import android.app.Application
import com.benji.weatherswe.utils.network.NetworkStateHolder.registerConnectivityBroadcaster

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerConnectivityBroadcaster()
    }

}


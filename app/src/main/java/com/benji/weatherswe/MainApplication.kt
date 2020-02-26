package com.benji.weatherswe

import android.app.Application
import com.benji.device.network.NetworkStateHolder.registerConnectivityBroadcaster

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerConnectivityBroadcaster()
    }

}


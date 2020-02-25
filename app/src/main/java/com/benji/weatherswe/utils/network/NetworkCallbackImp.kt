package com.benji.weatherswe.utils.network

import android.net.ConnectivityManager
import android.net.Network

class NetworkCallbackImp(private val holder: NetworkStateImp) :
    ConnectivityManager.NetworkCallback() {

    override fun onLost(network: Network) {
        holder.isConnected = false
    }

    override fun onAvailable(network: Network) {
        holder.isConnected = true
    }
}
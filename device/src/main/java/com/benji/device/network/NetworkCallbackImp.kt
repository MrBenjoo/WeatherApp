package com.benji.device.network

import android.net.ConnectivityManager
import android.net.Network

internal class NetworkCallbackImp(private val holder: NetworkStateImp) :
    ConnectivityManager.NetworkCallback() {

    override fun onLost(network: Network) {
        holder.isConnected = false
    }

    override fun onAvailable(network: Network) {
        when(holder.isConnected) {
            true -> Unit // unnecessary to send network available if it is already available
            false -> holder.isConnected = true
        }
    }
}
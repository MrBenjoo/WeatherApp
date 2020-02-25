package com.benji.weatherswe.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.annotation.RequiresPermission


class NetworkUtils(private val context: Context) {


    fun registerNetworkCallback() {
        val connectivityManagerCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {

            private val activeNetworks: MutableList<Network> = mutableListOf()


            override fun onAvailable(network: Network) {


                // Add to list of active networks if not already in list
                //if (activeNetworks.none {  activeNetwork ->  activeNetwork.networkHandle == network.networkHandle }) activeNetworks.add(network)
                //val isNetworkConnected = activeNetworks.isNotEmpty()
                Log.d("NetworkUtils", "onAvailable")
            }


            override fun onLost(network: Network) {


                // Remove network from active network list
                //activeNetworks.removeAll { activeNetwork -> activeNetwork.networkHandle == network.networkHandle }
                //val isNetworkConnected = activeNetworks.isNotEmpty()
                Log.d("NetworkUtils", "onLost")
            }
        }

    }
}
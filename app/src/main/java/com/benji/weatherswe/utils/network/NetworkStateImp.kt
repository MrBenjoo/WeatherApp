package com.benji.weatherswe.utils.network

class NetworkStateImp : INetworkState {

    override var isConnected: Boolean = false
        set(value) {
            field = value
            NetworkEvents.notify(if (value) Event.ConnectivityAvailable else Event.ConnectivityLost)
        }
}
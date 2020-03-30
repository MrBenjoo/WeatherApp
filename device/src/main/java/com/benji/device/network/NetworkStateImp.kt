package com.benji.device.network

internal class NetworkStateImp : INetworkState {

    override var isConnected: Boolean = true
        set(value) {
            field = value
            NetworkEvents.notify(if (value) Event.ConnectivityAvailable else Event.ConnectivityLost)
        }
}
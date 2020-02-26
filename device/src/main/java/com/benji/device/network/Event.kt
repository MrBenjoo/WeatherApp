package com.benji.device.network

sealed class Event {

    object ConnectivityLost : Event()
    object ConnectivityAvailable : Event()

}
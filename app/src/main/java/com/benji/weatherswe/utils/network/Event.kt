package com.benji.weatherswe.utils.network

sealed class Event {

    object ConnectivityLost : Event()
    object ConnectivityAvailable : Event()

}
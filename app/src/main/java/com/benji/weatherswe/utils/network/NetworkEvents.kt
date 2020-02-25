package com.benji.weatherswe.utils.network

import androidx.lifecycle.LiveData

/**
 * This object is a LiveData and provides a way of observing
 * network events from any LifeCycleOwner such as Activity and
 * Fragment by using NetworkEvents.observe(lifecycleowner, observer).
 *
 * Non-LifeCycleOwner classes can use NetworkEvents.observeForever(observer)
 */
object NetworkEvents : LiveData<Event>() {

    /**
     * Push new network events to the observers.
     * This notify function is used by the class: NetworkStateImp
     */
    fun notify(event: Event) {
        postValue(event)
    }

}
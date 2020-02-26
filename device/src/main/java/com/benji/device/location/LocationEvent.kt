package com.benji.device.location

import androidx.lifecycle.LiveData
import com.benji.domain.domainmodel.geocoding.Location

object LocationEvent: LiveData<Location>() {

    /**
     * Push new network events to the observers.
     * This notify function is used by the class: NetworkStateImp
     */
    fun notify(location: Location) {
        postValue(location)
    }

}

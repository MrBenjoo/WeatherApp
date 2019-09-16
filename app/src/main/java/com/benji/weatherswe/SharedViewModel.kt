package com.benji.weatherswe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benji.domain.domainmodel.geocoding.Candidate

class SharedViewModel: ViewModel() {
    val candidate = MutableLiveData<Candidate>()

}
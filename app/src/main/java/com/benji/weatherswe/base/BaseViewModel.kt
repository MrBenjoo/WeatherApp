package com.benji.weatherswe.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benji.domain.domainmodel.State

abstract class BaseViewModel : ViewModel() {

    /**
     * Do not let other classes modify the state.
     * Views - Fragments and Activities - should not be able of updating LiveData and
     * thus their own state because that is the responsibility of ViewModels.
     * Views should be able to only observe LiveData.
     * Therefore we encapsulate access to MutableLiveData with get.
     */
    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    protected val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    protected fun setError(error : String) {
        _error.value = error
    }

    protected fun setLoadingState() {
        _state.value = State.Loading
    }

    protected fun setCompletedState() {
        _state.value = State.Complete
    }

    private fun setGoneState() {
        _state.value = State.Gone
    }

    override fun onCleared() {
        setGoneState()
        super.onCleared()
    }

}
package com.benji.domain.domainmodel

sealed class State {
    object Loading: State()
    object Complete: State()
    object Gone: State()
}
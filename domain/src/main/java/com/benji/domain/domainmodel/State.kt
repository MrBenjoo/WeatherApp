package com.benji.domain.domainmodel

sealed class State {
    object InFlight: State()
    object Complete: State()
    object Idle: State()
    object Gone: State()
}
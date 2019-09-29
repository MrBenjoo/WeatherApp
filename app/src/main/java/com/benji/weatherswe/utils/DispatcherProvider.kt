package com.benji.weatherswe.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object DispatcherProvider {

    fun provideUIContext(): CoroutineContext {
        return Dispatchers.Main
    }

    fun provideIOContext(): CoroutineContext {
        return Dispatchers.IO
    }

}
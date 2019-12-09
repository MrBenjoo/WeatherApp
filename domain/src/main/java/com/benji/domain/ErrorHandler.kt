package com.benji.domain

interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity

}
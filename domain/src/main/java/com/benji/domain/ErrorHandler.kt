package com.benji.domain

import com.benji.domain.domainmodel.ErrorEntity

interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity

}
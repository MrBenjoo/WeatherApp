package com.benji.data

import com.benji.domain.domainmodel.ErrorEntity
import com.benji.domain.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class ErrorHandlerImpl : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity = when (throwable) {
        is IOException -> ErrorEntity.Network(throwable)
        is HttpException -> {
            when (throwable.code()) {
                // not found
                HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound(throwable)

                // access denied
                HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied(throwable)

                // unavailable service
                HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable(throwable)

                // all the others will be treated as unknown error
                else -> ErrorEntity.Unknown(throwable)
            }
        }
        else -> ErrorEntity.Unknown(throwable)
    }

}
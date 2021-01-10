package com.example.soa.network

import retrofit2.HttpException
import java.io.IOException

class RetrofitException : RuntimeException {

    /**
     * The event kind which triggered this error.
     */
    val kind: Kind

    private constructor(kind: Kind, message: String) : super(message, null) {
        this.kind = kind
    }

    private constructor(kind: Kind, exception: HttpException) : super(exception.message(), exception) {
        this.kind = kind
    }

    private constructor(kind: Kind, exception: Throwable) : super(exception.message, exception) {
        this.kind = kind
    }

    /**
     * Identifies the event kind which triggered a [RetrofitException].
     */
    enum class Kind {
        /**
         * Request executed correctly but server returned an error.
         */
        SERVER,

        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,

        /**
         * A non-200 HTTP update code was received from the server.
         */
        HTTP,

        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    companion object {

        fun serverError(error: Error): RetrofitException {
            return RetrofitException(Kind.SERVER, error)
        }

        fun httpError(httpException: HttpException): RetrofitException {
            return RetrofitException(Kind.HTTP, httpException)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(Kind.NETWORK, exception)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(Kind.UNEXPECTED, exception)
        }
    }
}

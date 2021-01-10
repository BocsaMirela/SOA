package com.example.soa.network

import com.example.soa.repository.IPreference
import com.example.soa.util.Constants.AUTHORIZATION
import com.example.soa.util.Constants.BEARER
import com.example.soa.util.Constants.KEY_ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HttpAuthInterceptor(private val preference: IPreference) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()

        val authToken = preference[KEY_ACCESS_TOKEN]
        if (!authToken.isNullOrEmpty()) {
            // Add authorization header with updated authorization value to intercepted request
            requestBuilder.header(AUTHORIZATION, "$BEARER $authToken")
        }
        return chain.proceed(requestBuilder.build())
    }
}
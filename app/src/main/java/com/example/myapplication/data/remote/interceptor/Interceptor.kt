package com.example.myapplication.data.remote.interceptor

import android.util.Log
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Loggable
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenProvider: suspend () -> String, // Fetch token dynamically
    loggable: Loggable? = null
) : CurlInterceptor(loggable) {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Fetch token in a blocking manner
        val token = runBlocking { tokenProvider() }

        val newRequest = request.newBuilder()
            .header("Accept", "application/json")
            .apply {
                if (token.isNotEmpty()) {
                    header("Authorization", "Bearer $token")
                }
            }
            .build()

        val response = chain.proceed(newRequest)

        val curlCommand = getCurlBuilder(newRequest).build()
        Log.i("CurLogger", curlCommand)

        return response
    }
}

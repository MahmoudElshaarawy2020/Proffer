package com.example.myapplication.data.remote.interceptor

import android.util.Log
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Loggable
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenProvider: suspend () -> String,
    private val tokenUpdater: suspend (String) -> Unit,
    loggable: Loggable? = null
) : CurlInterceptor(loggable) {

    private var cachedToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Get token (fetch from cache or retrieve)
        val token = cachedToken ?: runBlocking { tokenProvider().also { cachedToken = it } }

        val newRequest = request.newBuilder()
            .header("Accept", "application/json")  // Always add Accept header
            .apply {
                if (token.isNotEmpty()) {
                    header("Authorization", "Bearer $token") // Add Authorization header
                }
            }
            .build()

        val response = chain.proceed(newRequest)

        if (response.code == 401) {
            Log.w("AuthInterceptor", "Token expired! Fetching new token...")

            val newToken = runBlocking { tokenProvider() }
            if (newToken.isNotEmpty() && newToken != cachedToken) {
                cachedToken = newToken
                runBlocking { tokenUpdater(newToken) }

                val retriedRequest = request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
                return chain.proceed(retriedRequest)
            }
        }

        val curlCommand = getCurlBuilder(newRequest).build()
        Log.i("CurLogger", curlCommand)

        return response
    }
}

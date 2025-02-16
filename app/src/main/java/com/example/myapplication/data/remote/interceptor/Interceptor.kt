package com.example.myapplication.data.remote.interceptor

import android.util.Log
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Loggable
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor(loggable: Loggable? = null,private var token: String = "") :
    CurlInterceptor(loggable) {


    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        runBlocking {
            token = getToken()
        }

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


    object ResponseCode {
        var responseCode: Int? = 0
    }


    private suspend fun getToken(): String {
        return "Bearer $token"
    }

    private fun getAcceptHeader(): String {
        return "application/json"
    }
}
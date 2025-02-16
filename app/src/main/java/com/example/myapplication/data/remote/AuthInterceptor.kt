package com.example.myapplication.data.remote

import android.util.Log
import com.example.myapplication.data.data_store.DataStoreManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val dataStoreManager: DataStoreManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            try {
                dataStoreManager.getToken.firstOrNull()
            } catch (e: Exception) {
                Log.e("AuthInterceptor", "Error fetching token", e)
                null
            }
        }

        val requestBuilder = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            Log.d("AuthInterceptor", "Using Token: Bearer $token")
            requestBuilder.addHeader("Authorization", "Bearer $token")
        } else {
            Log.w("AuthInterceptor", "No token found, sending request without Authorization")
        }

        requestBuilder.addHeader("Accept", "application/json")

        return chain.proceed(requestBuilder.build())
    }
}

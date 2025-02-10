package com.example.myapplication.data.remote

import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>
}
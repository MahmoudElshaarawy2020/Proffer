package com.example.myapplication.data.remote

import com.example.myapplication.data.request.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST
    suspend fun register(
        @Body registerRequest: RegisterRequest
    )
}
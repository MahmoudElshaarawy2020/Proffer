package com.example.myapplication.data.remote

import com.example.myapplication.data.request.LoginRequest
import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.request.VerificationRequest
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.data.response.VerificationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("auth/verify")
    suspend fun verify(
        @Body verificationRequest: VerificationRequest
    ): Response<VerificationResponse>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}
package com.example.myapplication.domain.repository

import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.util.Result

interface RegisterRepository {
    suspend fun register(
        registerRequest: RegisterRequest
    ) : Result<RegisterResponse>
}
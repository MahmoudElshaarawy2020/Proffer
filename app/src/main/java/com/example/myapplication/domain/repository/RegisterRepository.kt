package com.example.myapplication.domain.repository

import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.util.Result
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    fun register(registerRequest: RegisterRequest): Flow<Result<AuthResponse>>
}
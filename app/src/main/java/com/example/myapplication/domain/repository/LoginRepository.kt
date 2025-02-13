package com.example.myapplication.domain.repository

import com.example.myapplication.data.request.LoginRequest
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.util.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(loginRequest: LoginRequest): Flow<Result<LoginResponse>>
}
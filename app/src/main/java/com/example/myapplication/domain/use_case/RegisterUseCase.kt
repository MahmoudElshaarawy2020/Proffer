package com.example.myapplication.domain.use_case

import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.util.Result

class RegisterUseCase(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(registerRequest: RegisterRequest): Result<RegisterResponse> {
        return repository.register(registerRequest)
    }
}
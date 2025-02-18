package com.example.myapplication.domain.use_case

import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    operator fun invoke(registerRequest: RegisterRequest) = repository.register(registerRequest = registerRequest)
}
package com.example.myapplication.domain.use_case

import com.example.myapplication.data.request.LoginRequest
import com.example.myapplication.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    operator fun invoke(loginRequest: LoginRequest) = repository.login(loginRequest = loginRequest)
}
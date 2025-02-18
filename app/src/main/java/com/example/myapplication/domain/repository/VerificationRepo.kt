package com.example.myapplication.domain.repository

import com.example.myapplication.data.request.VerificationRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.VerificationResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result

interface VerificationRepo {
    fun verify(verificationRequest: VerificationRequest): Flow<Result<AuthResponse>>
}
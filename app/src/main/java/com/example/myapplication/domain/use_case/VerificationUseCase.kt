package com.example.myapplication.domain.use_case

import com.example.myapplication.data.request.VerificationRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.VerificationResponse
import com.example.myapplication.domain.repository.VerificationRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.myapplication.util.Result

class VerificationUseCase @Inject constructor(
    private val repository: VerificationRepo,
) {
    operator fun invoke(verificationRequest: VerificationRequest):
            Flow<Result<AuthResponse>> = repository.verify(verificationRequest)
}
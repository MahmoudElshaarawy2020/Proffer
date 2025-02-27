package com.example.myapplication.domain.use_case

import com.example.myapplication.data.request.ChangePasswordRequest
import com.example.myapplication.domain.repository.ProfileRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(token: String, changePasswordRequest: ChangePasswordRequest) = repository.changePassword(token, changePasswordRequest)
}
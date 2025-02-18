package com.example.myapplication.domain.use_case

import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.myapplication.util.Result

class LogoutUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(token: String) = repository.logout(token = token)
}
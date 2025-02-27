package com.example.myapplication.domain.repository

import com.example.myapplication.data.request.ChangePasswordRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.EditProfileResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result


interface ProfileRepository {
    fun getMoreAboutUser(token: String): Flow<Result<AuthResponse>>
    fun logout(token: String): Flow<Result<AuthResponse>>
    fun changePassword(token: String, changePasswordRequest: ChangePasswordRequest): Flow<Result<EditProfileResponse>>
}
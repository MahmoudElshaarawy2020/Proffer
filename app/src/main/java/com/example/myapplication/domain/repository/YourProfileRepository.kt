package com.example.myapplication.domain.repository

import com.example.myapplication.data.request.EditProfileRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.EditProfileResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result

interface YourProfileRepository {
    fun deleteAccount(token: String): Flow<Result<AuthResponse>>
    fun getYourProfileData(token: String): Flow<Result<AuthResponse>>
    fun editeYourProfile(token: String, editProfileRequest: EditProfileRequest): Flow<Result<EditProfileResponse>>
}

package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.EditProfileResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface YourProfileRepository {
    fun deleteAccount(): Flow<Result<AuthResponse>>
    fun getYourProfileData(): Flow<Result<AuthResponse>>
    fun editYourProfile(
        method: RequestBody,
        userName: RequestBody,
        phoneNumber: RequestBody,
        address: RequestBody,
        image: MultipartBody.Part?
    ): Flow<Result<EditProfileResponse>>
}

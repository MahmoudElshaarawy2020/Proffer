package com.example.myapplication.domain.repository

import com.example.myapplication.data.request.ChangePasswordRequest
import com.example.myapplication.data.response.AboutUsResponse
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.EditProfileResponse
import com.example.myapplication.data.response.FAQResponse
import com.example.myapplication.data.response.PrivacyPolicyResponse
import com.example.myapplication.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result


interface ProfileRepository {
    fun getMoreAboutUser(token: String): Flow<Result<AuthResponse>>
    fun logout(token: String): Flow<Result<AuthResponse>>
    fun getFAQ(): Flow<Result<FAQResponse>>
    fun getPrivacyPolicy(): Flow<Result<PrivacyPolicyResponse>>
    fun getAboutUs(): Flow<Result<AboutUsResponse>>
    fun changePassword(token: String, changePasswordRequest: ChangePasswordRequest): Flow<Result<EditProfileResponse>>
}
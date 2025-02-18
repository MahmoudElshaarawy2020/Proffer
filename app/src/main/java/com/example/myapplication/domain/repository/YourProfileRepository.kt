package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.ProfileResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result

interface YourProfileRepository {
    fun deleteAccount(token: String): Flow<Result<ProfileResponse>>
    fun getYourProfileData(token: String): Flow<Result<ProfileResponse>>
}

package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.ProfileResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result


interface ProfileRepository {
    fun getMoreAboutUser(token: String): Flow<Result<ProfileResponse>>
}
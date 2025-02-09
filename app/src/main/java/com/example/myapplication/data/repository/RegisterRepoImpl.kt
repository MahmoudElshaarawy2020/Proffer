package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.myapplication.util.Result

class RegisterRepoImpl(
    private val apiService: ApiService,
    private val registerRequest: RegisterRequest
) {
    suspend fun register(registerRequest: RegisterRequest): Flow<Result<RegisterResponse>> = flow {
        emit(Result.Loading())
        try {
            val response = apiService.register(registerRequest)
            if (response.isSuccessful){
                response.body()?.let {
                    emit(Result.Success(response.body()!!))
                } ?: emit(Result.Error("Response body is empty"))
            } else {
                emit(Result.Error(response.message()))

            }

        } catch (e: Exception) {

        }
    }


}
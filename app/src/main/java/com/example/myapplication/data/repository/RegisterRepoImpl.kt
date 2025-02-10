package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterRepoImpl @Inject constructor(
    private val apiService: ApiService
) : RegisterRepository {

    override fun register(registerRequest: RegisterRequest): Flow<Result<RegisterResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.register(registerRequest)

            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response body"))
            } else {
                emit(Result.Error("Error: ${response.errorBody()?.string()}"))
            }

        } catch (e: HttpException) {
            emit(Result.Error("HTTP Error: ${e.message}"))
        } catch (e: IOException) {
            emit(Result.Error("Network Error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected Error: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)
}

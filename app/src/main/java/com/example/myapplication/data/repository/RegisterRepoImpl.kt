package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.AuthResponse
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
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) : RegisterRepository {

    override fun register(registerRequest: RegisterRequest): Flow<Result<AuthResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.register(registerRequest)

            if (response.isSuccessful) {
                Log.d("RegisterRepoImpl", "API call successful")
                response.body()?.let {authResponse ->
                    authResponse.token?.let { token ->
                        dataStoreManager.saveAuthToken(token)
                    }

                    authResponse.userData?.let { userData ->
                        dataStoreManager.saveUserData(userData)
                    }
                    emit(Result.Success(authResponse))
                } ?: emit(Result.Error("Empty response body"))

            } else {
                Log.d("RegisterRepoImpl", "API call failed")
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

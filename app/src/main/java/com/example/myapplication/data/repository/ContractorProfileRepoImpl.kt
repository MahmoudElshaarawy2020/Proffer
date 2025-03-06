package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.response.ContractorProfileResponse
import com.example.myapplication.domain.repository.ContractorProfileRepository
import com.example.myapplication.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ContractorProfileRepoImpl @Inject constructor(
    private val apiService: ApiService
): ContractorProfileRepository {
    override fun getContractorProfile(
        token: String,
        id: Int
    ): Flow<Result<ContractorProfileResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.getContractorProfile(token, id)

            if (response.isSuccessful) {
                Log.d("getContractorProfile", "successful")
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response body"))

            } else {
                Log.d("getContractorProfile", "profile API call failed")
                emit(
                    Result.Error(
                        "Error: ${
                            response.errorBody()?.string()
                        }"
                    )
                )
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
package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.response.BidsResponse
import com.example.myapplication.domain.repository.BidsRepository
import com.example.myapplication.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BidsRepoImpl @Inject constructor(
    private val apiService: ApiService
): BidsRepository {
    override fun getBids(
        skip: Int,
        take: Int,
        projectId: Int,
        rate: Int,
        minPrice: Int,
        maxPrice: Int,
        token: String
    ): Flow<Result<BidsResponse>>  = flow {
        emit(Result.Loading())

        try {
            val response = apiService.getBids(skip, take, projectId, rate, minPrice, maxPrice, token, "application/json")

            if (response.isSuccessful) {
                Log.d("getBidsImpl", "successful")
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response body"))

            } else {
                Log.d("getBidsImpl", "profile API call failed")
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
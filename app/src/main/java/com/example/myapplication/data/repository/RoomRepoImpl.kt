package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.response.AdditionsResponse
import com.example.myapplication.data.response.MaterialsResponse
import com.example.myapplication.data.response.RoomZonesResponse
import com.example.myapplication.domain.repository.RoomRepository
import com.example.myapplication.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RoomRepoImpl @Inject constructor(
    private val apiService: ApiService
): RoomRepository {
    override fun getRoomZones(): Flow<Result<RoomZonesResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.getRoomZones()

            if (response.isSuccessful) {
                Log.d("getRoomZones", "successful")
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response body"))

            } else {
                Log.d("getRoomZones", "profile API call failed")
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

    override fun getMaterials(
        category: Int
    ): Flow<Result<MaterialsResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.getMaterials(category)

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("getMaterials", "Body: $body")

                if (body?.status == true) {
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error(body?.message ?: "Unknown error"))
                }

            } else {
                Log.d("getMaterials", "API call failed")
                emit(
                    Result.Error(
                        "Server Error: ${
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

    override fun getAdditions(category: Int): Flow<Result<AdditionsResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.getAdditions(category)

            if (response.isSuccessful) {
                val body = response.body()
                Log.d("getAdditions", "Body: $body")

                if (body?.status == true) {
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error(body?.message ?: "Unknown error"))
                }

            } else {
                Log.d("getAdditions", "API call failed")
                emit(
                    Result.Error(
                        "Server Error: ${
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
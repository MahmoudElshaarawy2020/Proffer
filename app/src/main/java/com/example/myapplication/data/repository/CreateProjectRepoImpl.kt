package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.response.CreateProjectResponse
import com.example.myapplication.domain.repository.CreateProjectRepository
import com.example.myapplication.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateProjectRepoImpl @Inject constructor(
    private val apiService: ApiService,
) : CreateProjectRepository {
    override fun createProject(
        partMap: Map<String, @JvmSuppressWildcards RequestBody>,
        images: List<MultipartBody.Part>
    ): Flow<Result<CreateProjectResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.createProject(
                partMap = partMap,
                images = images
            )

            if (response.isSuccessful) {
                Log.d("createProjectImpl", "CreateProject API call successful")
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response body"))

            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                Log.e("createProjectImpl", "CreateProject API call failed: $errorMsg")
                emit(Result.Error("Error: $errorMsg"))
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

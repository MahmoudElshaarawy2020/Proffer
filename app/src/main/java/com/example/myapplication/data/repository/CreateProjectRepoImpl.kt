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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateProjectRepoImpl @Inject constructor(
    private val apiService: ApiService,
) : CreateProjectRepository {
    override fun createProject(
        token: String,
        name: RequestBody,
        project_type_id: RequestBody,
        from_budget: RequestBody,
        to_budget: RequestBody,
        location: RequestBody,
        lat: RequestBody,
        long: RequestBody,
        area: RequestBody,
        start_date: RequestBody,
        duration: RequestBody,
        is_open_budget: RequestBody,
        city_id: RequestBody,
        governorate_id: RequestBody,
        image: List<MultipartBody.Part>
    ): Flow<Result<CreateProjectResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.createProject(
                name = name,
                project_type_id = project_type_id,
                from_budget = from_budget,
                to_budget = to_budget,
                location = location,
                lat = lat,
                long = long,
                start_date = start_date,
                is_open_budget = is_open_budget,
                area = area,
                duration = duration,
                city_id = city_id,
                governorate_id = governorate_id,
                images = image,
                token = token,
                accept = "application/json",
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

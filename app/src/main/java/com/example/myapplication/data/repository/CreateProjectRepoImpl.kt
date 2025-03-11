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
): CreateProjectRepository {
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

        image: List<MultipartBody.Part>
    ): Flow<Result<CreateProjectResponse>> = flow {
        emit(Result.Loading())

        try {
            val imageFile = "image".toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val response = apiService.createProject(
                name = name,
                project_type_id = project_type_id,
                from_budget =  from_budget,
                to_budget = to_budget,
                location = location,
                lat = lat,
                long = long,
                start_date = start_date,
                is_open_budget =  is_open_budget,
                area = area,
                image = image,
                project_image = imageFile,
                token = token,
                duration = duration ,
                accept = "application/json"
            )

            if (response.isSuccessful) {
                Log.d("createProjectImpl", "editProfile API call successful")
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response body"))

            } else {
                Log.d("createProjectImpl", "CreateProject API call failed")
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
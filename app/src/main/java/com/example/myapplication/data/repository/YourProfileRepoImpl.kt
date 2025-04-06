package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.EditProfileResponse
import com.example.myapplication.domain.repository.YourProfileRepository
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

class YourProfileRepoImpl @Inject constructor(
    private val apiService: ApiService
) : YourProfileRepository {
    override fun deleteAccount(): Flow<Result<AuthResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.deleteAccount()

            if (response.isSuccessful) {
                Log.d("profileDeletion", "successful")
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response body"))

            } else {
                Log.d("ProfileDeletion", "profile API call failed")
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

    override fun getYourProfileData(): Flow<Result<AuthResponse>> = flow {
        emit(Result.Loading())

        try {
            val response = apiService.getMoreAboutUser()

            if (response.isSuccessful) {
                Log.d("YourProfileData", "successful")
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response body"))

            } else {
                Log.d("YourProfileData", "profile API call failed")
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

    override fun editYourProfile(
        partMap: Map<String, RequestBody>,
        image: MultipartBody.Part?
    ): Flow<Result<EditProfileResponse>> = flow {
        emit(Result.Loading())

        try {
            val imageFile = "image".toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val response = apiService.
            editProfile(partMap, image)

            if (response.isSuccessful) {
                Log.d("editProfileRepoImpl", "editProfile API call successful")
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response body"))

            } else {
                Log.d("editProfileRepoImpl", "editProfile API call failed")
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
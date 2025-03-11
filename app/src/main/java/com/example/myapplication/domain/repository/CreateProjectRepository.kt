package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.CreateProjectResponse
import com.example.myapplication.util.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface CreateProjectRepository {
    fun createProject(
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
    ): Flow<Result<CreateProjectResponse>>
}
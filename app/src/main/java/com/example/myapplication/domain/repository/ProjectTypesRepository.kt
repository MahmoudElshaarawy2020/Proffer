package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.ProjectTypesResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result

interface ProjectTypesRepository {
    fun getProjectTypes(): Flow<Result<ProjectTypesResponse>>
}
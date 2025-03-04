package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.SliderResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result


interface HomeRepository {
    fun getSliders(): Flow<Result<SliderResponse>>
}
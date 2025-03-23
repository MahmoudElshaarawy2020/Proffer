package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.HomeResponse
import com.example.myapplication.data.response.HomeSliderResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result


interface HomeRepository {
    fun getSliders(): Flow<Result<HomeSliderResponse>>
    fun getContractors(): Flow<Result<HomeResponse>>
}
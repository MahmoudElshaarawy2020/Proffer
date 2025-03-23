package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.BidsResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result


interface BidsRepository {
    fun getBids(skip: Int, take: Int, projectId: Int, rate: Int, minPrice: Int, maxPrice: Int): Flow<Result<BidsResponse>>
}
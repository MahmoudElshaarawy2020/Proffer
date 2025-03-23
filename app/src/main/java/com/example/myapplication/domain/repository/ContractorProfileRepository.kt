package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.ContractorProfileResponse
import com.example.myapplication.util.Result
import kotlinx.coroutines.flow.Flow

interface ContractorProfileRepository {
    fun getContractorProfile( id: Int): Flow<Result<ContractorProfileResponse>>
}
package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.MaterialsResponse
import com.example.myapplication.data.response.RoomZonesResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result

interface RoomRepository {
    fun getRoomZones(): Flow<Result<RoomZonesResponse>>
    fun getMaterials(
        category: Int
    ): Flow<Result<MaterialsResponse>>
}
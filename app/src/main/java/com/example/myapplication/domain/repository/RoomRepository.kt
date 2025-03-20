package com.example.myapplication.domain.repository

import com.example.myapplication.data.response.RoomZonesResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result

interface RoomRepository {
    fun getRoomZones(): Flow<Result<RoomZonesResponse>>
}
package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.RoomRepository
import javax.inject.Inject

class GetRoomZonesUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    operator fun invoke() = roomRepository.getRoomZones()

}
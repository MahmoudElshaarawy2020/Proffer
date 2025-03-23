package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.BidsRepository
import javax.inject.Inject

class GetBidsUseCase @Inject constructor(
    private val bidsRepository: BidsRepository
) {
    operator fun invoke(
        skip: Int,
        take: Int,
        projectId: Int,
        rate: Int,
        minPrice: Int,
        maxPrice: Int,
    ) = bidsRepository.getBids(skip, take, projectId, rate, minPrice, maxPrice)
}
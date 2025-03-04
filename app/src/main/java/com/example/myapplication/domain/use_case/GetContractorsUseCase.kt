package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.HomeRepository
import javax.inject.Inject

class GetContractorsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(token: String) = repository.getContractors(token)

}
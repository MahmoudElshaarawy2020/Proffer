package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.ContractorProfileRepository
import javax.inject.Inject

class GetContractorProfileUseCase @Inject constructor(
    private val repository: ContractorProfileRepository
) {
    operator fun invoke(token: String, id: Int) = repository.getContractorProfile(token, id)

}
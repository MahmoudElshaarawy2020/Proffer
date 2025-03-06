package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.ProfileRepository
import javax.inject.Inject

class GetContactTypesUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() = repository.getContactTypes()

}
package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.ProfileRepository
import javax.inject.Inject

class getAboutUsUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke() = repository.getAboutUs()
}
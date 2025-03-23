package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.YourProfileRepository
import javax.inject.Inject

class GetYourProfileUseCase @Inject constructor(
    private val repository: YourProfileRepository
) {
    operator fun invoke() = repository.getYourProfileData()
}
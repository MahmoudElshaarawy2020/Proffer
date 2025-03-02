package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.ProfileRepository
import javax.inject.Inject

class getFAQUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke(skip: Int, take: Int) = repository.getFAQ(skip, take)

}
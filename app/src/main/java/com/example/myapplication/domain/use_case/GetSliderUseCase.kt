package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.HomeRepository
import javax.inject.Inject

class GetSliderUseCase @Inject constructor(
    private val repository: HomeRepository
){
    operator fun invoke() = repository.getSliders()
}

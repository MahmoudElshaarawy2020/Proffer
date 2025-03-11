package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.ProjectTypesRepository
import javax.inject.Inject

class GetProjectTypesUseCase @Inject constructor(
    private val projectTypesRepository: ProjectTypesRepository
) {
     operator fun invoke() = projectTypesRepository.getProjectTypes()
}
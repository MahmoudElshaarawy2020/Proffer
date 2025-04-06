package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.CreateProjectRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class CreateProjectUseCase @Inject constructor(
    private val createProjectRepository: CreateProjectRepository
) {
    operator fun invoke(
        partMap : Map<String, @JvmSuppressWildcards RequestBody>,
        image: List<MultipartBody.Part>
    ) = createProjectRepository.createProject(
        partMap,
        image
    )
}

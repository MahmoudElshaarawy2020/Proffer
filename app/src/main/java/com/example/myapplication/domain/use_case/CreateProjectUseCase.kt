package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.CreateProjectRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class CreateProjectUseCase @Inject constructor(
    private val createProjectRepository: CreateProjectRepository
) {
    operator fun invoke(
        token: String,
        name: RequestBody,
        project_type_id: RequestBody,
        from_budget: RequestBody,
        to_budget: RequestBody,
        location: RequestBody,
        lat: RequestBody,
        long: RequestBody,
        area: RequestBody,
        start_date: RequestBody,
        duration: RequestBody,
        is_open_budget: RequestBody,
        city_id: RequestBody,
        governorate_id: RequestBody,
        image: List<MultipartBody.Part>
    ) = createProjectRepository.createProject(
        token,
        name,
        project_type_id,
        from_budget,
        to_budget,
        location,
        lat,
        long,
        area,
        start_date,
        duration,
        is_open_budget,
        city_id,
        governorate_id,
        image
    )
}

package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.YourProfileRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class EditYourProfileUseCase @Inject constructor(
    private val repository: YourProfileRepository
) {
    operator fun invoke(
        partMap: Map<String, RequestBody>,
        image: MultipartBody.Part?
        ) = repository.editYourProfile(partMap, image)

}
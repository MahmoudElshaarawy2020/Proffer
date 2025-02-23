package com.example.myapplication.domain.use_case

import com.example.myapplication.data.request.EditProfileRequest
import com.example.myapplication.domain.repository.YourProfileRepository
import javax.inject.Inject

class EditYourProfileUseCase @Inject constructor(
    private val repository: YourProfileRepository
) {
    operator fun invoke(token: String, editProfileRequest: EditProfileRequest) = repository.editeYourProfile(token, editProfileRequest)

}
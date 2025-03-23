package com.example.myapplication.domain.use_case

import com.example.myapplication.data.request.ContactUsRequest
import com.example.myapplication.domain.repository.ProfileRepository
import javax.inject.Inject

class ContactUsUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke( contactUsRequest: ContactUsRequest) =
        profileRepository.contactUs(contactUsRequest)

}
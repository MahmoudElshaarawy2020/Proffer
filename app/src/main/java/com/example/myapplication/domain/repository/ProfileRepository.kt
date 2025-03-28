package com.example.myapplication.domain.repository

import com.example.myapplication.data.request.ChangePasswordRequest
import com.example.myapplication.data.request.ContactUsRequest
import com.example.myapplication.data.response.AboutUsResponse
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.EditProfileResponse
import com.example.myapplication.data.response.FAQResponse
import com.example.myapplication.data.response.GetContactTypesResponse
import com.example.myapplication.data.response.PrivacyPolicyResponse
import com.example.myapplication.data.response.TermsResponse
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.util.Result


interface ProfileRepository {
    fun getMoreAboutUser(): Flow<Result<AuthResponse>>
    fun logout(): Flow<Result<AuthResponse>>
    fun getFAQ(skip: Int, take: Int): Flow<Result<FAQResponse>>
    fun getPrivacyPolicy(): Flow<Result<PrivacyPolicyResponse>>
    fun getAboutUs(): Flow<Result<AboutUsResponse>>
    fun getTerms(): Flow<Result<TermsResponse>>
    fun getContactTypes(): Flow<Result<GetContactTypesResponse>>
    fun contactUs(contactUsRequest: ContactUsRequest): Flow<Result<EditProfileResponse>>
    fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<Result<EditProfileResponse>>
}
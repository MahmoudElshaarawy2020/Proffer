package com.example.myapplication.data.remote

import com.example.myapplication.data.request.ChangePasswordRequest
import com.example.myapplication.data.request.ContactUsRequest
import com.example.myapplication.data.request.LoginRequest
import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.request.VerificationRequest
import com.example.myapplication.data.response.AboutUsResponse
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.BidsResponse
import com.example.myapplication.data.response.ContractorProfileResponse
import com.example.myapplication.data.response.CreateProjectResponse
import com.example.myapplication.data.response.EditProfileResponse
import com.example.myapplication.data.response.FAQResponse
import com.example.myapplication.data.response.GetContactTypesResponse
import com.example.myapplication.data.response.HomeResponse
import com.example.myapplication.data.response.PrivacyPolicyResponse
import com.example.myapplication.data.response.HomeSliderResponse
import com.example.myapplication.data.response.MaterialsResponse
import com.example.myapplication.data.response.ProjectTypesResponse
import com.example.myapplication.data.response.RoomZonesResponse
import com.example.myapplication.data.response.TermsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<AuthResponse>

    @POST("auth/verify")
    suspend fun verify(
        @Body verificationRequest: VerificationRequest
    ): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<AuthResponse>

    @GET("auth/profile")
    suspend fun getMoreAboutUser(): Response<AuthResponse>

    @DELETE("auth/delete-account")
    suspend fun deleteAccount(): Response<AuthResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<AuthResponse>

    @Multipart
    @POST("auth/edit-profile")
    suspend fun editProfile(
        @Part("_method") method: RequestBody,
        @Part("name") userName: RequestBody,
        @Part("phone") phoneNumber: RequestBody,
        @Part("address") address: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("profile_image") requestBody: RequestBody,
    ): Response<EditProfileResponse>

    @POST("auth/change-password")
    suspend fun changePassword(
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<EditProfileResponse>

    @GET("clients/bids")
    suspend fun getBids(
        @Query("skip") skip: Int,
        @Query("take") take: Int,
        @Query("project_id") projectId: Int,
        @Query("rate") rate: Int,
        @Query("min_price") minPrice: Int,
        @Query("max_price") maxPrice: Int,
    ): Response<BidsResponse>

    @GET("faqs")
    suspend fun getFAQ(
        @Query("skip") skip: Int,
        @Query("take") take: Int
    ): Response<FAQResponse>

    @GET("settings/about_us")
    suspend fun getAboutUs(): Response<AboutUsResponse>

    @GET("settings/privacy_policy")
    suspend fun getPrivacyPolicy(): Response<PrivacyPolicyResponse>

    @GET("settings/terms")
    suspend fun getTerms(): Response<TermsResponse>

    @GET("sliders")
    suspend fun getSliders(): Response<HomeSliderResponse>

    @GET("clients/home?filter[status]=pending")
    suspend fun getContractors(): Response<HomeResponse>


    @GET("contact-types")
    suspend fun getContactTypes(): Response<GetContactTypesResponse>

    @GET("project-types")
    suspend fun getProjectTypes(
        @Header("Accept") accept: String
    ): Response<ProjectTypesResponse>

    @POST("contact-us")
    suspend fun contactUs(
        @Body contactUsRequest: ContactUsRequest
    ): Response<EditProfileResponse>

    @GET("clients/contractor/{contractorId}/profile")
    suspend fun getContractorProfile(
        @Path("contractorId") contractorId: Int
    ): Response<ContractorProfileResponse>

    @Multipart
    @POST("clients/projects")
    suspend fun createProject(
        @Part("name") name: RequestBody,
        @Part("project_type_id") project_type_id: RequestBody,
        @Part("area") area: RequestBody,
        @Part("from_budget") from_budget: RequestBody,
        @Part("to_budget") to_budget: RequestBody,
        @Part("location") location: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("long") long: RequestBody,
        @Part("start_date") start_date: RequestBody,
        @Part("duration") duration: RequestBody,
        @Part("is_open_budget") is_open_budget: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part("governorate_id") governorate_id: RequestBody,
        @Part images: List<MultipartBody.Part>,
    ): Response<CreateProjectResponse>

    @GET("room-zones")
    suspend fun getRoomZones(
    ): Response<RoomZonesResponse>

    @GET("materials")
    suspend fun getMaterials(
        @Query("filter[category]") category: Int,
    ): Response<MaterialsResponse>

}
    package com.example.myapplication.domain.repository

    import com.example.myapplication.data.response.CreateProjectResponse
    import com.example.myapplication.util.Result
    import kotlinx.coroutines.flow.Flow
    import okhttp3.MultipartBody
    import okhttp3.RequestBody

    interface CreateProjectRepository {
        fun createProject(
            partMap: Map<String, @JvmSuppressWildcards RequestBody>,
            images: List<MultipartBody.Part>
        ): Flow<Result<CreateProjectResponse>>
    }

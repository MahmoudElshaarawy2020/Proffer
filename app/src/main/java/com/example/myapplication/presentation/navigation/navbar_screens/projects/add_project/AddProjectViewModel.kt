package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.CreateProjectResponse
import com.example.myapplication.data.response.ProjectTypesResponse
import com.example.myapplication.domain.use_case.CreateProjectUseCase
import com.example.myapplication.domain.use_case.GetProjectTypesUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
    private val getProjectTypesUseCase: GetProjectTypesUseCase,
    private val createProjectUseCase: CreateProjectUseCase
) : ViewModel() {
    private val _getProjectTypesState =
        MutableStateFlow<Result<ProjectTypesResponse>>(Result.Loading())
    val getProjectTypesState: MutableStateFlow<Result<ProjectTypesResponse>> get() = _getProjectTypesState

    private val _createProjectState =
        MutableStateFlow<Result<CreateProjectResponse>>(Result.Loading())
    val createProjectState: MutableStateFlow<Result<CreateProjectResponse>> get() = _createProjectState

    fun getProjectTypes() {
        viewModelScope.launch {
            try {
                getProjectTypesUseCase.invoke()
                    .catch { e ->
                        Log.e("getProjectTypesError", "API call failed", e)
                        _getProjectTypesState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        _getProjectTypesState.value = result
                    }

            } catch (e: Exception) {
                Log.e("getProjectTypesError", "Unexpected error", e)
                _getProjectTypesState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }

    fun createProject(
        token: String,
        name: RequestBody,
        project_type_id: RequestBody,
        from_budget: RequestBody,
        to_budget: RequestBody,
        location: RequestBody,
        lat: RequestBody,
        long: RequestBody,
        area: RequestBody,
        duration: RequestBody,
        start_date: RequestBody,
        is_open_budget: RequestBody,
        image: List<MultipartBody.Part>
    ) {
        if (token.isBlank()) {
            Log.e("createProjectError", "Token is missing!")
            _createProjectState.value = Result.Error("Authentication token is missing!")
            return
        }

        viewModelScope.launch {
            _createProjectState.value = Result.Loading()
            createProjectUseCase.invoke(
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
                image
            )
                .catch { e ->
                    Log.e("createProjectError", "API call failed", e)
                    _createProjectState.value = Result.Error("Failed to edit profile: ${e.message}")
                }
                .collectLatest { result ->
                    _createProjectState.value = result
                }
        }
    }
}
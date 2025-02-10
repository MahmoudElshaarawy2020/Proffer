package com.example.myapplication.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.RegisterResponse
import com.example.myapplication.domain.repository.RegisterRepository
import com.example.myapplication.domain.use_case.RegisterUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<Result<RegisterResponse>>(Result.Loading())
    val registerState: StateFlow<Result<RegisterResponse>> get() = _registerState

    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerUseCase.invoke(registerRequest)
                .catch { e ->
                    _registerState.value = Result.Error("Unexpected Error: ${e.message}")
                }
                .collectLatest { result ->
                    _registerState.value = result
                }
        }
    }
}

package com.example.myapplication.presentation.log_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.request.LoginRequest
import com.example.myapplication.data.request.VerificationRequest
import com.example.myapplication.data.response.LoginResponse
import com.example.myapplication.data.response.VerificationResponse
import com.example.myapplication.domain.use_case.LoginUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _loginState = MutableStateFlow<Result<LoginResponse>>(Result.Loading())
    val loginState: MutableStateFlow<Result<LoginResponse>> get() = _loginState

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase.invoke(loginRequest)
                .catch { e ->
                    Log.e("VerificationError", "API call failed", e)
                    _loginState.value = Result.Error("Unexpected Error: ${e.message}")
                }
                .collectLatest { result ->
                    _loginState.value = result
                }
        }
    }
}
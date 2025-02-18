package com.example.myapplication.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.request.RegisterRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.domain.use_case.RegisterUseCase
import com.example.myapplication.presentation.log_in.LoginViewModel
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
    private val registerUseCase: RegisterUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _registerState = MutableStateFlow<Result<AuthResponse>>(Result.Loading())
    val registerState: StateFlow<Result<AuthResponse>> get() = _registerState

    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerUseCase.invoke(registerRequest)
                .catch { e ->
                    Log.e("Register Error", "API call failed", e)
                    _registerState.value = Result.Error("Unexpected Error: ${e.message}")
                }
                .collectLatest { result ->
                    _registerState.value = result
                    if (result is Result.Success) {
                        result.data?.token?.let { token ->
                            Log.d("New Token", token)
                            dataStoreManager.saveAuthToken(token)
                        }
                    }
                }
        }
    }
}

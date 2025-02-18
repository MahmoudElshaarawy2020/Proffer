package com.example.myapplication.presentation.verification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.request.VerificationRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.VerificationResponse
import com.example.myapplication.domain.use_case.VerificationUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val verificationUseCase: VerificationUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _verificationState = MutableStateFlow<Result<AuthResponse>>(Result.Loading())
    val verificationState: StateFlow<Result<AuthResponse>> get() = _verificationState

    fun verification(verificationRequest: VerificationRequest) {
        viewModelScope.launch {
            verificationUseCase.invoke(verificationRequest)
                .catch { e ->
                    Log.e("VerificationError", "API call failed", e)
                    _verificationState.value = Result.Error("Unexpected Error: ${e.message}")
                }
                .collectLatest { result ->
                    _verificationState.value = result
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
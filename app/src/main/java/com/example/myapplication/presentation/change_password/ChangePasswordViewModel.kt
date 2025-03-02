package com.example.myapplication.presentation.change_password

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.request.ChangePasswordRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.EditProfileResponse
import com.example.myapplication.domain.use_case.ChangePasswordUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val dataStoreManager: DataStoreManager,
): ViewModel() {
    private val _changePasswordState = MutableStateFlow<Result<EditProfileResponse>>(Result.Loading())
    val changePasswordState: MutableStateFlow<Result<EditProfileResponse>> get() = _changePasswordState

    fun changePassword(token: String, changePasswordRequest: ChangePasswordRequest) {
        viewModelScope.launch {
            _changePasswordState.value = Result.Loading()

            changePasswordUseCase.invoke(token, changePasswordRequest)
                .catch { e ->
                    Log.e("changePassword Error", "API call failed", e)
                    _changePasswordState.value = Result.Error("Failed to change password: ${e.message}")
                }
                .collectLatest { result ->
                    _changePasswordState.value = result
                    if (result is Result.Error) {
                        Log.e("changePassword Error", "API response error: ${result.message}")
                    } else if (result is Result.Success) {
                        Log.d("changePassword Success", "Password changed successfully")
                        dataStoreManager.clearToken()
                    }
                }
        }

    }
}
package com.example.myapplication.presentation.navigation.navbar_screens.more.your_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.ProfileResponse
import com.example.myapplication.domain.use_case.DeleteProfileUseCase
import com.example.myapplication.domain.use_case.GetYourProfileUseCase
import com.example.myapplication.domain.use_case.ProfileUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourProfileViewModel @Inject constructor(
    private val profileUseCase: GetYourProfileUseCase,
    private val deleteAccountUseCase: DeleteProfileUseCase
): ViewModel() {
    private val _yourProfileState = MutableStateFlow<Result<ProfileResponse>>(Result.Loading())
    val yourProfileState: MutableStateFlow<Result<ProfileResponse>> get() = _yourProfileState

    private val _deleteAccountState = MutableStateFlow<Result<Unit>?>(null)
    val deleteAccountState: MutableStateFlow<Result<Unit>?> get() = _deleteAccountState

    fun getYourProfileData(token: String) {
        viewModelScope.launch {
            try {
                if (token.isNotEmpty()) {
                    profileUseCase.invoke(token)
                        .catch { e ->
                            Log.e("YourProfileRequestError", "API call failed", e)
                            _yourProfileState.value = Result.Error("Unexpected Error: ${e.message}")
                        }
                        .collectLatest { result ->
                            _yourProfileState.value = result
                        }
                } else {
                    Log.e("YourProfileRequestError", "Token is missing!")
                    _yourProfileState.value = Result.Error("Authentication token is missing!")
                }
            } catch (e: Exception) {
                Log.e("YourProfileRequestError", "Unexpected error", e)
                _yourProfileState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }

    fun deleteAccount(token: String) {
        viewModelScope.launch {
            _deleteAccountState.value = Result.Loading()

            try {
                if (token.isNotEmpty()) {
                    deleteAccountUseCase.invoke(token)
                        .catch { e ->
                            Log.e("DeleteAccountError", "API call failed", e)
                            _deleteAccountState.value = Result.Error("Failed to delete account: ${e.message}")
                        }
                        .collectLatest { result ->
                            _deleteAccountState.value = result
                        }
                } else {
                    Log.e("DeleteAccountError", "Token is missing!")
                    _deleteAccountState.value = Result.Error("Authentication token is missing!")
                }
            } catch (e: Exception) {
                Log.e("DeleteAccountError", "Unexpected error", e)
                _deleteAccountState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }

}
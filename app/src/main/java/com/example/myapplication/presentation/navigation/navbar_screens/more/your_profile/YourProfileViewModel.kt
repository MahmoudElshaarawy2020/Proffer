package com.example.myapplication.presentation.navigation.navbar_screens.more.your_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.EditProfileResponse
import com.example.myapplication.domain.use_case.DeleteProfileUseCase
import com.example.myapplication.domain.use_case.EditYourProfileUseCase
import com.example.myapplication.domain.use_case.GetYourProfileUseCase
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
class YourProfileViewModel @Inject constructor(
    private val profileUseCase: GetYourProfileUseCase,
    private val deleteAccountUseCase: DeleteProfileUseCase,
    private val editProfileUseCase: EditYourProfileUseCase
) : ViewModel() {

    private val _yourProfileState = MutableStateFlow<Result<AuthResponse>>(Result.Loading())
    val yourProfileState: MutableStateFlow<Result<AuthResponse>> get() = _yourProfileState

    private val _deleteAccountState = MutableStateFlow<Result<AuthResponse>>(Result.Loading())
    val deleteAccountState: MutableStateFlow<Result<AuthResponse>> get() = _deleteAccountState

    private val _editYourProfileState =
        MutableStateFlow<Result<EditProfileResponse>>(Result.Loading())
    val editYourProfileState: MutableStateFlow<Result<EditProfileResponse>> get() = _editYourProfileState

    fun getYourProfileData(token: String) {
        if (token.isBlank()) {
            Log.e("YourProfileRequestError", "Token is missing!")
            _yourProfileState.value = Result.Error("Authentication token is missing!")
            return
        }

        viewModelScope.launch {
            profileUseCase.invoke()
                .catch { e ->
                    Log.e("YourProfileRequestError", "API call failed", e)
                    _yourProfileState.value = Result.Error("Unexpected Error: ${e.message}")
                }
                .collectLatest { result ->
                    _yourProfileState.value = result
                }
        }
    }

    fun deleteAccount(token: String) {
        if (token.isBlank()) {
            Log.e("DeleteAccountError", "Token is missing!")
            _deleteAccountState.value = Result.Error("Authentication token is missing!")
            return
        }

        viewModelScope.launch {
            _deleteAccountState.value = Result.Loading()

            deleteAccountUseCase.invoke()
                .catch { e ->
                    Log.e("DeleteAccountError", "API call failed", e)
                    _deleteAccountState.value = Result.Error("Failed to delete account: ${e.message}")
                }
                .collectLatest { result ->
                    _deleteAccountState.value = result
                }
        }
    }

    fun editYourProfile(
        token: String,
        method: RequestBody,
        userName: RequestBody,
        phoneNumber: RequestBody?,
        address: RequestBody?,
        image: MultipartBody.Part?
    ) {
        if (token.isBlank()) {
            Log.e("EditYourProfileError", "Token is missing!")
            _editYourProfileState.value = Result.Error("Authentication token is missing!")
            return
        }

        viewModelScope.launch {
            _editYourProfileState.value = Result.Loading()

            if (userName != null) {
                if (phoneNumber != null) {
                    if (address != null) {
                        editProfileUseCase.invoke(method, userName, phoneNumber, address, image)
                            .catch { e ->
                                Log.e("EditYourProfileError", "API call failed", e)
                                _editYourProfileState.value = Result.Error("Failed to edit profile: ${e.message}")
                            }
                            .collectLatest { result ->
                                _editYourProfileState.value = result
                            }
                    }
                }
            }
        }
    }
}

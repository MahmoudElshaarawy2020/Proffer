package com.example.myapplication.presentation.navigation.navbar_screens.more

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.response.AboutUsResponse
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.FAQResponse
import com.example.myapplication.data.response.PrivacyPolicyResponse
import com.example.myapplication.domain.use_case.LogoutUseCase
import com.example.myapplication.domain.use_case.ProfileUseCase
import com.example.myapplication.domain.use_case.getAboutUsUseCase
import com.example.myapplication.domain.use_case.getFAQUseCase
import com.example.myapplication.domain.use_case.getPrivacyUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoreViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val getFAQUseCase: getFAQUseCase,
    private val getPrivacyUseCase : getPrivacyUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getAboutUsUseCase: getAboutUsUseCase,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    // Update the state to reflect the correct type
    private val _profileState = MutableStateFlow<Result<AuthResponse>>(Result.Loading())
    val profileState: MutableStateFlow<Result<AuthResponse>> get() = _profileState

    private val _getFaqState = MutableStateFlow<Result<FAQResponse>>(Result.Loading())
    val getFaqState: MutableStateFlow<Result<FAQResponse>> get() = _getFaqState

    private val _getAboutUsState = MutableStateFlow<Result<AboutUsResponse>>(Result.Loading())
    val getAboutUsState: MutableStateFlow<Result<AboutUsResponse>> get() = _getAboutUsState

    private val _getPrivacyState = MutableStateFlow<Result<PrivacyPolicyResponse>>(Result.Loading())
    val getPrivacyState: MutableStateFlow<Result<PrivacyPolicyResponse>> get() = _getPrivacyState


    private val _logoutState = MutableStateFlow<Result<AuthResponse>>(Result.Loading())
    val logoutState: MutableStateFlow<Result<AuthResponse>> get() = _logoutState

    fun getMoreAboutUser(token: String) {
        viewModelScope.launch {
            try {
                if (token.isNotEmpty()) {
                    profileUseCase.invoke(token)
                        .catch { e ->
                            Log.e("ProfileRequestError", "API call failed", e)
                            _profileState.value = Result.Error("Unexpected Error: ${e.message}")
                        }
                        .collectLatest { result ->
                            _profileState.value = result
                        }
                } else {
                    Log.e("ProfileRequestError", "Token is missing!")
                    _profileState.value = Result.Error("Authentication token is missing!")
                }
            } catch (e: Exception) {
                Log.e("ProfileRequestError", "Unexpected error", e)
                _profileState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }

    fun logout(token: String) {
        viewModelScope.launch {
            _logoutState.value = Result.Loading()

            logoutUseCase.invoke(token)
                .catch { e ->
                    Log.e("LogoutError", "API call failed", e)
                    _logoutState.value = Result.Error("Failed to logout: ${e.message}")
                }
                .collectLatest { result ->
                    _logoutState.value = result
                    if (result is Result.Success) {
                        viewModelScope.launch {
                            dataStoreManager.clearToken()
                        }
                    }
                }
        }
    }

    fun getFAQ() {
        viewModelScope.launch {
            try {
                getFAQUseCase.invoke()
                    .catch { e ->
                        Log.e("getFAQRequestError", "API call failed", e)
                        _getFaqState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        _getFaqState.value = result
                    }

            } catch (e: Exception) {
                Log.e("getFAQRequestError", "Unexpected error", e)
                _getFaqState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }
    fun getPrivacy() {
        viewModelScope.launch {
            try {
                getPrivacyUseCase.invoke()
                    .catch { e ->
                        Log.e("getPrivacyError", "API call failed", e)
                        _getPrivacyState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        _getPrivacyState.value = result
                    }

            } catch (e: Exception) {
                Log.e("getPrivacyError", "Unexpected error", e)
                _getPrivacyState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }


    fun getAboutUs() {
        viewModelScope.launch {
            try {
                getAboutUsUseCase.invoke()
                    .catch { e ->
                        Log.e("getAboutUsError", "API call failed", e)
                        _getAboutUsState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        _getAboutUsState.value = result
                    }

            } catch (e: Exception) {
                Log.e("getAboutUsError", "Unexpected error", e)
                _getAboutUsState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }
}

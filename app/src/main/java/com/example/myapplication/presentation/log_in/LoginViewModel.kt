package com.example.myapplication.presentation.log_in

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.request.LoginRequest
import com.example.myapplication.data.response.AuthResponse
import com.example.myapplication.data.response.Data
import com.example.myapplication.domain.use_case.LoginUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    val userData: LiveData<Data?> = dataStoreManager.getUserData.asLiveData()

    private val _loginState = MutableStateFlow<Result<AuthResponse>>(Result.Loading())
    val loginState: MutableStateFlow<Result<AuthResponse>> get() = _loginState

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase.invoke(loginRequest)
                .catch { e ->
                    Log.e("LoginError", "API call failed", e)
                    _loginState.value = Result.Error("Unexpected Error: ${e.message}")
                }
                .collectLatest { result ->
                    _loginState.value = result
                    if (result is Result.Success) {
                        result.data?.token?.let { token ->
                            Log.d("New Token", token)
                            dataStoreManager.saveAuthToken(token)

                            val savedToken = dataStoreManager.getToken
                            savedToken.collectLatest { storedToken ->
                                if (storedToken == token) {
                                    Log.d("Token Saved", "Token successfully saved in DataStore")
                                } else {
                                    Log.e("Token Save Error", "Token was not saved correctly!")
                                }
                            }
                        }
                    }
                }
        }
    }


    fun getAuthToken(): Flow<String?> = dataStoreManager.getToken

}
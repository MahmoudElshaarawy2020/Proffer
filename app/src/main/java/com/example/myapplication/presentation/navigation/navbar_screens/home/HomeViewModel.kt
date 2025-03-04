package com.example.myapplication.presentation.navigation.navbar_screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.HomeResponse
import com.example.myapplication.data.response.HomeSliderResponse
import com.example.myapplication.domain.use_case.GetContractorsUseCase
import com.example.myapplication.domain.use_case.GetSliderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.myapplication.util.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSliderUseCase: GetSliderUseCase,
    private val getContractorsUseCase: GetContractorsUseCase
): ViewModel() {

    private val _getSliderState = MutableStateFlow<Result<HomeSliderResponse>>(Result.Loading())
    val getSliderState: MutableStateFlow<Result<HomeSliderResponse>> get() = _getSliderState

    private val _getContractorsState = MutableStateFlow<Result<HomeResponse>>(Result.Loading())
    val getContractorsState: MutableStateFlow<Result<HomeResponse>> get() = _getContractorsState

    fun getSliders() {
        viewModelScope.launch {
            try {
                getSliderUseCase.invoke()
                    .catch { e ->
                        Log.e("getSlidersError", "API call failed", e)
                        _getSliderState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        _getSliderState.value = result
                    }

            } catch (e: Exception) {
                Log.e("getSlidersError", "Unexpected error", e)
                _getSliderState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }

    fun getContractors(token: String) {
        if (token.isBlank()) {
            Log.e("getContractorsError", "Token is missing!")
            _getContractorsState.value = Result.Error("Authentication token is missing!")
            return
        }

        viewModelScope.launch {
            getContractorsUseCase.invoke(token)
                .catch { e ->
                    Log.e("getContractorsError", "API call failed", e)
                    _getContractorsState.value = Result.Error("Unexpected Error: ${e.message}")
                }
                .collectLatest { result ->
                    _getContractorsState.value = result
                }
        }
    }
}
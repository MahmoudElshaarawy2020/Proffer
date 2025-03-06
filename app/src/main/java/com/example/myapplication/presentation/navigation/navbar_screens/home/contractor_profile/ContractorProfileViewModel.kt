package com.example.myapplication.presentation.navigation.navbar_screens.home.contractor_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.ContractorProfileResponse
import com.example.myapplication.data.response.HomeSliderResponse
import com.example.myapplication.domain.use_case.GetContractorProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContractorProfileViewModel @Inject constructor(
    private val getContractorProfileUseCase: GetContractorProfileUseCase
): ViewModel() {

    private val _getContractorProfileState = MutableStateFlow<Result<ContractorProfileResponse>>(Result.Loading())
    val getContractorProfileState: MutableStateFlow<Result<ContractorProfileResponse>> get() = _getContractorProfileState

    fun getContractorProfile(token: String, id: Int) {
        viewModelScope.launch {
            try {
                getContractorProfileUseCase.invoke(token, id)
                    .catch { e ->
                        Log.e("getContractorProfileError", "API call failed", e)
                        _getContractorProfileState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        _getContractorProfileState.value = result
                    }

            } catch (e: Exception) {
                Log.e("getContractorProfileError", "Unexpected error", e)
                _getContractorProfileState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }
}
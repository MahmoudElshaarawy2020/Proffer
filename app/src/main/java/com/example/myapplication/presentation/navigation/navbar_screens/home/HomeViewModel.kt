package com.example.myapplication.presentation.navigation.navbar_screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.data_store.DataStoreManager
import com.example.myapplication.data.response.Data
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
    private val getContractorsUseCase: GetContractorsUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val userData: LiveData<Data?> = dataStoreManager.getUserData.asLiveData()

    private val _getSliderState = MutableStateFlow<Result<HomeSliderResponse>>(Result.Loading())
    val getSliderState: MutableStateFlow<Result<HomeSliderResponse>> get() = _getSliderState
    private var isSlidersFetched = false

    private val _getContractorsState = MutableStateFlow<Result<HomeResponse>>(Result.Loading())
    val getContractorsState: MutableStateFlow<Result<HomeResponse>> get() = _getContractorsState
    private var isContractorsFetched = false

    fun getSliders() {
        if (isSlidersFetched) return
        isSlidersFetched = true

        viewModelScope.launch {
            try {
                getSliderUseCase.invoke()
                    .catch { e ->
                        Log.e("getSlidersError", "API call failed", e)
                        _getSliderState.value = Result.Error("Unexpected Error: ${e.message}")
                        isSlidersFetched = false
                    }
                    .collectLatest { result ->
                        _getSliderState.value = result
                    }
            } catch (e: Exception) {
                Log.e("getSlidersError", "Unexpected error", e)
                _getSliderState.value = Result.Error("Unexpected Error: ${e.message}")
                isSlidersFetched = false
            }
        }
    }

    fun getContractors(token: String) {
        if (token.isBlank() || isContractorsFetched) return
        isContractorsFetched = true

        viewModelScope.launch {
            getContractorsUseCase.invoke()
                .catch { e ->
                    Log.e("getContractorsError", "API call failed", e)
                    _getContractorsState.value = Result.Error("Unexpected Error: ${e.message}")
                    isContractorsFetched = false
                }
                .collectLatest { result ->
                    _getContractorsState.value = result
                }
        }
    }
}

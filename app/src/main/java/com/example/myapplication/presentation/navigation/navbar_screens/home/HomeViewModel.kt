package com.example.myapplication.presentation.navigation.navbar_screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.SliderResponse
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
    private val getSliderUseCase: GetSliderUseCase
): ViewModel() {

    private val _getSliderState = MutableStateFlow<Result<SliderResponse>>(Result.Loading())
    val getSliderState: MutableStateFlow<Result<SliderResponse>> get() = _getSliderState

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
}
package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project.add_room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.ProjectTypesResponse
import com.example.myapplication.data.response.RoomZonesResponse
import com.example.myapplication.domain.use_case.GetRoomZonesUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getRoomZonesUseCase: GetRoomZonesUseCase
) : ViewModel() {

    private val _getRoomZonesState =
        MutableStateFlow<Result<RoomZonesResponse>>(Result.Loading())
    val getRoomZonesState: MutableStateFlow<Result<RoomZonesResponse>> get() = _getRoomZonesState

    fun getRoomZones() {
        viewModelScope.launch {
            try {
                getRoomZonesUseCase.invoke()
                    .catch { e ->
                        Log.e("getRoomZonesError", "API call failed", e)
                        _getRoomZonesState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        _getRoomZonesState.value = result
                    }

            } catch (e: Exception) {
                Log.e("getRoomZonesError", "Unexpected error", e)
                _getRoomZonesState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }
}
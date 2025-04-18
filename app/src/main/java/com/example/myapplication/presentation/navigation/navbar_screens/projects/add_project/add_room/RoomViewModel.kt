package com.example.myapplication.presentation.navigation.navbar_screens.projects.add_project.add_room

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.AdditionsResponse
import com.example.myapplication.data.response.MaterialsResponse
import com.example.myapplication.data.response.RoomZonesResponse
import com.example.myapplication.domain.use_case.GetAdditionsUseCase
import com.example.myapplication.domain.use_case.GetMaterialsUseCase
import com.example.myapplication.domain.use_case.GetRoomZonesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.myapplication.util.Result
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getRoomZonesUseCase: GetRoomZonesUseCase,
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val getAdditionsUseCase: GetAdditionsUseCase
) : ViewModel() {

    private val _getRoomZonesState =
        MutableStateFlow<Result<RoomZonesResponse>>(Result.Loading())
    val getRoomZonesState: MutableStateFlow<Result<RoomZonesResponse>> get() = _getRoomZonesState


    private val _getMaterialsState =
        MutableStateFlow<Result<MaterialsResponse>>(Result.Loading())
    val getMaterialsState: MutableStateFlow<Result<MaterialsResponse>> get() = _getMaterialsState


    private val _getAdditionsState =
        MutableStateFlow<Result<AdditionsResponse>>(Result.Loading())
    val getAdditionsState: MutableStateFlow<Result<AdditionsResponse>> get() = _getAdditionsState

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

    fun getMaterials(category: Int) {
        viewModelScope.launch {
            try {
                getMaterialsUseCase.invoke(category)
                    .catch { e ->
                        Log.e("getMaterialError", "API call failed", e)
                        _getMaterialsState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        _getMaterialsState.value = result
                    }

            } catch (e: Exception) {
                Log.e("getMaterialError", "Unexpected error", e)
                _getMaterialsState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }

    fun getAdditions(category: Int) {
        viewModelScope.launch {
            try {
                getAdditionsUseCase.invoke(category)
                    .catch { e ->
                        Log.e("getAdditionsError", "API call failed", e)
                        _getAdditionsState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        _getAdditionsState.value = result
                    }

            } catch (e: Exception) {
                Log.e("getAdditionsError", "Unexpected error", e)
                _getAdditionsState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }
}
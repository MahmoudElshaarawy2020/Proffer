package com.example.myapplication.presentation.navigation.navbar_screens.bids

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.response.BidsResponse
import com.example.myapplication.domain.use_case.GetBidsUseCase
import com.example.myapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BidsViewModel @Inject constructor(
    private val getBidsUseCase: GetBidsUseCase,
    ): ViewModel() {

    private val _getBidsState = MutableStateFlow<Result<BidsResponse>>(Result.Loading())
    val getBidsState: MutableStateFlow<Result<BidsResponse>> get() = _getBidsState

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    private var currentPage = 1
    private val pageSize = 10
    private var canLoadMore = true

    fun getBids(skip: Int = 0, take: Int = 10, projectId: Int, rate: Int, minPrice: Int, maxPrice: Int, token: String) {
        viewModelScope.launch {
            try {
                getBidsUseCase.invoke(skip, take, projectId, rate, minPrice, maxPrice, token)
                    .catch { e ->
                        Log.e("getBidsRequestError", "API call failed", e)
                        _getBidsState.value = Result.Error("Unexpected Error: ${e.message}")
                    }
                    .collectLatest { result ->
                        if (result is Result.Success) {
                            val bidsList = result.data?.data ?: emptyList()
                            canLoadMore = bidsList.isNotEmpty()
                        }
                        _getBidsState.value = result
                    }
            } catch (e: Exception) {
                Log.e("getBidsRequestError", "Unexpected error", e)
                _getBidsState.value = Result.Error("Unexpected Error: ${e.message}")
            }
        }
    }

    fun loadMoreBids(projectId: Int, rate: Int, minPrice: Int, maxPrice: Int, token: String) {
        if (!canLoadMore || _isLoadingMore.value) return

        _isLoadingMore.value = true

        viewModelScope.launch {
            val currentFaqList = (_getBidsState.value as? Result.Success)?.data?.data.orEmpty()
            val newSkip = (currentPage - 1) * pageSize
            val newTake = pageSize

            try {
                getBidsUseCase.invoke(newSkip, newTake, projectId, rate, minPrice, maxPrice, token)
                    .catch { e ->
                        Log.e("LoadMoreBidsError", "API call failed", e)
                        _isLoadingMore.value = false
                    }
                    .collectLatest { result ->
                        if (result is Result.Success) {
                            val newData = result.data?.data.orEmpty()
                            if (newData.isEmpty()) {
                                canLoadMore = false
                            } else {
                                currentPage++
                            }
                            val updatedList = currentFaqList + newData
                            _getBidsState.value = Result.Success(BidsResponse(updatedList))
                        }
                        _isLoadingMore.value = false
                    }
            } catch (e: Exception) {
                Log.e("LoadMoreBidsError", "Unexpected error", e)
                _isLoadingMore.value = false
            }
        }
    }
}
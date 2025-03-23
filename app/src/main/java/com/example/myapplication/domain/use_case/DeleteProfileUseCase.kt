package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.repository.YourProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.myapplication.util.Result

class DeleteProfileUseCase @Inject constructor(
    private val repository: YourProfileRepository
) {
    operator fun invoke() = repository.deleteAccount()
}
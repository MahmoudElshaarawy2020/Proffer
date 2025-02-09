package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.request.RegisterRequest

class RegisterRepoImpl(
    private val apiService: ApiService,
    private val registerRequest: RegisterRequest
) {
}
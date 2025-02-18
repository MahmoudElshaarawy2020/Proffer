package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @field:SerializedName("data")
    val userData: Data? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("token")
    val token: String? = null
)

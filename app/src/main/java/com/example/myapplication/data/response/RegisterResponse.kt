package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,

    //May be change it to a data class later
    @SerializedName("data")
    val data: String

)
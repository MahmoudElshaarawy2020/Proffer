package com.example.myapplication.data.request

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(
    @SerializedName("name")
    var userName: String,
    @SerializedName("phone")
    var phoneNumber: String,
    @SerializedName("address")
    var address: String,

    @SerializedName("_method")
    var method: String = "put",
)
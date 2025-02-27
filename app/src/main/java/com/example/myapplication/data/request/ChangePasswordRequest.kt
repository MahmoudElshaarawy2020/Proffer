package com.example.myapplication.data.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest (
    @SerializedName("old_password")
    var oldPassword: String,

    @SerializedName("new_password")
    var newPassword: String,

    @SerializedName("new_password_confirmation")
    var newPasswordConfirmation: String,


    )
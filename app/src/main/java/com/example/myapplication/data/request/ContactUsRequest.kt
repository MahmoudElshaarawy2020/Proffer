package com.example.myapplication.data.request

import com.google.gson.annotations.SerializedName

data class ContactUsRequest(
    @SerializedName("contact_type_id")
    val contactTypeId: Int,
    @SerializedName("message")
    val message: String
)

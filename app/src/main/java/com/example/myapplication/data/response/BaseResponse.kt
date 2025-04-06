package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @field:SerializedName("data")
    open val data: T? = null,

    @field:SerializedName("message")
   open val message: String? = null,

    @field:SerializedName("status")
    open val status: Boolean? = null
)

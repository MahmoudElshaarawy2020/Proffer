package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class VerificationResponse(

	@SerializedName("data")
	val userData: UserData? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Boolean? = null,

	@SerializedName("token")
	val token: String? = null,

	@SerializedName("error")
	val error: String? = null
)


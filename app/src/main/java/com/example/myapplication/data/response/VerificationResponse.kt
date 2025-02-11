package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class VerificationResponse(

	@SerializedName("data")
	val data: Data? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Boolean? = null,

	@SerializedName("token")
	val token: String? = null,

	@SerializedName("error")
	val error: String? = null
)

data class Data(

	@SerializedName("account_type")
	val accountType: Int? = null,

	@SerializedName("profile_image")
	val profileImage: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("email")
	val email: String? = null
)

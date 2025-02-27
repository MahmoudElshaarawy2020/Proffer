package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class PrivacyPolicyResponse(

	@field:SerializedName("data")
	val data: PrivacyData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class PrivacyData(

	@field:SerializedName("privacy_policy")
	val privacyPolicy: String? = null
)

package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class TermsResponse(

	@field:SerializedName("data")
	val data: TermsData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class TermsData(

	@field:SerializedName("terms")
	val terms: String? = null
)

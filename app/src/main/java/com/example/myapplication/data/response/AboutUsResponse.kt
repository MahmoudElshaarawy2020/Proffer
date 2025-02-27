package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class AboutUsResponse(

	@field:SerializedName("data")
	val data: AboutUsData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class AboutUsData(

	@field:SerializedName("about_us")
	val aboutUs: String? = null
)

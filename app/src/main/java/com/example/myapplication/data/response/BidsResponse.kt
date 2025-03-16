package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class BidsResponse(

	@field:SerializedName("data")
	val data: List<Any?>? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
